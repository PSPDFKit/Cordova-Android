# Cordova Wrapper for PSPDFKit 4 for Android

The [PSPDFKit SDK](https://pspdfkit.com/pdf-sdk/) is a framework that allows you to view, annotate, sign, and fill PDF forms on iOS, Android, Windows, macOS, and Web. [PSPDFKit Instant](https://pspdfkit.com/instant/) adds real-time collaboration features to seamlessly share, edit, and annotate PDF documents.

PSPDFKit comes with open source wrappers for Cordova on both [iOS](https://pspdfkit.com/guides/ios/current/other-languages/apache-cordova-phonegap/) and [Android](https://pspdfkit.com/guides/android/current/other-languages/apache-cordova-phonegap/).

This plugin defines a global `PSPDFKit` object, which provides an API for viewing PDF documents with PSPDFKit for Android.

## Installation

	cordova plugin add https://github.com/PSPDFKit/Cordova-Android.git

## Example

```javascript
function showMyDocument() {
	PSPDFKit.showDocumentFromAssets('www/documents/myFile.pdf', {
		title: 'My PDF Document',
		page: 4,
		scrollDirection: PSPDFKit.PageScrollDirection.VERTICAL,
		scrollMode: PSPDFKit.ScrollMode.CONTINUOUS,
		useImmersiveMode: true,
		password: "my-document-password"
	});
}
```

## PSPDFKit.showDocument

Opens a document from the local device storage.

```javascript
PSPDFKit.showDocument(uri, options, success, fail)
```

## PSPDFKit.showDocumentFromAssets

Opens a document from the app's asset directory. To package a file within your app's assets, put it into the `www/` directory of your project.

```javascript
PSPDFKit.showDocumentFromAssets(assetPath, options, success, fail)
```

## Supported Platforms

* Android SDK API level 19+ / Android 4.4+ (KitKat)

## Options

You can use the `options` parameter to configure PSPDFKit. Here is a list of valid configuration options. You can omit option entries to use their default value, or pass in an empty options object `{}` to keep all default settings.

```javascript
var options {
	backgroundColor: '#EFEFEF', // hex-color of the page background
	disableAnnotationList: true, // hide annotation list (default: false)
	disableAnnotationNoteHinting: true, // hide small notes next to annotations that have a text set (default: false)
	disableBookmarkEditing: true, // hide bookmark editing (default: false)
	disableBookmarkList: true, // hide bookmark list (default: false)
	disableCopyPaste: true, // disable annotation copy/paste (default: false)
	disableDocumentEditor: true, // hide document editor (default: false)
	disableOutline: true, // hide the outline menu (default: false)
	disablePrinting: true, // hide option to print (default: false)
	disableShare: true, // hide share button (default: false)
	disableUndoRedo: true, // disable undo/redo system (default: false)      
	hidePageLabels: true, // hide page labels (if available in PDF) in page overlay and thumbnail grid (default: false)  
	hidePageNumberOverlay: false, // hide the overlay showing the current page (default: false)
	hideSettingsMenu: false, // hide the settings menu (default: false)
	thumbnailBarMode: PSPDFKit.ThumbnailBarMode.THUMBNAIL_BAR_MODE_DEFAULT, // show static thumbnail bar. Also valid: THUMBNAIL_BAR_MODE_DEFAULT, THUMBNAIL_BAR_MODE_SCROLLABLE
	hideThumbnailGrid: false, // hide the thumbnail grid menu (default: false)
	pageFitMode: PSPDFKit.PageFitMode.FIT_TO_WIDTH, // also valid: PSPDFKit.PageFitMode.FIT_TO_SCREEN
	scrollDirection: PSPDFKit.PageScrollDirection.VERTICAL, // also valid: PSPDFKit.PageScrollDirection.HORIZONTAL
	scrollMode: PSPDFKit.ScrollMode.CONTINUOUS, // also valid: PSPDFKit.ScrollMode.PER_PAGE
	invertColors: false, // invert rendered colors (default: false)
	toGrayscale: true, // render document in grayscale only (default: false)
	title: "My PSPDFKit app", // title displayed in the viewer action bar
	startZoomScale: 2.0, // initial zoom value (default: 1.0)
	maxZoomScale: 10.0, // maximum zoom factor when zooming into a page (default: 15.0)
	zoomOutBounce: false, // "bounce" animation when pinch-zooming out (default: true)
	page: 2, // initial page number (default: 0, i.e. the first page)
	useImmersiveMode: true, // activate Android's immersive app mode (default: false)
	disableSearch: false, // completely deactivate document search (default: false)
	searchType: PSPDFKit.SearchType.SEARCH_MODULAR, // also valid: PSPDFKit.SearchType.SEARCH_INLINE
	autosaveEnabled: true, // automatically save document changes on exit (default: true)
	annotationEditing: {
		enabled: true, // activate annotation editing (default: true)
		creatorName: 'John Doe' // author name written into new annotations (default: null)
	},
	password: "my-document-password" // use this to open encrypted PDF files
};

PSPDFKit.showDocumentFromAssets('www/documents/myFile.pdf', options);
```

## Quickstart Guide

Create a new Apache Cordova project from your command line using the [Apache Cordova Command-Line Interface (CLI)](https://cordova.apache.org/docs/en/5.1.1/index.html).

	$ cordova create pdfapp com.example.pdfapp PDF-App
	$ cd pdfapp

> Important: Your app's package name (in the above example `com.example.pdfapp`) has to match your PSPDFKit license name or PSPDFKit will throw an exception.

Add Android platform support to your project:

	$ cordova platform add android

Install the PSPDFKit plugin:

	$ cordova plugin add https://github.com/PSPDFKit/Cordova-Android.git

Copy the PSPDFKit library file (usually `pspdfkit-<version>.aar`, demo version also works) into your project. This example uses version `4.x.x` of the PSPDFKit library. The current working directory has to be your project directory:

	$ cp /path/to/pspdfkit-4.x.x.aar platforms/android/libs/

Set the minimum SDK version of your Android application to 19. To do so, add the `android-minSdkVersion` preference to the android platform configuration of your `config.xml`. It should now look like this:

	<platform name="android">
        <preference name="android-minSdkVersion" value="19" />
        <!-- more Android platform settings -->
    </platform>

Configure your PSPDFKit license key inside the `platforms/android/app/src/main/AndroidManifest.xml` (demo license key also works):

	<manifest>
		<application>
			<meta-data android:name="pspdfkit_license_key" android:value="..." />
		</application>
	</manifest>

You are now ready to build your app!

	$ cordova build

 ## Contributing

 Please ensure [you signed our CLA](https://pspdfkit.com/guides/web/current/miscellaneous/contributing/) so we can accept your contributions.
