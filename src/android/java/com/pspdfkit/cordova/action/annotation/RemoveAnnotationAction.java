package com.pspdfkit.cordova.action.annotation;

import androidx.annotation.NonNull;

import com.pspdfkit.annotations.AnnotationProvider;
import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.action.BasicAction;
import com.pspdfkit.document.PdfDocument;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Adds a new annotation to the current document using the Instant JSON Annotation payload:
 * https://pspdfkit.com/guides/ios/current/importing-exporting/instant-json/#instant-annotation-json-api
 */
public class RemoveAnnotationAction extends BasicAction {

  private static final int ARG_ANNOTATION_JSON = 0;

  public RemoveAnnotationAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
    String annotationJson = args.getJSONObject(ARG_ANNOTATION_JSON).toString();
    final PdfDocument document = CordovaPdfActivity.getCurrentActivity().getDocument();
    if (document != null) {
      AnnotationProvider annotationProvider = document.getAnnotationProvider();
      annotationProvider.addAnnotationToPage(
          annotationProvider.createAnnotationFromInstantJson(annotationJson)
      );
      callbackContext.success();
    }
  }
}
