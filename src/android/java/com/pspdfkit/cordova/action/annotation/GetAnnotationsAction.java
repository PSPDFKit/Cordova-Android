package com.pspdfkit.cordova.action.annotation;

import androidx.annotation.NonNull;

import com.pspdfkit.annotations.Annotation;
import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.action.BasicAction;
import com.pspdfkit.document.PdfDocument;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.pspdfkit.cordova.Utilities.convertJsonNullToJavaNull;
import static com.pspdfkit.cordova.Utilities.getAnnotationTypeFromString;

/**
 * Asynchronously retrieves all annotations of the given type from the given page.
 */
public class GetAnnotationsAction extends BasicAction {

  private static final int ARG_PAGE_INDEX = 0;
  private static final int ARG_ANNOTATION_TYPE = 1;

  public GetAnnotationsAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
    CordovaPdfActivity cordovaPdfActivity = CordovaPdfActivity.getCurrentActivity();
    final PdfDocument document = cordovaPdfActivity.getDocument();

    // Capture the given callback and make sure it is retained in JavaScript too.
    final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
    result.setKeepCallback(true);
    callbackContext.sendPluginResult(result);

    if (document != null) {
      cordovaPdfActivity.addSubscription(
          document.getAnnotationProvider().getAllAnnotationsOfType(
              getAnnotationTypeFromString(convertJsonNullToJavaNull(args.getString(ARG_ANNOTATION_TYPE))),
              args.getInt(ARG_PAGE_INDEX),
              1)
              .observeOn(Schedulers.io())
              .map(Annotation::toInstantJson)
              .toList()
              .observeOn(AndroidSchedulers.mainThread())
              .doOnError(e -> callbackContext.error(e.getMessage()))
              .subscribe(strings -> {
                JSONArray response = new JSONArray(strings);
                callbackContext.success(response);
              })
      );
    } else {
      callbackContext.error("No document is set");
    }
  }
}
