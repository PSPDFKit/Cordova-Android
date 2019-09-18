package com.pspdfkit.cordova;

import android.os.Bundle;
import android.util.Log;

import com.pspdfkit.cordova.event.EventDispatcher;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.listeners.DocumentListener;
import com.pspdfkit.listeners.SimpleDocumentListener;
import com.pspdfkit.ui.PdfActivity;
import com.pspdfkit.ui.PdfFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.pspdfkit.cordova.Utilities.checkArgumentNotNull;

public class CordovaPdfActivity extends PdfActivity {

  public static final String LOG_TAG = "CordovaPdfActivity";

  /**
   * For communication with the JavaScript context, we keep a static reference to the current
   * activity.
   */
  private static CordovaPdfActivity currentActivity;
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();

  /** Singleton document listener for dispatching events from the current activity to JavaScript interface. */
  @NonNull
  private final static DocumentListener listener = new SimpleDocumentListener() {
    @Override
    public void onDocumentSaved(@NonNull PdfDocument document) {
      EventDispatcher.getInstance().sendEvent("onDocumentSaved", new JSONObject());
    }

    @Override
    public void onDocumentSaveFailed(
        @NonNull PdfDocument document, @NonNull Throwable exception) {
      try {
        final JSONObject data = new JSONObject();
        data.put("message", exception.getMessage());
        EventDispatcher.getInstance().sendEvent("onDocumentSaveFailed", data);
      } catch (JSONException e) {
        Log.e(LOG_TAG, "Error while creating JSON payload for 'onDocumentSaveFailed' event.", e);
      }
    }
  };

  public static CordovaPdfActivity getCurrentActivity() {
    return currentActivity;
  }

  private void bindActivity(@NonNull final CordovaPdfActivity activity) {
    checkArgumentNotNull(activity, "activity");

    final CordovaPdfActivity oldActivity = currentActivity;
    if (oldActivity != null) {
      releaseActivity();
      oldActivity.disposeSubscriptions();
    }

    currentActivity = activity;
    final PdfFragment pdfFragment = currentActivity.getPdfFragment();
    if (pdfFragment == null) {
      throw new IllegalStateException(
          "EventDispatcher only supports binding to activities that have a fragment instance.");
    }
    pdfFragment.addDocumentListener(listener);
  }

  private void releaseActivity() {
    // Release the current activity instance. However, we intentionally don't unregister the
    // document listener, so that asynchronous save events from saving the document after the
    // activity was destroyed, can still be relayed to the JavaScript interface.
    // Since the listener is static, we also don't leak the activity nor fragment.
    currentActivity = null;
  }

  /**
   * Adds given {@link Disposable} to disposable collection which will be automatically disposed as
   * a part of onDestroy during activity finishing process.
   *
   * @param disposable to add to the disposable collection.
   */
  public void addSubscription(Disposable disposable) {
    compositeDisposable.add(disposable);
  }

  private void disposeSubscriptions() {
    compositeDisposable.dispose();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    bindActivity(this);
  }

  @Override
  protected void onDestroy() {
    if(currentActivity.equals(this)) {
      releaseActivity();

      if (isFinishing()) {
        disposeSubscriptions();
      }
    }

    super.onDestroy();
  }

  @Override
  public void finish() {
    super.finish();
    // Notify JavaScript listeners that the activity was dismissed.
    EventDispatcher.getInstance().notifyActivityDismissed();
  }

  /**
   * Dismisses this PDF activity.
   */
  public void dismiss() {
    finish();
  }

  public boolean saveDocument() throws IOException {
    final PdfDocument document = getDocument();
    if (document != null) {
      return document.saveIfModified();
    } else {
      return false;
    }
  }
}
