# Cordova Wrapper for PSPDFKit 5 for Android

The [PSPDFKit SDK](https://pspdfkit.com/pdf-sdk/) is a framework that allows you to view, annotate, sign, and fill PDF forms on iOS, Android, Windows, macOS, and Web. [PSPDFKit Instant](https://pspdfkit.com/instant/) adds real-time collaboration features to seamlessly share, edit, and annotate PDF documents.

PSPDFKit comes with open source wrappers for Cordova on both [iOS](https://pspdfkit.com/guides/ios/current/other-languages/apache-cordova-phonegap/) and [Android](https://pspdfkit.com/guides/android/current/other-languages/apache-cordova-phonegap/). These wrappers also work for being used with the [Ionic](https://ionicframework.com/) framework.

This plugin defines a global `PSPDFKit` object, which provides an API for viewing PDF documents with PSPDFKit for Android.

## Installation

### Installation in a Cordova app

```shell
cordova plugin add https://github.com/PSPDFKit/Cordova-Android.git
```

### Installation in an Ionic app

```shell
ionic cordova plugin add https://github.com/PSPDFKit/Cordova-Android.git
```

## Example

```javascript
function showMyDocument() {
  PSPDFKit.showDocumentFromAssets("www/documents/myFile.pdf", {
    title: "My PDF Document",
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
PSPDFKit.showDocument(uri, options, success, fail);
```

## PSPDFKit.showDocumentFromAssets

Opens a document from the app's asset directory. To package a file within your app's assets, put it into the `www/` directory of your project.

```javascript
PSPDFKit.showDocumentFromAssets(assetPath, options, success, fail);
```

## Supported Platforms

- Android SDK API level 19+ / Android 4.4+ (KitKat)
- Cordova Android 7+.

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
    disableUndoRedo: true, // disable undo/redo system (default: false)
    hidePageLabels: true, // hide page labels (if available in PDF) in page overlay and thumbnail grid (default: false)
    hidePageNumberOverlay: false, // hide the overlay showing the current page (default: false)
    hideSettingsMenu: false, // hide the settings menu (default: false)
    thumbnailBarMode: PSPDFKit.ThumbnailBarMode.THUMBNAIL_BAR_MODE_DEFAULT, // show static thumbnail bar. Also valid: THUMBNAIL_BAR_MODE_DEFAULT, THUMBNAIL_BAR_MODE_SCROLLABLE
    hideThumbnailGrid: false, // hide the thumbnail grid menu (default: false)
    pageFitMode: PSPDFKit.PageFitMode.FIT_TO_WIDTH, // also valid: PSPDFKit.PageFitMode.FIT_TO_SCREEN
    scrollDirection: PSPDFKit.PageScrollDirection.VERTICAL, // also valid: PSPDFKit.PageScrollDirection.HORIZONTAL
    scrollMode: PSPDFKit.ScrollMode.CONTINUOUS, // also valid: PSPDFKit.ScrollMode.PER_PAGE
    // Configures which share actions should be visible in the user interface. (default: all enabled)
    shareFeatures: [
        /** Document sharing inside the activity. */
        PSPDFKit.ShareFeatures.DOCUMENT_SHARING,
        /** Sharing of embedded files (on file annotations). */
        PSPDFKit.ShareFeatures.EMBEDDED_FILE_SHARING,
        /** Sharing of text from selected free text annotations. */
        PSPDFKit.ShareFeatures.FREE_TEXT_ANNOTATION_SHARING,
        /** Sharing of selected image annotations. */
        PSPDFKit.ShareFeatures.IMAGE_SHARING,
        /** Sharing of text from selected note annotations. */
        PSPDFKit.ShareFeatures.NOTE_ANNOTATION_SHARING,
        /** Sharing of text from annotation contents or comments. */
        PSPDFKit.ShareFeatures.NOTE_EDITOR_CONTENT_SHARING,
        /** Sharing of selected text. */
        PSPDFKit.ShareFeatures.TEXT_SELECTION_SHARING
    ],
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

## Quickstart Guide Cordova

Create a new Apache Cordova project from your command line using the [Apache Cordova Command-Line Interface (CLI)](https://cordova.apache.org/docs/en/5.1.1/index.html).

    cordova create pdfapp com.example.pdfapp PDF-App
    cd pdfapp

> Important: Your app's package name (in the above example `com.example.pdfapp`) has to match your PSPDFKit license name or PSPDFKit will throw an exception. If you don't have a license yet, you can request an evaluation license of PSPDFKit at https://pspdfkit.com/try.

Add Android platform support to your project. Right now, PSPDFKit 5.1.1 for Cordova is only compatible with the nightly build of `cordova-android` (until https://github.com/apache/cordova-android/pull/507 is available on the stable npm channel):

    cordova platform add android@8.0.0-nightly.2018.11.23.ef243418

Install the PSPDFKit plugin:

    cordova plugin add https://github.com/PSPDFKit/Cordova-Android.git

Setup the minimum and target SDK versions of the Android SDK inside your Android app's `platforms/android/gradle.properties` file. Also, since PSPDFKit 5 for Android uses Java 8, you need to enable D8 for your builds:

```properties
cdvCompileSdkVersion=android-28
cdvBuildToolsVersion=28.0.3
android.enableD8=true
```

Next you need to setup your PSPDFKit license key and Maven password. If you don't have a license key or Maven password yet, you can get them by requesting an evaluation version of PSPDFKit at https://pspdfkit.com/try. Inside your Android app's `platforms/android/local.properties` file, specify the `pspdfkit.password` and `pspdfkit.license` properties:

```properties
pspdfkit.password=YOUR_PASSWORD
pspdfkit.license=LICENSE_STRING
```

> Note: If you're already a customer then please make sure that the package ID matches with your bundle ID that's assigned to your license (e.g. com.ionic.test). You can check this in your `AndroidManifest.xml` by searching for `package`. If you are using a trial license then you don't have to worry about that.

Now open your `index.js` file located in `www/js/` and paste the below code into the `receivedEvent: function(id)` function. For this to work you need to create a folder called `documents` in `wwww` and paste a PDF in this folder.

```javascript
PSPDFKit.showDocumentFromAssets("www/documents/A.pdf", {
  title: "My PDF Document",
  page: 0,
  scrollDirection: PSPDFKit.PageScrollDirection.VERTICAL,
  scrollMode: PSPDFKit.ScrollMode.CONTINUOUS,
  useImmersiveMode: true
});
```

You are now ready to build and test your app!

```shell
cordova build
cordova run
```

## Quickstart Guide Ionic

For more information regarding the Ionic installation you can check out the [official Ionic installation guide](https://ionicframework.com/docs/v1/guide/installation.html).

Create a new Ionic project from the command line using the [Ionic Command-Line Interface (CLI)](https://ionicframework.com/docs/cli/start/) .

```shell
ionic start todo blank --type ionic1
```

It will then ask you if you want to integrate your new app with Cordova, answer with yes. After the process is finished change to the newly created Ionic project directory.

```shell
cd todo
```

Add Android platform support. Right now, PSPDFKit 5.1.1 for Cordova is only compatible with the nightly build of `cordova-android` (until https://github.com/apache/cordova-android/pull/507 is available on the stable npm channel):

```shell
ionic cordova platform add android@8.0.0-nightly.2018.11.23.ef243418
```

Install the PSPDFKit plugin:

```shell
ionic cordova plugin add https://github.com/PSPDFKit/Cordova-Android.git
```

Setup the minimum and target SDK versions of the Android SDK inside your Android app's `platforms/android/gradle.properties` file. Also, since PSPDFKit 5 for Android uses Java 8, you need to enable D8 for your builds:

```properties
cdvCompileSdkVersion=android-28
cdvBuildToolsVersion=28.0.3
android.enableD8=true
```

Next you need to setup your PSPDFKit license key and Maven password. If you don't have a license key or Maven password yet, you can get them by requesting an evaluation version of PSPDFKit at https://pspdfkit.com/try. Specify the `pspdfkit.password` and `pspdfkit.license` properties inside your Android app's `platforms/android/local.properties` file, create the file if it does not exist:

```properties
pspdfkit.password=YOUR_PASSWORD
pspdfkit.license=LICENSE_STRING
```

> Note: If you're already a customer then please make sure that the package ID matches with your bundle ID that's assigned to your license (e.g. com.ionic.test). You can check this in your `AndroidManifest.xml` by searching for `package`. If you are using a trial license then you don't have to worry about that.

Now open your `app.js` file located in `www/js/` and paste the below code into the `$ionicPlatform.ready(function() {}` function. For this to work you need to create a folder called `documents` in `wwww` and paste a PDF in this folder.

```javascript
PSPDFKit.showDocumentFromAssets("www/documents/Document.pdf", {
  title: "My PDF Document",
  page: 0,
  scrollDirection: PSPDFKit.PageScrollDirection.VERTICAL,
  scrollMode: PSPDFKit.ScrollMode.CONTINUOUS,
  useImmersiveMode: true
});
```

Now you need to build the app to see if everything works correctly:

```shell
ionic cordova build
```

And finally to run the app on a real device simply enter:

```shell
ionic cordova run android
```

Or if you want to run the app on a simulator you can use:

```shell
ionic cordova emulate android
```

### What your project structure should look like

Below is a screenshot of how the project structure should look like if it's a working project. All folders with important files that need to be adapted are open in the screenshot. If it doesn't look like this then there is a high chance that the guide wasn't properly followed which will also lead to the project not working.

```
.
├── platforms
│   ├── android
│   │   ├── app
│   │   │   └── src
│   │   │       └── main
│   │   │           └── AndroidManifest.xml
│   │   ├── pspdfkit-cordova-android
│   │   │   └── starter-pspdfkit.gradle
│   │   ├── gradle.properties
│   │   └── local.properties
└── www
    ├── documents
    │   └── A.pdf
    ├── index.html
    └── js
        ├── app.js     (only for Ionic)
        └── index.js   (only for Cordova)
```

## Troubleshooting

### PdfActivity missing in AndroidManifest.xml

In some cases, it might occur that Cordova fails adding the required `<activity/>` entry to your app's `AndroidManifest.xml`. If this is the case, you will see an error message like this when trying to show a PDF document:

```text
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

   ```xml
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

## Troubleshooting Ionic

### Failed Version Downgrade

Sometimes when running the app on the device again an error can occur which says:

```text
Error: adb: Command failed with exit code 1 Error output:
adb: failed to install /Users/christoph/Downloads/todo/platforms/android/app/build/outputs/apk/debug/app-debug.apk: Failure [INSTALL_FAILED_VERSION_DOWNGRADE]
```

To solve this just uninstall the existing app from your device. To be 100% sure you can got the Settings -> Apps and delete the app for all users.

### Build succeeds but it doesn't show the document on the device

Please make sure that your license key is properly set in the `AndroidManifest.xml`! You can also open a new terminal window and type `adb logcat` to see exactly what's happening on your device. When searching for "PSPDFKit" you should be able to search for the error rather easily.

### Can't find version for a specific support library

Example:
```
* What went wrong:
Could not resolve all files for configuration ':app:debugCompileClasspath'.
> Could not find support-media-compat.aar (com.android.support:support-media-compat:26.0.2).
  Searched in the following locations:
      https://jcenter.bintray.com/com/android/support/support-media-compat/26.0.2/support-media-compat-26.0.2.aar
```

Simply open your `platforms/android/pspdfkit-cordova-android/YOURAPP-pspdfkit.gradle` file and change the version. In this case changing `26.0.2` to `26.0.1` can already fix such issues because sometimes specific support library versions are not available anymore.

### PSPDFKit name not found in TypeScript app

If you are using newer versions of Ionic (for example Ionic 3 with Angular) together with the TypeScript language, you will probably see build errors similar to this:

```text
ERROR in src/app/app.component.ts(27,7): error TS2304: Cannot find name 'PSPDFKit'.
src/app/app.component.ts(30,26): error TS2304: Cannot find name 'PSPDFKit'.
src/app/app.component.ts(31,21): error TS2304: Cannot find name 'PSPDFKit'.
```

Since TypeScript is a type-safe language, and the `Cordova-Android` plugin is written for JavaScript, you need to manually define the `PSPDFKit` type inside your application. The easiest way to do this is to add following line to your `app.components.ts` file, or any other suitable TypeScript file in your app:

```typescript
// import statements should go first

declare var PSPDFKit: any;
```

## Contributing

Please ensure [you signed our CLA](https://pspdfkit.com/guides/web/current/miscellaneous/contributing/) so we can accept your contributions.
