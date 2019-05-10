package com.pspdfkit.cordova;

import androidx.annotation.NonNull;

public final class Utilities {
  public static void checkArgumentNotNull(Object value, @NonNull String parameterName) {
    if (value == null) {
      throw new IllegalArgumentException(
          "Argument for parameter " + parameterName + " may not be null");
    }
  }

  public static RuntimeException propagate(Exception ex) {
    if (ex instanceof RuntimeException) return ((RuntimeException) ex);
    return new RuntimeException(ex);
  }
}
