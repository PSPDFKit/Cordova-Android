/**
 PSPDFKit

 Copyright (c) 2015 PSPDFKit GmbH. All rights reserved.

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

            it('should open a document from assets', function(done) {
                window.PSPDFKit.showDocumentFromAssets("www/Guide.pdf", function() {
                    done();
                }, function(error) {
                    done(error);
                });
            })
        });

    });
};