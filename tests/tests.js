/**
 PSPDFKit

 Copyright (c) 2015-2016 PSPDFKit GmbH. All rights reserved.

 THIS SOURCE CODE AND ANY ACCOMPANYING DOCUMENTATION ARE PROTECTED BY INTERNATIONAL COPYRIGHT LAW
 AND MAY NOT BE RESOLD OR REDISTRIBUTED. USAGE IS BOUND TO THE PSPDFKIT LICENSE AGREEMENT.
 UNAUTHORIZED REPRODUCTION OR DISTRIBUTION IS SUBJECT TO CIVIL AND CRIMINAL PENALTIES.
 This notice may not be removed from this file.
 */
exports.defineAutoTests = function () {
    describe('PSPSDFKit (window.PSPDFKit)', function () {
        it("should exist", function () {
            expect(window.PSPDFKit).toBeDefined();
        });

        describe('showDocument', function() {
            it('should exist', function () {
                expect(window.PSPDFKit.showDocument).toBeDefined();
            });

        });

        describe('showDocumentFromAssets', function() {
            it('should exist', function () {
                expect(window.PSPDFKit.showDocumentFromAssets).toBeDefined();
            });
        });
    });
};

exports.defineManualTests = function(contentEl, createActionButton) {

    createActionButton('Open Document', function() {
        var asset = 'www/Guide.pdf';

        console.log('Opening document ' + asset);
        window.PSPDFKit.showDocumentFromAssets(asset, {}, function() {
            console.log("Document was successfully loaded.");
        }, function(error) {
            console.log('Error while loading the document:' + error)
        });
    });
};
