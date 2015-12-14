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
 * @param options   PSPDFKit configuration options.
 * @param success Success callback function.
 * @param error   Error callback function.
 */
exports.showDocument = function (uri, options, success, error) {
    options = options || {};
    exec(success, error, "PSPDFCordovaPlugin", "showDocument", [uri, options]);
};

/**
 * Opens the PSPDFActivity to show a document from the app's assets folder. This
 * method copies the file to the internal app directory on the device before showing
 * it.
 *
 * @param assetFile Relative path within the app's assets folder.
 * @param options   PSPDFKit configuration options.
 * @param success   Success callback function.
 * @param error     Error callback function.
 */
exports.showDocumentFromAssets = function (assetFile, options, success, error) {
    options = options || {};
    exec(success, error, "PSPDFCordovaPlugin", "showDocumentFromAssets", [assetFile, options]);
};

/**
 * Constant values used for setting the 'pageFitMode' option.
 */
exports.PageFitMode = {
  /**
   * Fit the into the screen.
   */
  FIT_TO_SCREEN: "FIT_TO_SCREEN",

  /**
   * Scale the page to fill the screen width (if possible).
   */
  FIT_TO_WIDTH: "FIT_TO_WIDTH"
};

/**
 * Constant values used for setting the 'pageDirection' option.
 */
exports.PageScrollDirection = {
  /**
   * Scroll horizontally.
   */
  HORIZONTAL: "HORIZONTAL",

  /**
   * Scroll vertically.
   */
  VERTICAL: "VERTICAL"
};

/**
 * Constant values used for setting the 'searchType' option.
 */
exports.SearchType = {
  /**
   * Modular search window.
   */
  SEARCH_MODULAR: "SEARCH_MODULAR",

  /**
   * Inline search (in action bar).
   */
  SEARCH_INLINE: "SEARCH_INLINE"
};
