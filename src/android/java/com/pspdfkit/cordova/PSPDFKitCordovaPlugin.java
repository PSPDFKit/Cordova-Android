/*
 *   Copyright (c) 2015-2018 PSPDFKit GmbH. All rights reserved.
 *
 *   THIS SOURCE CODE AND ANY ACCOMPANYING DOCUMENTATION ARE PROTECTED BY INTERNATIONAL COPYRIGHT LAW
 *   AND MAY NOT BE RESOLD OR REDISTRIBUTED. USAGE IS BOUND TO THE PSPDFKIT LICENSE AGREEMENT.
 *   UNAUTHORIZED REPRODUCTION OR DISTRIBUTION IS SUBJECT TO CIVIL AND CRIMINAL PENALTIES.
 *   This notice may not be removed from this file.
 */

package com.pspdfkit.cordova;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.pspdfkit.PSPDFKit;
import com.pspdfkit.cordova.action.ActionManager;
import com.pspdfkit.cordova.action.DismissAction;
import com.pspdfkit.cordova.action.annotation.AddAnnotationAction;
import com.pspdfkit.cordova.action.annotation.ApplyInstantJsonAction;
import com.pspdfkit.cordova.action.annotation.GetAllUnsavedAnnotationsAction;
import com.pspdfkit.cordova.action.annotation.GetAnnotationsAction;
import com.pspdfkit.cordova.action.annotation.RemoveAnnotationAction;
import com.pspdfkit.cordova.action.document.SaveDocumentAction;
import com.pspdfkit.cordova.action.document.ShowDocumentFromAssetsAction;
import com.pspdfkit.cordova.action.document.ShowDocumentAction;
import com.pspdfkit.cordova.event.EventDispatcher;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Primary PSPDFKit plugin class for Cordova applications. Methods of this class are exposed in
 * JavaScript using the {@code PSPDFKit} object.
 */
public class PSPDFKitCordovaPlugin extends CordovaPlugin {
  /**
   * Name of a {@code meta-data} element in the app manifest file holding the PSPDFKit license key.
   */
  private static final String METADATA_LICENSE_KEY = "pspdfkit_license_key";

  @NonNull
  private final List<OnActivityResultListener> onActivityResultListeners = new ArrayList<>();

  @NonNull private final EventDispatcher eventDispatcher = EventDispatcher.getInstance();
  private ActionManager actionManager;

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    initializePSPDFKit(cordova);

    final EventDispatcher.EventDispatchingActions connectionActions =
        eventDispatcher.getConnectionActions(this);
    actionManager =
        new ActionManager(
            connectionActions.startEventDispatching,
            connectionActions.stopEventDispatching,
            new ShowDocumentAction("showDocument", this),
            new ShowDocumentFromAssetsAction("showDocumentFromAssets", this),
            new DismissAction("dismiss", this),
            new SaveDocumentAction("saveDocument", this),
            new AddAnnotationAction("addAnnotation", this),
            new RemoveAnnotationAction("removeAnnotation", this),
            new ApplyInstantJsonAction("applyInstantJson", this),
            new GetAnnotationsAction("getAnnotations", this),
            new GetAllUnsavedAnnotationsAction("getAllUnsavedAnnotations", this));
  }

  private void initializePSPDFKit(CordovaInterface cordova) {
    final String licenseKey;
    try {
      licenseKey =
          cordova
              .getActivity()
              .getPackageManager()
              .getApplicationInfo(
                  cordova.getActivity().getPackageName(), PackageManager.GET_META_DATA)
              .metaData
              .getString(METADATA_LICENSE_KEY, null);
    } catch (PackageManager.NameNotFoundException e) {
      throw new PSPDFKitCordovaPluginException(
          "Error while reading PSPDFKit license from AndroidManifest.xml", e);
    }

    if (TextUtils.isEmpty(licenseKey)) {
      throw new PSPDFKitCordovaPluginException(
          "PSPDFKit license key is missing! Please add a <meta-data android:name=\"pspdfkit_license_key\" android:value=\"...\"> to your AndroidManifest.xml.");
    }

    try {
      PSPDFKit.initialize(cordova.getActivity(), licenseKey);
    } catch (Exception ex) {
      throw new PSPDFKitCordovaPluginException("Error while initializing PSPDFKit", ex);
    }
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    return actionManager.executeAction(action, args, callbackContext);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    for (OnActivityResultListener listener : onActivityResultListeners) {
      listener.onActivityResult(requestCode, resultCode, intent);
    }
  }

  /**
   * Registers the given {@code listener} to be notified of calls to {@link #onActivityResult(int,
   * int, Intent)}.
   */
  public void registerOnActivityResultListener(@NonNull final OnActivityResultListener listener) {
    onActivityResultListeners.add(listener);
  }

  /**
   * Listener for Android's activity result events. These can be registered on the plugin using
   * {@link #registerOnActivityResultListener(OnActivityResultListener)}.
   */
  public interface OnActivityResultListener {
    void onActivityResult(int requestCode, int resultCode, Intent intent);
  }
}
