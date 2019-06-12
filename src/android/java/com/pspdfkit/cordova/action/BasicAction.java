package com.pspdfkit.cordova.action;

import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;

import org.apache.cordova.CallbackContext;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import androidx.annotation.NonNull;

import static com.pspdfkit.cordova.Utilities.checkArgumentNotNull;
import static com.pspdfkit.cordova.Utilities.propagate;

/**
 * Represents a simple abstract action which can be executed by {@link PSPDFKitCordovaPlugin}.
 */
public abstract class BasicAction implements Action {
  @NonNull private final String name;
  @NonNull private final PSPDFKitCordovaPlugin plugin;

  public BasicAction(@NonNull final String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    checkArgumentNotNull(name, "name");
    this.name = name;
    this.plugin = plugin;
  }

  @NotNull
  @Override
  public String getName() {
    return name;
  }

  @NonNull
  protected PSPDFKitCordovaPlugin getPlugin() {
    return plugin;
  }

  @Override
  public boolean exec(JSONArray args, CallbackContext callbackContext) {
    try {
      execAction(args, callbackContext);
    } catch (Exception ex) {
      throw propagate(ex);
    }
    return true;
  }

  /**
   * Internal wrapper for {@link #exec(JSONArray, CallbackContext)} for easier exception handling.
   * Any exceptions thrown by this are automatically propagated as runtime exceptions.
   */
  protected abstract void execAction(JSONArray args, CallbackContext callbackContext)
      throws Exception;
}
