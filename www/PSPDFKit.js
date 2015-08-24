/*
 * PSPDFKit.js
 *
 *   PSPDFKit
 *
 *   Copyright (c) 2015 PSPDFKit GmbH. All rights reserved.
 *
 *   THIS SOURCE CODE AND ANY ACCOMPANYING DOCUMENTATION ARE PROTECTED BY INTERNATIONAL COPYRIGHT LAW
 *   AND MAY NOT BE RESOLD OR REDISTRIBUTED. USAGE IS BOUND TO THE PSPDFKIT LICENSE AGREEMENT.
 *   UNAUTHORIZED REPRODUCTION OR DISTRIBUTION IS SUBJECT TO CIVIL AND CRIMINAL PENALTIES.
 *   This notice may not be removed from this file.
 */

var exec = require('cordova/exec');

/**
 * Opens the PSPDFActivity to show a document from the local device file system.
 *
 * @param uri     The local filesystem URI of the document to show.
 * @param success Success callback function.
 * @param error   Error callback function.
 */
exports.showDocument = function (uri, success, error) {
    exec(success, error, "PSPDFCordovaPlugin", "showDocument", [uri]);
};

/**
 * Opens the PSPDFActivity to show a document from the app's assets folder. This
 * method copies the file to the internal app directory on the device before showing
 * it.
 *
 * @param assetFile Relative path within the app's assets folder.
 * @param success   Success callback function.
 * @param error     Error callback function.
 */
exports.showDocumentFromAssets = function (assetFile, success, error) {
    exec(success, error, "PSPDFCordovaPlugin", "showDocumentFromAssets", [assetFile]);
};