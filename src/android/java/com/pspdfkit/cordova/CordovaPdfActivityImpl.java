package com.pspdfkit.cordova;

import android.os.Bundle;

import com.pspdfkit.ui.PdfActivity;

public class CordovaPdfActivityImpl extends PdfActivity implements CordovaPdfActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      EventDispatcher.getInstance().registerActivity(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
      EventDispatcher.getInstance().unregisterActivity(this);
  }

  @Override
  public void onDismissRequested() {
    finish();
  }
}
