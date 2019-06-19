package com.pspdfkit.cordova.action.xfdf;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.pspdfkit.annotations.Annotation;
import com.pspdfkit.annotations.AnnotationProvider;
import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.action.BasicAction;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.document.formatters.XfdfFormatter;
import com.pspdfkit.document.providers.ContentResolverDataProvider;
import com.pspdfkit.ui.PdfFragment;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Asynchronously imports annotations from XFDF-file to the current document.
 */
public class ImportXfdfAction extends BasicAction {

  private static final int ARG_XFDF_FILE_URI = 0;

  public ImportXfdfAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
    final Uri xfdfFileUri = Uri.parse(args.getString(ARG_XFDF_FILE_URI));

    final CordovaPdfActivity cordovaPdfActivity = CordovaPdfActivity.getCurrentActivity();
    final PdfDocument document = cordovaPdfActivity.getDocument();

    // Capture the given callback and make sure it is retained in JavaScript too.
    final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
    result.setKeepCallback(true);
    callbackContext.sendPluginResult(result);

    if (document != null) {
      cordovaPdfActivity.addSubscription(
          XfdfFormatter.parseXfdfAsync(document, new ContentResolverDataProvider(xfdfFileUri))
              .subscribeOn(Schedulers.io())
              .map(annotations -> {
                // Annotations parsed from XFDF are not added to document automatically. We need to add them manually.
                AnnotationProvider annotationProvider = document.getAnnotationProvider();
                for (Annotation annotation : annotations) {
                  annotationProvider.addAnnotationToPage(annotation);
                }

                return true;
              })
              .observeOn(AndroidSchedulers.mainThread())
              .doOnError(e -> callbackContext.error(e.getMessage()))
              .subscribe(b -> callbackContext.success())
      );
    } else {
      callbackContext.error("No document is set");
    }
  }
}
