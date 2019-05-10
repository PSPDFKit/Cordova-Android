package com.pspdfkit.cordova.action;

import android.net.Uri;

import com.pspdfkit.configuration.activity.PdfActivityConfiguration;
import com.pspdfkit.cordova.ExtractAssetTask;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;

import org.apache.cordova.CallbackContext;
import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShowDocumentFromAssetsAction extends ShowDocumentAction {
  public ShowDocumentFromAssetsAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  @Override
  protected void showDocumentFromUri(
      @NotNull final Uri uri,
      @Nullable final String password,
      @NotNull final PdfActivityConfiguration configuration,
      @NotNull final CallbackContext callbackContext) {
    final String assetPath = uri.getPath();
    ExtractAssetTask.extract(
        assetPath,
        getPlugin().cordova.getActivity(),
        documentFile -> {
          if (documentFile != null) {
            super.showDocumentFromUri(
                Uri.fromFile(documentFile), password, configuration, callbackContext);
            callbackContext.success();
          } else {
            callbackContext.error("Could not load '" + assetPath + "' from the assets.");
          }
        });
  }
}
