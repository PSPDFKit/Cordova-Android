package com.pspdfkit.cordova;

import com.pspdfkit.cordova.action.Action;
import com.pspdfkit.cordova.action.BasicAction;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.listeners.DocumentListener;
import com.pspdfkit.listeners.SimpleDocumentListener;
import com.pspdfkit.ui.PdfFragment;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.pspdfkit.cordova.Utilities.checkArgumentNotNull;

/**
 * Internal singleton class used by the PSPDFKit plugin to communicate between activities and the
 * JavaScript context.
 */
public class EventDispatcher {
  @Nullable private static EventDispatcher instance;
  private CordovaPdfActivity activity;
  /**
   * A callback channel for sending events from Java to JavaScript. This is registered as soon as
   * there's at least one listener for PSPDFKit events in JavaScript.
   */
  @Nullable private CallbackContext eventChannel = null;

  @NonNull
  private final DocumentListener listener =
      new SimpleDocumentListener() {
        @Override
        public void onDocumentSaved(@NonNull PdfDocument document) {
          sendEvent("onDocumentSaved", new JSONObject());
        }

        @Override
        public void onDocumentSaveFailed(
            @NonNull PdfDocument document, @NonNull Throwable exception) {
          try {
            final JSONObject data = new JSONObject();
            data.put("message", exception.getMessage());
            sendEvent("onDocumentSaveFailed", data);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
      };

  private EventDispatcher() {}

  @NonNull
  public static synchronized EventDispatcher getInstance() {
    if (instance == null) {
      instance = new EventDispatcher();
    }
    return instance;
  }

  private void sendEvent(String eventType, JSONObject data) {
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

  void bindActivity(@NonNull final CordovaPdfActivity activity) {
    checkArgumentNotNull(activity, "activity");
    if (this.activity != null) {
      throw new IllegalStateException(
          "EventDispatcher only supports a single CordovaPdfActivity at a time.");
    }
    this.activity = activity;
    final PdfFragment pdfFragment = this.activity.getPdfFragment();
    if (pdfFragment == null) {
      throw new IllegalStateException(
          "EventDispatcher only supports binding to activities that have a fragment instance.");
    }
    pdfFragment.addDocumentListener(listener);
  }

  void releaseActivity() {
    PdfFragment fragment = activity.getPdfFragment();
    if (fragment != null) {
      fragment.removeDocumentListener(listener);
    }
    activity = null;
  }

  private void verifyBoundToActivity() {
    if (activity == null)
      throw new IllegalStateException(
          "Can't dispatch notifications on the activity. No activity was bound.");
  }

  /**
   * Dismisses any previously registered PDF activity. This method returns {@code false} if no
   * activity was registered.
   */
  public void notifyDismiss() {
    verifyBoundToActivity();
    activity.dismiss();
  }

  public boolean notifySaveDocument() throws IOException {
    verifyBoundToActivity();
    return activity.saveDocument();
  }

  Action getStartEventDispatchingAction(@NonNull final PSPDFKitCordovaPlugin plugin) {
    return new BasicAction("startEventDispatching", plugin) {
      @Override
      protected void execAction(JSONArray args, CallbackContext callbackContext) {
        // Capture the given callback and make sure it is retained in JavaScript too.
        final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
        result.setKeepCallback(true);
        callbackContext.sendPluginResult(result);
        eventChannel = callbackContext;
      }
    };
  }

  Action getStopEventDispatchingAction(@NonNull final PSPDFKitCordovaPlugin plugin) {
    return new BasicAction("stopEventDispatching", plugin) {
      @Override
      protected void execAction(JSONArray args, CallbackContext callbackContext) {
        if (eventChannel != null) {
          // Send one last event through the event channel, releasing the JavaScript object.
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

  public void notifyActivityDismissed() {
    sendEvent("onDocumentDismissed", new JSONObject());
  }
}
