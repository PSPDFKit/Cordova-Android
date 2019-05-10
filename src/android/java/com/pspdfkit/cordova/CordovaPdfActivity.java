package com.pspdfkit.cordova;

import java.io.IOException;

public interface CordovaPdfActivity {
    void onDismissRequested();

    void saveDocument() throws IOException;
}
