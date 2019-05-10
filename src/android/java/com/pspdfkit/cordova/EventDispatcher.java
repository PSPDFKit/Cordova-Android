package com.pspdfkit.cordova;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.pspdfkit.cordova.Utilities.checkArgumentNotNull;

/**
 * Internal singleton class used by the PSPDFKit plugin to communicate between activities and the
 * JavaScript context.
 */
public class EventDispatcher {
  @Nullable private static EventDispatcher instance;
  @NonNull private final List<CordovaPdfActivity> activities = new CopyOnWriteArrayList<>();

  private EventDispatcher() {}

  public static synchronized EventDispatcher getInstance() {
    if (instance == null) {
      instance = new EventDispatcher();
    }
    return instance;
  }

  void registerActivity(@NonNull CordovaPdfActivity activity) {
    checkArgumentNotNull(activity, "activity");
    activities.add(activity);
  }

  void unregisterActivity(CordovaPdfActivity activity) {
    checkArgumentNotNull(activity, "activity");
    activities.remove(activity);
  }

  /**
   * Dismisses any previously registered PDF activity. This method returns {@code false} if no
   * activity was registered.
   */
  public boolean notifyDismissActivities() {
    for (CordovaPdfActivity activity : activities) {
      activity.onDismissRequested();
    }
    return false;
  }
}
