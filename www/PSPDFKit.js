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

exports.showDocument = function (uri, success, error) {
    exec(success, error, "PSPDFKitCordovaPlugin", "showDocument", [uri]);
};

exports.showDocumentFromAssets = function (assetFile, success, error) {
    exec(success, error, "PSPDFKitCordovaPlugin", "showDocumentFromAssets", [assetFile]);
};