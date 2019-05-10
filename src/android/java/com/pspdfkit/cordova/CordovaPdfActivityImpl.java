package com.pspdfkit.cordova;

import android.os.Bundle;

import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.ui.PdfActivity;

import java.io.IOException;

public class CordovaPdfActivity extends PdfActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventDispatcher.getInstance().bindActivity(this);
  }

  @Override
  protected void onDestroy() {
    EventDispatcher.getInstance().releaseActivity();
    super.onDestroy();
  }

  @Override public void finish() {
    super.finish();
    EventDispatcher.getInstance().notifyActivityDismissed();
  }

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
