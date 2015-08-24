/*
 * PSPDFKitCordovaPlugin.java
 *
 *   PSPDFKit
 *
 *   Copyright (c) 2015 PSPDFKit GmbH. All rights reserved.
 *
 *   THIS SOURCE CODE AND ANY ACCOMPANYING DOCUMENTATION ARE PROTECTED BY INTERNATIONAL COPYRIGHT LAW
 *   AND MAY NOT BE RESOLD OR REDISTRIBUTED. USAGE IS BOUND TO THE PSPDFKIT LICENSE AGREEMENT.
 *   UNAUTHORIZED REPRODUCTION OR DISTRIBUTION IS SUBJECT TO CIVIL AND CRIMINAL PENALTIES.
 *   This notice may not be removed from this file.
 */

package com.pspdfkit.cordova;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.pspdfkit.PSPDFKit;
import com.pspdfkit.configuration.activity.PSPDFKitActivityConfiguration;
import com.pspdfkit.ui.PSPDFKitActivity;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

public class PSPDFKitCordovaPlugin extends CordovaPlugin {

    private static final String METADATA_LICENSE_KEY = "PSPDFKIT_LICENSE_KEY";

    private String licenseKey;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        licenseKey = cordova.getActivity().getApplicationInfo().metaData.getString(METADATA_LICENSE_KEY, null);

        if (TextUtils.isEmpty(licenseKey)) {
            throw new PSPDFKitCordovaPluginException("PSPDFKit license key is missing! Please add a <meta-data android:name=\"PSPDFKIT_LICENSE_KEY\" android:value=\"...\"> to your AndroidManifest.xml.");
        }

        try {
            PSPDFKit.initialize(cordova.getActivity(), licenseKey);
        } catch (Exception ex) {
            throw new PSPDFKitCordovaPluginException("Error while initializing PSPDFKit", ex);
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("showDocument")) {
            final Uri documentUri = Uri.parse(args.getString(0));
            this.showDocument(documentUri, callbackContext);
            return true;
        } else if (action.equals("showDocumentFromAssets")) {
            this.showDocumentFromAssets(args.getString(0), callbackContext);
            return true;
        }

        return false;
    }

    /**
     * Starts the {@link PSPDFKitActivity} to show a single document.
     *
     * @param documentUri     Local filesystem Uri pointing to a document.
     * @param callbackContext Cordova callback.
     */
    private void showDocument(@NonNull Uri documentUri, CallbackContext callbackContext) {
        showDocumentForUri(documentUri);
        callbackContext.success();
    }

    /**
     * Starts the {@link PSPDFKitActivity} to show a single document stored within the app's assets.
     *
     * @param assetPath       Relative path inside the app's assets folder.
     * @param callbackContext Cordova callback.
     */
    private void showDocumentFromAssets(@NonNull final String assetPath, final CallbackContext callbackContext) {
        ExtractAssetTask.extract(assetPath, cordova.getActivity(), new ExtractAssetTask.OnDocumentExtractedCallback() {
            @Override
            public void onDocumentExtracted(File documentFile) {
                if(documentFile != null) {
                    showDocumentForUri(Uri.fromFile(documentFile));
                    callbackContext.success();
                } else {
                    callbackContext.error("Could not load '" + assetPath + "' from the assets.");
                }
            }
        });
    }

    private void showDocumentForUri(Uri uri) {
        PSPDFKitActivity.showDocument(cordova.getActivity(), uri, new PSPDFKitActivityConfiguration.Builder(cordova.getActivity(), licenseKey).build());
    }
}
