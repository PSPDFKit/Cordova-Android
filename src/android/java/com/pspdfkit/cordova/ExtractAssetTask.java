/*
 * ExtractAssetTask.java
 *
 *   PSPDFKit
 *
 *   Copyright (c) 2015-2017 PSPDFKit GmbH. All rights reserved.
 *
 *   THIS SOURCE CODE AND ANY ACCOMPANYING DOCUMENTATION ARE PROTECTED BY INTERNATIONAL COPYRIGHT LAW
 *   AND MAY NOT BE RESOLD OR REDISTRIBUTED. USAGE IS BOUND TO THE PSPDFKIT LICENSE AGREEMENT.
 *   UNAUTHORIZED REPRODUCTION OR DISTRIBUTION IS SUBJECT TO CIVIL AND CRIMINAL PENALTIES.
 *   This notice may not be removed from this file.
 */

package com.pspdfkit.cordova;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Helper class for asynchronously pulling a PDF document from the app's assets into the internal
 * device storage.
 */
public class ExtractAssetTask extends AsyncTask<String, Void, Void> {

    public interface OnDocumentExtractedCallback {
        void onDocumentExtracted(File documentFile);
    }

    private final Context ctx;
    private final OnDocumentExtractedCallback callback;
    private File outputFile;

    public static void extract(String assetFile, Context ctx, OnDocumentExtractedCallback callback) {
        final File outputFile = new File(ctx.getFilesDir(), assetFile);

        if (outputFile.exists()) {
            callback.onDocumentExtracted(outputFile);
        } else {
            new ExtractAssetTask(ctx, callback)
                .execute(assetFile);
        }
    }

    private ExtractAssetTask(Context ctx, OnDocumentExtractedCallback callback) {
        this.ctx = ctx.getApplicationContext();
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(String... params) {

        for (String asset : params) {
            try {
                InputStream is = ctx.getResources().getAssets().open(asset);
                outputFile = new File(ctx.getFilesDir(), asset);
                outputFile.getParentFile().mkdirs();

                OutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile));
                byte[] buffer = new byte[4096];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }

                os.close();
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) callback.onDocumentExtracted(outputFile);
    }
}
