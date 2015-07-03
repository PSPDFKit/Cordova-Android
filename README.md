<!-- 
 # README.md
 #
 #   PSPDFKit
 #
 #   Copyright (c) 2015 PSPDFKit GmbH. All rights reserved.
 #
 #   THIS SOURCE CODE AND ANY ACCOMPANYING DOCUMENTATION ARE PROTECTED BY INTERNATIONAL COPYRIGHT LAW
 #   AND MAY NOT BE RESOLD OR REDISTRIBUTED. USAGE IS BOUND TO THE PSPDFKIT LICENSE AGREEMENT.
 #   UNAUTHORIZED REPRODUCTION OR DISTRIBUTION IS SUBJECT TO CIVIL AND CRIMINAL PENALTIES.
 #   This notice may not be removed from this file.
 # -->
# pspdfkit-cordova-android

This plugin defines a global `PSPDFKit` object, which provides an API for viewing PDF documents with PSPDFKit for Android.

## Installation

	cordova plugin add pspdfkit-cordova-android
	
## Example

	function showMyDocument() {
		PSPDFKit.showDocumentFromAssets('www/documents/myFile.pdf');
	}
	
## PSPDFKit.showDocument

Opens a document from the local device storage.

	PSPDFKit.showDocument(uri, success, fail)
	
## PSPDFKit.showDocumentFromAssets

Opens a document from the app's asset directory. To package a file within your app's assets, put it into the `www/` directory of your project.

	PSPDFKit.showDocumentFromAssets(assetPath, success, fail)

## Supported Platforms

* Android SDK API level 15+ / Android 4.0.3+ (Ice Cream Sandwich MR1)

## Quickstart Guide

Create a new Apache Cordova project from your command line using the [Apache Cordova Command-Line Interface (CLI)](https://cordova.apache.org/docs/en/5.1.1/index.html).

	$ cordova create pdfapp com.example.pdfapp PDF-App
	$ cd pdfapp

> Important: Your app's package name (in the above example `com.example.pdfapp`) has to match your PSPDFKit license name or PSPDFKit will throw an exception.

Add Android platform support to your project:

	$ cordova platform add android

Install the PSPDFKit plugin:

	$ cordova plugin add https://github.com/PSPDFKit/Cordova-Android.git
	
Set the minimum SDK version of your Android application to 15. To do so, add the `android-minSdkVersion` preference inside the android platform configuration of your `config.xml`. It should now look like this:

	<platform name="android">
        <preference name="android-minSdkVersion" value="15" />
        <!-- more Android platform settings -->
    </platform>

Copy the PSPDFKit library file (usually `pspdfkit-<version>.aar`) into your project. This example uses version `1.2.2` of the PSPDFKit library. The current working directory has to be your project directory:

	$ cp /path/to/pspdfkit-1.2.2.aar platforms/android/libs/
	
You are now ready to build your app!

	$ cordova build