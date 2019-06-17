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

    /**
     * For communication with the JavaScript context, we keep a static reference to the current
     * activity.
     */
    private static CordovaPdfActivity currentActivity;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull
    private final DocumentListener listener =
        new SimpleDocumentListener() {
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
                    e.printStackTrace();
                }
            }
        };

    public static CordovaPdfActivity getCurrentActivity() {
        return currentActivity;
    }

    private void bindActivity(@NonNull final CordovaPdfActivity activity) {
        checkArgumentNotNull(activity, "activity");
        if (currentActivity != null) {
            throw new IllegalStateException(
                "EventDispatcher only supports a single CordovaPdfActivity at a time.");
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
        PdfFragment fragment = currentActivity.getPdfFragment();
        if (fragment != null) {
            fragment.removeDocumentListener(listener);
        }
        currentActivity = null;
    }

    public void addSubscription(Disposable disposable){
        compositeDisposable.add(disposable);
    }

    public void disposeSubscriptions(){
        compositeDisposable.dispose();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindActivity(this);
    }

    @Override
    protected void onStop() {
        disposeSubscriptions();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        releaseActivity();
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        // Notify JavaScript listeners that the activity was dismissed.
        EventDispatcher.getInstance().notifyActivityDismissed();
    }

    /** Dismisses this PDF activity. */
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
