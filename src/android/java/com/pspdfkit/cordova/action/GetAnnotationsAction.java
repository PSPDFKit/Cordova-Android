package com.pspdfkit.cordova.action;

import com.pspdfkit.annotations.Annotation;
import com.pspdfkit.annotations.AnnotationType;
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
import java.util.EnumSet;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.pspdfkit.cordova.Utilities.convertJsonNullToJavaNull;

public class GetAnnotationsAction extends BasicAction {

  private static final int ARG_PAGE_INDEX = 0;
  private static final int ARG_ANNOTATION_TYPE = 1;


  public GetAnnotationsAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override protected void execAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
    PdfDocument document = CordovaPdfActivity.getCurrentActivity().getDocument();
    if (document != null) {
      List<String> instantJsonAnnotations = document.getAnnotationProvider().getAllAnnotationsOfType(
          getTypeFromString(convertJsonNullToJavaNull(args.getString(ARG_ANNOTATION_TYPE))),
          args.getInt(ARG_PAGE_INDEX),
          1
      ).map(Annotation::toInstantJson).toList().blockingGet();

      JSONArray response = new JSONArray(instantJsonAnnotations);
      callbackContext.success(response);
    }
  }

  private EnumSet<AnnotationType> getTypeFromString(@Nullable String type) {
    if (type == null) {
      return EnumSet.allOf(AnnotationType.class);
    }
    if ("pspdfkit/ink".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.INK);
    }
    if ("pspdfkit/link".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.LINK);
    }
    if ("pspdfkit/markup/highlight".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.HIGHLIGHT);
    }
    if ("pspdfkit/markup/squiggly".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.SQUIGGLY);
    }
    if ("pspdfkit/markup/strikeout".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.STRIKEOUT);
    }
    if ("pspdfkit/markup/underline".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.UNDERLINE);
    }
    if ("pspdfkit/note".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.NOTE);
    }
    if ("pspdfkit/shape/ellipse".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.CIRCLE);
    }
    if ("pspdfkit/shape/line".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.LINE);
    }
    if ("pspdfkit/shape/polygon".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.POLYGON);
    }
    if ("pspdfkit/shape/polyline".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.POLYLINE);
    }
    if ("pspdfkit/shape/rectangle".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.SQUARE);
    }
    if ("pspdfkit/text".equalsIgnoreCase(type)) {
      return EnumSet.of(AnnotationType.FREETEXT);
    }
    return EnumSet.noneOf(AnnotationType.class);
  }
}
