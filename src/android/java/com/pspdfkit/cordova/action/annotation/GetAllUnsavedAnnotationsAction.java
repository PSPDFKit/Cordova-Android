package com.pspdfkit.cordova.action.annotation;

import androidx.annotation.NonNull;

import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.action.BasicAction;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.document.formatters.DocumentJsonFormatter;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Asynchronously retrieves all unsaved changes to annotations.
 */
public class GetAllUnsavedAnnotationsAction extends BasicAction {

  public GetAllUnsavedAnnotationsAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) {
    PdfDocument document = CordovaPdfActivity.getCurrentActivity().getDocument();
    // Capture the given callback and make sure it is retained in JavaScript too.
    final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
    result.setKeepCallback(true);
    callbackContext.sendPluginResult(result);

    if (document != null) {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      DocumentJsonFormatter.exportDocumentJsonAsync(document, outputStream)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnError(e -> callbackContext.error(e.getMessage()))
          .subscribe(() -> {
            JSONObject response = new JSONObject(outputStream.toString());
            callbackContext.success(response);
          });
    } else {
      callbackContext.error("No document is set");
    }
  }
}
