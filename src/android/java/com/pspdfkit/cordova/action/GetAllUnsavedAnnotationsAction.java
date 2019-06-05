package com.pspdfkit.cordova.action;

import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.document.formatters.DocumentJsonFormatter;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GetAllUnsavedAnnotationsAction extends BasicAction {

    public GetAllUnsavedAnnotationsAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
        super(name, plugin);
    }

    @Override protected void execAction(JSONArray args, CallbackContext callbackContext) {
//        final boolean wasModified = CordovaPdfActivity.getCurrentActivity().saveDocument();
//        final JSONObject response = new JSONObject();
//        response.put("wasModified", wasModified);
//        callbackContext.success(response);
        PdfDocument document = CordovaPdfActivity.getCurrentActivity().getDocument();
        if (document != null) {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JSONObject response = DocumentJsonFormatter.exportDocumentJsonAsync(document, outputStream)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toSingle(() -> {
                    final String jsonString = outputStream.toString();
                    return new JSONObject(jsonString);
                }).blockingGet();
            callbackContext.success(response);
        }
    }
}
