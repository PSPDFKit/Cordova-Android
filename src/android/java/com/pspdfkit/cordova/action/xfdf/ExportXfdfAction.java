package com.pspdfkit.cordova.action.xfdf;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.pspdfkit.annotations.AnnotationType;
import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.action.BasicAction;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.document.formatters.XfdfFormatter;
import com.pspdfkit.forms.FormField;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.EnumSet;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Asynchronously imports a document JSON and applies its changes to the document.
 */
public class ExportXfdfAction extends BasicAction {

  private static final int ARG_XFDF_FILE_URI = 0;

  public ExportXfdfAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
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
      try {
        final OutputStream outputStream = cordovaPdfActivity.getContentResolver().openOutputStream(xfdfFileUri);
        if (outputStream == null) {
          callbackContext.error("Failed to open output stream during XFDF export");
          return;
        }

        cordovaPdfActivity.addSubscription(document.getAnnotationProvider().getAllAnnotationsOfType(EnumSet.allOf(AnnotationType.class))
            .toList()
            .flatMapCompletable(annotations -> XfdfFormatter.writeXfdfAsync(
                document,
                annotations,
                Collections.<FormField>emptyList(),
                outputStream
            )).doFinally(outputStream::close)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(e -> callbackContext.error(e.getMessage()))
            .subscribe(callbackContext::success)
        );
      } catch (FileNotFoundException exception) {
        callbackContext.error(exception.getMessage());
      }
    } else {
      callbackContext.error("No document is set");
    }
  }
}
