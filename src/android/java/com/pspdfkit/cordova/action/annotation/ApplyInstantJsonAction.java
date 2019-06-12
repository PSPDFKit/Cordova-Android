package com.pspdfkit.cordova.action.annotation;

import androidx.annotation.NonNull;

import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.action.BasicAction;
import com.pspdfkit.cordova.provider.DocumentJsonDataProvider;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.document.formatters.DocumentJsonFormatter;
import com.pspdfkit.document.providers.DataProvider;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Asynchronously imports a document JSON and applies its changes to the document.
 */
public class ApplyInstantJsonAction extends BasicAction {

  private static final int ARG_ANNOTATIONS_JSON = 0;

  public ApplyInstantJsonAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
    JSONObject annotationsJson = args.getJSONObject(ARG_ANNOTATIONS_JSON);
    PdfDocument document = CordovaPdfActivity.getCurrentActivity().getDocument();

    // Capture the given callback and make sure it is retained in JavaScript too.
    final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
    result.setKeepCallback(true);
    callbackContext.sendPluginResult(result);

    if (document != null) {
      final DataProvider dataProvider = new DocumentJsonDataProvider(annotationsJson);
      DocumentJsonFormatter.importDocumentJsonAsync(document, dataProvider)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnError(e -> callbackContext.error(e.getMessage()))
          .subscribe(() -> callbackContext.success());
    } else {
      callbackContext.error("No document is set");
    }
  }
}
