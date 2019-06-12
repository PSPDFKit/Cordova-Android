package com.pspdfkit.cordova.action;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;

import androidx.annotation.NonNull;

/**
 * Represents basic action requirement. Action should provide its name and have an execution block
 * with success {@code true} or failure {@code false} response.
 */
public interface Action {
  @NonNull String getName();

  boolean exec(JSONArray args, CallbackContext callbackContext);
}
