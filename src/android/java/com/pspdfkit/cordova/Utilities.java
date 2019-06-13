package com.pspdfkit.cordova;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pspdfkit.annotations.AnnotationType;

import java.util.EnumSet;

/**
 * Contains commonly used utility/helper methods.
 */
public final class Utilities {
  /**
   * Checks that given value isn't null. Will produce `IllegalArgumentException` in case if value is null.
   *
   * @param value         to check for null
   * @param parameterName the name of parameter for which value is checked for. Will be used to
   *                      construct `IllegalArgumentException` message.
   */
  public static void checkArgumentNotNull(Object value, @NonNull String parameterName) {
    if (value == null) {
      throw new IllegalArgumentException(
          "Argument for parameter " + parameterName + " may not be null");
    }
  }

  /**
   * Wraps given exception into an instance of `RuntimeException` for it to be properly propagated.
   *
   * @param exception exception which supposed to be propagated.
   * @return given exception wrapped into `RuntimeException`.
   */
  public static RuntimeException propagate(Exception exception) {
    if (exception instanceof RuntimeException) return ((RuntimeException) exception);
    return new RuntimeException(exception);
  }

  /**
   * Ensures that JavaScript "null" strings are correctly converted to Java's {@code null}.
   */
  @Nullable
  public static String convertJsonNullToJavaNull(@Nullable String value) {
    if (value == null || value.equals("null")) return null;
    return value;
  }

  public static EnumSet<AnnotationType> getTypeFromString(@Nullable String type) {
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
