/*
 * PSPDFKit.js
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

var exec = require('cordova/exec');

/**
 * Retrieves a named property from the given target object while removing the property from the object.
 */
function getPropertyAndUnset(target, name) {
    var value = target.hasOwnProperty(name) ? target[name] : null;
    delete target[name];
    return value;
};

/**
 * Opens the PSPDFActivity to show a document from the local device file system.
 *
 * @param uri     The local filesystem URI of the document to show.
 * @param options   PSPDFKit configuration options.
 * @param success Success callback function.
 * @param error   Error callback function.
 */
exports.showDocument = function (uri, options, success, error) {
    options = options || {};
    var password = getPropertyAndUnset(options, "password");
    exec(success, error, "PSPDFKitCordovaPlugin", "showDocument", [uri, options, password]);
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
    options = options || {};
    var password = getPropertyAndUnset(options, "password");
    exec(success, error, "PSPDFKitCordovaPlugin", "showDocumentFromAssets", [assetFile, options, password]);
};

/**
 * Constant values used for setting the `scrollMode` option.
 */
exports.ScrollMode = {
  /**
   * Paginated scrolling, will always snap to a page when user stops dragging or flinging.
   */
  PER_PAGE: "PER_PAGE",

  /**
   * Continuous/smooth scrolling, will stop in whatever position the user stopped dragging.
   */
  CONTINUOUS: "CONTINUOUS"
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

/**
 * Constant values used for setting the 'thumbnailBarMode' option.
 */
exports.ThumbnailBarMode = {
  /**
   * Default (static) thumbnail bar.
   */
  THUMBNAIL_BAR_MODE_DEFAULT: "THUMBNAIL_BAR_MODE_DEFAULT",
  /**
   * Scrollable thumbnail bar.
   */
  THUMBNAIL_BAR_MODE_SCROLLABLE: "THUMBNAIL_BAR_MODE_SCROLLABLE",
  /**
   * No thumbnail bar.
   */
  THUMBNAIL_BAR_MODE_NONE: "THUMBNAIL_BAR_MODE_NONE"
};
