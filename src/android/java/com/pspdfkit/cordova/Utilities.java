package com.pspdfkit.cordova;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Utilities {
  public static void checkArgumentNotNull(Object value, @NonNull String parameterName) {
    if (value == null) {
      throw new IllegalArgumentException(
          "Argument for parameter " + parameterName + " may not be null");
    }
  }

  public static void checkAtLeast(final int min, final int value, @NonNull final String message) {
    if (value < min) {
      throw new IllegalArgumentException(message);
    }
  }

  public static RuntimeException propagate(Exception ex) {
    if (ex instanceof RuntimeException) return ((RuntimeException) ex);
    return new RuntimeException(ex);
  }

  /**
   * Ensures that Javascript "null" strings are correctly converted to javas <code>null</code>.
   */
  @Nullable
  public static String convertJsonNullToJavaNull(@Nullable String value) {
    if (value == null || value.equals("null")) return null;
    return value;
  }
}
