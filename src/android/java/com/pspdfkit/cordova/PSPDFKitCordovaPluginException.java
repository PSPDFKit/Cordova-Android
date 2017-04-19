/*
 * PSPDFKitCordovaPluginException.java
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

public class PSPDFKitCordovaPluginException extends RuntimeException {

    public PSPDFKitCordovaPluginException() {
    }

    public PSPDFKitCordovaPluginException(String detailMessage) {
        super(detailMessage);
    }

    public PSPDFKitCordovaPluginException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public PSPDFKitCordovaPluginException(Throwable throwable) {
        super(throwable);
    }
}
