package com.pspdfkit.cordova.action;

import com.pspdfkit.annotations.AnnotationProvider;
import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.document.formatters.DocumentJsonFormatter;
import com.pspdfkit.document.providers.DataProvider;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;

public class AddAnnotationsAction extends BasicAction {

  private static final int ARG_ANNOTATIONS_JSON = 0;

  public AddAnnotationsAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException, IOException {
    String annotationJson = args.getJSONObject(ARG_ANNOTATIONS_JSON).toString();
    PdfDocument document = CordovaPdfActivity.getCurrentActivity().getDocument();
    if (document != null) {
//      AnnotationProvider annotationProvider = document.getAnnotationProvider();
//      annotationProvider.addAnnotationToPage(
//          annotationProvider.createAnnotationFromInstantJson(annotationJson)
//      );

//      final DataProvider dataProvider = new (json);
//      DocumentJsonFormatter.importDocumentJson(fragment.getDocument(), dataProvider);
      callbackContext.success();
    }
  }
}
