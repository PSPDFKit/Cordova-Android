package com.pspdfkit.cordova.action;

import android.content.Intent;

import com.pspdfkit.cordova.EventDispatcher;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Action to dismiss all currently displayed {@link com.pspdfkit.cordova.CordovaPdfActivity}
 * instances.
 */
public final class DismissAction extends BasicAction implements PSPDFKitCordovaPlugin.OnActivityResultListener {
  /**
   * Callback for notifying listeners of successful dismissal of PDF activities. Only a single
   * callback can be stored and notified at a time.
   */
  @Nullable private CallbackContext pendingOnDismissCallback = null;

  public DismissAction(@NonNull final String name, @NonNull final PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
    plugin.registerOnActivityResultListener(this);
  }

  @Override
  public void execAction(JSONArray args, CallbackContext callbackContext) {
    pendingOnDismissCallback = callbackContext;
    EventDispatcher.getInstance().notifyDismiss();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if (pendingOnDismissCallback != null) {
      pendingOnDismissCallback.success();
      pendingOnDismissCallback = null;
    }
  }
}
