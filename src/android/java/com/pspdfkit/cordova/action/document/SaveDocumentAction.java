package com.pspdfkit.cordova.action.document;

import androidx.annotation.NonNull;

import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.action.BasicAction;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Saves the document to original location if it has been changed. If there were no changes to the
 * document, the document file will not be modified.
 * Provides "wasModified" as a part of a successful response which will be equal to {@code true} if
 * the file was modified and changes were saved. {@code false} if there was nothing to save.
 */
public class SaveDocumentAction extends BasicAction {
  public SaveDocumentAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException, IOException {
    final boolean wasModified = CordovaPdfActivity.getCurrentActivity().saveDocument();
    final JSONObject response = new JSONObject();
    response.put("wasModified", wasModified);
    callbackContext.success(response);
  }
}
