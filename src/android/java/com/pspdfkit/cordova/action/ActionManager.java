package com.pspdfkit.cordova.action;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

import static com.pspdfkit.cordova.Utilities.checkArgumentNotNull;

public class ActionManager {
  @NonNull private final List<Action> actions;

  public ActionManager(@NonNull final Action... actions) {
    this.actions = Arrays.asList(actions);
  }

  public boolean executeAction(
      @NonNull final String name, JSONArray args, CallbackContext callbackContext) {
    checkArgumentNotNull(name, "name");
    for (Action action : actions) {
      if (action.getName().equals(name)) {
        return action.exec(args, callbackContext);
      }
    }
    return false;
  }
}
