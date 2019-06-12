package com.pspdfkit.cordova;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
}
