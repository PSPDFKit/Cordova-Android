package com.pspdfkit.cordova.event;

import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.Utilities;
import com.pspdfkit.cordova.action.Action;
import com.pspdfkit.cordova.action.BasicAction;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Internal singleton class used by the PSPDFKit plugin to communicate between activities and the
 * JavaScript context.
 */
public class EventDispatcher {
  @Nullable private static EventDispatcher instance;
  /**
   * A callback channel for sending events from Java to JavaScript. This is registered as soon as
   * there's at least one listener for PSPDFKit events in JavaScript.
   */
  @Nullable private CallbackContext eventChannel = null;

  @Nullable private EventDispatchingActions connectionActions;

  private EventDispatcher() {}

  @NonNull
  public static synchronized EventDispatcher getInstance() {
    if (instance == null) {
      instance = new EventDispatcher();
    }
    return instance;
  }

  public void notifyActivityDismissed() {
    sendEvent("onDocumentDismissed");
  }

  private void sendEvent(@NonNull final String eventType) {
    sendEvent(eventType, new JSONObject());
  }

  public void sendEvent(@NonNull final String eventType, @NonNull final JSONObject data) {
    final CallbackContext eventChannel = this.eventChannel;
    if (eventChannel != null) {
      try {
        final JSONObject info = new JSONObject();
        info.put("eventType", eventType);
        info.put("data", data);
        final PluginResult result = new PluginResult(PluginResult.Status.OK, info);
        result.setKeepCallback(true);
        eventChannel.sendPluginResult(result);
      } catch (JSONException ex) {
        final PluginResult result =
            new PluginResult(PluginResult.Status.JSON_EXCEPTION, ex.getMessage());
        result.setKeepCallback(true);
        eventChannel.sendPluginResult(result);
      }
    }
  }

  public EventDispatchingActions getConnectionActions(@NonNull final PSPDFKitCordovaPlugin plugin) {
    Utilities.checkArgumentNotNull(plugin, "plugin");
    if (connectionActions == null) {
      connectionActions = new EventDispatchingActions(plugin);
    }

    return connectionActions;
  }

  /**
   * Two {@link com.pspdfkit.cordova.action.Action} instances that are exposed to the JavaScript
   * plugin for connecting and disconnecting to the event dispatcher. Upon connecting to the event
   * dispatcher using the {@link #startEventDispatching} action, the provided {@link
   * CallbackContext} is retained, as used as communication channel for sending events from Java to
   * JavaScript. This channel is released upon calling {@link #stopEventDispatching}.
   */
  public class EventDispatchingActions {
    public final Action startEventDispatching;
    public final Action stopEventDispatching;

    EventDispatchingActions(PSPDFKitCordovaPlugin plugin) {
      startEventDispatching =
          new BasicAction("startEventDispatching", plugin) {
            @Override
            protected void execAction(JSONArray args, CallbackContext callbackContext) {
              // Capture the given callback and make sure it is retained in JavaScript too.
              final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
              result.setKeepCallback(true);
              callbackContext.sendPluginResult(result);
              eventChannel = callbackContext;
            }
          };

      stopEventDispatching =
          new BasicAction("stopEventDispatching", plugin) {
            @Override
            protected void execAction(JSONArray args, CallbackContext callbackContext) {
              if (eventChannel != null) {
                // Send one last event through the event channel, releasing the JavaScript
                // object.
                final PluginResult result = new PluginResult(PluginResult.Status.OK);
                result.setKeepCallback(false);
                eventChannel.sendPluginResult(result);
                // Clean up the Java reference and notify that the operation was successful.
                eventChannel = null;
                callbackContext.success();
              }
            }
          };
    }
  }
}
