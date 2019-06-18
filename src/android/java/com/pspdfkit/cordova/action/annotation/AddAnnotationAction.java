package com.pspdfkit.cordova.action.annotation;

import androidx.annotation.NonNull;

import com.pspdfkit.annotations.Annotation;
import com.pspdfkit.annotations.AnnotationProvider;
import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.action.BasicAction;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.ui.PdfFragment;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Adds a new annotation to the current document using the Instant JSON Annotation payload:
 * https://pspdfkit.com/guides/ios/current/importing-exporting/instant-json/#instant-annotation-json-api
 */
public class AddAnnotationAction extends BasicAction {

  private static final int ARG_ANNOTATION_JSON = 0;

  public AddAnnotationAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
    String annotationJson = args.getJSONObject(ARG_ANNOTATION_JSON).toString();
    CordovaPdfActivity pdfActivity = CordovaPdfActivity.getCurrentActivity();

    final PdfDocument document = pdfActivity.getDocument();
    if (document != null) {
      Annotation annotationFromInstantJson = document.getAnnotationProvider()
          .createAnnotationFromInstantJson(annotationJson);

      PdfFragment pdfFragment = pdfActivity.getPdfFragment();
      if(pdfFragment != null){
        pdfFragment.notifyAnnotationHasChanged(annotationFromInstantJson);
      }

      callbackContext.success();
    } else {
      callbackContext.error("No document is set");
    }
  }
}
