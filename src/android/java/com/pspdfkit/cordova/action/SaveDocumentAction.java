    package com.pspdfkit.cordova.action;

import com.pspdfkit.cordova.EventDispatcher;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;

public class SaveDocumentAction extends BasicAction {
    public SaveDocumentAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
        super(name, plugin);
    }

    @Override protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
        final boolean wasModified = EventDispatcher.getInstance().notifySaveDocument();
        final JSONObject response = new JSONObject();
        response.put("wasModified", wasModified);
        callbackContext.success(response);
    }
}
