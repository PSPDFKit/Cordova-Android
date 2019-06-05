package com.pspdfkit.cordova.action;

import androidx.annotation.NonNull;

import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.provider.DocumentJsonDataProvider;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.document.formatters.DocumentJsonFormatter;
import com.pspdfkit.document.providers.DataProvider;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ApplyInstantJsonAction extends BasicAction {

  private static final int ARG_ANNOTATIONS_JSON = 0;

  public ApplyInstantJsonAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException, IOException {
    JSONObject annotationsJson = args.getJSONObject(ARG_ANNOTATIONS_JSON);
    PdfDocument document = CordovaPdfActivity.getCurrentActivity().getDocument();
    if (document != null) {
      final DataProvider dataProvider = new DocumentJsonDataProvider(annotationsJson);
      DocumentJsonFormatter.importDocumentJson(document, dataProvider);
      callbackContext.success();
    }
  }
}
