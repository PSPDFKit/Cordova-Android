package com.pspdfkit.cordova;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.pspdfkit.cordova.Utilities.checkArgumentNotNull;

/**
 * Internal singleton class used by the PSPDFKit plugin to communicate between activities and the
 * JavaScript context.
 */
public class EventDispatcher {
  @Nullable private static EventDispatcher instance;

  private CordovaPdfActivity activity;

  private EventDispatcher() {}

  @NonNull
  public static synchronized EventDispatcher getInstance() {
    if (instance == null) {
      instance = new EventDispatcher();
    }
    return instance;
  }

  void bindActivity(@NonNull final CordovaPdfActivity activity) {
    checkArgumentNotNull(activity, "activity");
    if (this.activity != null) {
      throw new IllegalStateException(
          "EventDispatcher only supports a single CordovaPdfActivty at a time.");
    }
    this.activity = activity;
  }

  void releaseActivity() {
    activity = null;
  }

  private void verifyBoundToActivity() {
    if (activity == null)
      throw new IllegalStateException(
          "Can't dispatch notifications on the activity. No activity was bound.");
  }

  /**
   * Dismisses any previously registered PDF activity. This method returns {@code false} if no
   * activity was registered.
   */
  public void notifyDismiss() {
    verifyBoundToActivity();
    activity.dismiss();
  }

  public boolean notifySaveDocument() throws IOException {
    verifyBoundToActivity();
    return activity.saveDocument();
  }
}
