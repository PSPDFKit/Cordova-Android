package com.pspdfkit.cordova.action.annotation;

import androidx.annotation.NonNull;

import com.pspdfkit.annotations.AnnotationProvider;
import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.action.BasicAction;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.ui.PdfFragment;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.pspdfkit.cordova.Utilities.getTypeFromString;

/**
 * Removes a given annotation from the current document. The annotation should be in the Instant
 * JSON format: https://pspdfkit.com/guides/ios/current/importing-exporting/instant-json/#instant-annotation-json-api
 */
public class RemoveAnnotationAction extends BasicAction {

  private static final int ARG_ANNOTATION_JSON = 0;

  public RemoveAnnotationAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
    JSONObject jsonObject = args.getJSONObject(ARG_ANNOTATION_JSON);
    // We can't create an annotation from the Instant JSON since that will attach it to the document,
    // so we manually grab the necessary values.
    int pageIndex = jsonObject.getInt("pageIndex");
    String name = jsonObject.getString("name");
    String type = jsonObject.getString("type");

    CordovaPdfActivity pdfActivity = CordovaPdfActivity.getCurrentActivity();

    // Capture the given callback and make sure it is retained in JavaScript too.
    final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
    result.setKeepCallback(true);
    callbackContext.sendPluginResult(result);

    final PdfDocument document = pdfActivity.getDocument();
    if (document != null) {
      AnnotationProvider annotationProvider = document.getAnnotationProvider();
      annotationProvider.getAllAnnotationsOfType(getTypeFromString(type), pageIndex, 1)
          .filter(annotationToFilter -> name.equals(annotationToFilter.getName()))
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnError(e -> callbackContext.error(e.getMessage()))
          .subscribe(annotation -> {
            annotationProvider.removeAnnotationFromPage(annotation);

            PdfFragment pdfFragment = pdfActivity.getPdfFragment();
            if (pdfFragment != null) {
              pdfFragment.notifyAnnotationHasChanged(annotation);
            }

            callbackContext.success();
          });
    } else {
      callbackContext.error("No document is set");
    }
  }
}
