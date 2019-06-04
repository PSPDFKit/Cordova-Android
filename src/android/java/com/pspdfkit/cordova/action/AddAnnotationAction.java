package com.pspdfkit.cordova.action;

import androidx.annotation.NonNull;

import com.pspdfkit.annotations.Annotation;
import com.pspdfkit.annotations.AnnotationProvider;
import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.document.PdfDocument;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddAnnotationAction extends BasicAction {

  private static final int ARG_ANNOTATION_JSON = 0;

  public AddAnnotationAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
    String annotationJson = args.getJSONObject(ARG_ANNOTATION_JSON).toString();
    PdfDocument document = CordovaPdfActivity.getCurrentActivity().getDocument();
    if (document != null) {
      AnnotationProvider annotationProvider = document.getAnnotationProvider();
      annotationProvider.addAnnotationToPage(
          annotationProvider.createAnnotationFromInstantJson(annotationJson)
      );
      callbackContext.success();
    }
  }
}
