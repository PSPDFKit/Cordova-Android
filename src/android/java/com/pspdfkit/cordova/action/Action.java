package com.pspdfkit.cordova.action;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;

import androidx.annotation.NonNull;

public interface Action {
  @NonNull String getName();

  boolean exec(JSONArray args, CallbackContext callbackContext);
}
