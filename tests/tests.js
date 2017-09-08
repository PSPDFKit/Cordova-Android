/**
 PSPDFKit

 Copyright (c) 2015-2017 PSPDFKit GmbH. All rights reserved.

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

        describe('ScrollMode', function() {
          it('should exist', function () {
              expect(window.PSPDFKit.ScrollMode).toBeDefined();
          });
        });

        describe('PageFitMode', function() {
          it('should exist', function () {
              expect(window.PSPDFKit.PageFitMode).toBeDefined();
          });
        });

        describe('PageScrollDirection', function() {
          it('should exist', function () {
              expect(window.PSPDFKit.PageScrollDirection).toBeDefined();
          });
        });

        describe('SearchType', function() {
          it('should exist', function () {
              expect(window.PSPDFKit.SearchType).toBeDefined();
          });
        });

        describe('ThumbnailBarMode', function() {
          it('should exist', function () {
              expect(window.PSPDFKit.ThumbnailBarMode).toBeDefined();
          });
        })
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

    createActionButton('Vertical continuous scrolling', function() {
        var asset = 'www/Guide.pdf';
        var options = {
          scrollDirection: PSPDFKit.PageScrollDirection.VERTICAL,
          scrollMode: PSPDFKit.ScrollMode.CONTINUOUS,
          title: "Custom Title",
          page: 2,
          useImmersiveMode: true,
          thumbnailBarMode: PSPDFKit.ThumbnailBarMode.THUMBNAIL_BAR_MODE_SCROLLABLE
        };

        console.log('Opening document ' + asset);

        window.PSPDFKit.showDocumentFromAssets(asset, options, function() {
            console.log("Document was successfully loaded.");
        }, function(error) {
            console.log('Error while loading the document:' + error)
        });
    });

    createActionButton('Password protected document', function() {
        var asset = 'www/password.pdf';
        var options = {
          title: "Password protected document",
          password: "test123"
        };

        console.log('Opening encrypted document ' + asset);

        window.PSPDFKit.showDocumentFromAssets(asset, options, function() {
            console.log("Document was successfully loaded.");
        }, function(error) {
            console.log('Error while loading the document:' + error)
        });
    });
};
