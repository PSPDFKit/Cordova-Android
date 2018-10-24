# Cordova Wrapper for PSPDFKit 4 for Android

The [PSPDFKit SDK](https://pspdfkit.com/pdf-sdk/) is a framework that allows you to view, annotate, sign, and fill PDF forms on iOS, Android, Windows, macOS, and Web. [PSPDFKit Instant](https://pspdfkit.com/instant/) adds real-time collaboration features to seamlessly share, edit, and annotate PDF documents.

PSPDFKit comes with open source wrappers for Cordova on both [iOS](https://pspdfkit.com/guides/ios/current/other-languages/apache-cordova-phonegap/) and [Android](https://pspdfkit.com/guides/android/current/other-languages/apache-cordova-phonegap/). These wrappers also work for being used with the [Ionic](https://ionicframework.com/) framework.

This plugin defines a global `PSPDFKit` object, which provides an API for viewing PDF documents with PSPDFKit for Android.

## Installation

#### Installation in a Cordova app

```shell
$ cordova plugin add https://github.com/PSPDFKit/Cordova-Android.git
```

#### Installation in an Ionic app


```shell
$ ionic cordova plugin add https://github.com/PSPDFKit/Cordova-Android.git
```


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
* Cordova Android 7+.

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

## Troubleshooting

### PdfActivity missing in AndroidManifest.xml

In some cases, it might occur that Cordova fails adding the required `<activity/>` entry to your app's `AndroidManifest.xml`. If this is the case, you will see an error message like this when trying to show a PDF document:

```
05-23 21:55:40.214 20912-20982/com.example.app E/PluginManager: Uncaught exception from plugin 
android.content.ActivityNotFoundException: Unable to find explicit activity class {com.example.app/com.pspdfkit.ui.PdfActivity}; have you declared this activity in your AndroidManifest.xml? 
at android.app.Instrumentation.checkStartActivityResult(Instrumentation.java:1854) 
at android.app.Instrumentation.execStartActivity(Instrumentation.java:1545) 
at android.app.Activity.startActivityForResult(Activity.java:4283) 
at org.apache.cordova.CordovaActivity.startActivityForResult(CordovaActivity.java:342) 
at android.app.Activity.startActivityForResult(Activity.java:4230) 
at android.app.Activity.startActivity(Activity.java:4567) 
at android.app.Activity.startActivity(Activity.java:4535) 
at com.pspdfkit.ui.PdfActivity.showDocument(SourceFile:113) 
at com.pspdfkit.cordova.PSPDFKitCordovaPlugin.showDocumentForUri(PSPDFKitCordovaPlugin.java:253) 
at com.pspdfkit.cordova.PSPDFKitCordovaPlugin.showDocument(PSPDFKitCordovaPlugin.java:227) 
at com.pspdfkit.cordova.PSPDFKitCordovaPlugin.execute(PSPDFKitCordovaPlugin.java:85) 
at org.apache.cordova.CordovaPlugin.execute(CordovaPlugin.java:98) 
at org.apache.cordova.PluginManager.exec(PluginManager.java:132) 
at org.apache.cordova.CordovaBridge.jsExec(CordovaBridge.java:57) 
at org.apache.cordova.engine.SystemExposedJsApi.exec(SystemExposedJsApi.java:41) 
at org.chromium.base.SystemMessageHandler.nativeDoRunLoopOnce(Native Method) 
at org.chromium.base.SystemMessageHandler.handleMessage(SystemMessageHandler.java:9) 
at android.os.Handler.dispatchMessage(Handler.java:102) 
at android.os.Looper.loop(Looper.java:158) 
at android.os.HandlerThread.run(HandlerThread.java:61)
```

To fix the issue, you need to manually add following entry to your `AndroidManifest.xml`, usually located at `<your-project>/platforms/android/app/src/main/AndroidManifest.xml`: 

```AndroidManifest.xml
<manifest ...>
    <application ...>
        ...
	
	<!-- Add this entry if it is missing inside the manifest file. -->
        <activity android:name="com.pspdfkit.ui.PdfActivity" android:theme="@style/PSPDFKit.Theme" android:windowSoftInputMode="adjustNothing" />
    </application>
</manifest>
```

The entry needs to be inside the existing `<application></application>` tags and should be added without removing any other entries that already exist. After recompiling and re-running the application, the error should be gone and PDF files should be properly displayed.

### Locking the device orientation inside a PDF

If you want to lock the device orientation to, for example, portrait mode, you can do this by specifying the `android:screenOrientation` attribute on the `PdfActivity` inside your app's `AndroidManifest.xml`. However, since Cordova will always regenerate the `AndroidManifest.xml` at build time, you can't manually change the existing activity entry in the XML.

1. Specify the extra `android:screenOrientation` attribute for `PdfActivity` inside your app's `config.xml`:

	```config.xml
	<widget id="com.example.app" ...>
	    ...
	    <!-- Add this to the end of your configuration to set the screen orientation inside PDFs. -->
	    <edit-config file="AndroidManifest.xml" mode="merge" target="/manifest/application/activity[@android:name='com.pspdfkit.ui.PdfActivity']">
		<!-- This will lock the screen orientation to portrait mode. -->
		<activity android:screenOrientation="portrait" />
	    </edit-config>
	</widget>
	```

    For the complete list of screen orientations supported by Android, check out the official Android documentation: https://developer.android.com/guide/topics/manifest/activity-element#screen

2. From the command line, run `cordova prepare` to update the `AndroidManifest.xml`. 
3. Open the `platforms/android/app/src/main/AndroidManifest.xml` file and ensure that the `android:screenOrientation` attribute was properly added to the existing `PdfActivity` declaration.

## Contributing

Please ensure [you signed our CLA](https://pspdfkit.com/guides/web/current/miscellaneous/contributing/) so we can accept your contributions.
