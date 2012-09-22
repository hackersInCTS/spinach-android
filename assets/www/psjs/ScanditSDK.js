var ScanditSDK = function() {

}

/**
 * Available options:
 * 
 * exampleStringForOption: defaultValue
 * Short explanation of option.
 *
 * 1DScanning: true
 * Enables or disables the recognition of 1D codes.
 *
 * 2DScanning: true
 * Enables or disables the recognition of 2D codes.
 *
 * ean13AndUpc12: true
 * Enables or disables the recognition of EAN13 and UPC12/UPCA codes. 
 *
 * ean8Enabled: true
 * Enables or disables the recognition of EAN8 codes. 
 *
 * upce: true
 * Enables or disables the recognition of UPCE codes. 
 *
 * code39: true
 * Enables or disables the recognition of CODE39 codes. 
 * Note: Not all Scandit SDK versions support Code 39 scanning.
 *
 * code128: true
 * Enables or disables the recognition of CODE128 codes. 
 * Note: Not all Scandit SDK versions support Code 128 scanning.
 *
 * itf: true
 * Enables or disables the recognition of ITF codes. 
 * Note: Not all Scandit SDK versions support ITF scanning.
 *
 * qr: false
 * Enables or disables the recognition of QR codes.
 *
 * dataMatrix: false
 * Enables or disables the recognition of Data Matrix codes.
 * Note: Not all Scandit SDK versions support Data Matrix scanning.
 *
 * inverseRecognition: false
 * Enables the detection of white on black codes. This option currently
 * only works on Data Matrix codes.
 *
 * microDataMatrix: false
 * Enables special settings to allow the recognition of very small Data
 * Matrix codes. If this is not specifically needed, do not enable it as it
 * uses considerable processing power. This setting automatically forces
 * 2d recognition on every frame. This option only works on devices with
 * Android 2.2 or higher, it does not cause issues with lower versions but
 * simply doesn't work.
 *
 * force2d: false
 * Forces the engine to always run a 2d recognition, ignoring whether a 2d
 * code was detected in the current frame.
 *
 * scanningHotSpot: "0.5/0.5" (x/y)
 * Changes the location of the spot where the recognition actively scans for 
 * barcodes. X and y can be between 0 and 1, where 0/0 is the top left corner 
 * and 1/1 the bottom right corner.
 *
 * scanningHotSpotHeight: 0.25
 * Changes the height of the spot where the recognition actively scans for
 * barcodes. The height of the hot spot is given relative to the height of
 * the screen and has to be between 0.0 and 0.5.
 * Be aware that if the hot spot height is very large, the engine is forced
 * to decrease the quality of the recognition to keep the speed at an 
 * acceptable level.
 *
 * ignorePreviewAspectRatio: false
 * Normally the picker adjusts to the aspect ratio of the preview image. If
 * this is called, it will no longer do this.
 * Warning: If the aspect ratio is not kept, the camera feed may be
 * stretched and no longer be a proper representation of what is recorded.
 *
 * searchBar: false
 * Adds (or removes) a search bar to the top of the scan screen.
 *
 * titleBar: true
 * Adds (or removes) the title bar at the top of the scan screen.
 * This parameter is deprecated but retained for use with the old GUI which is
 * superseeded by the new 3.0 GUI that does not have a title bar anymore.
 *
 * toolBar: true
 * Adds (or removes) the tool bar at the bottom of the scan screen.
 * This parameter is deprecated but retained for use with the old GUI which is
 * superseeded by the new 3.0 GUI that does not have a tool bar anymore.
 *
 * beep: true
 * Enables or disables the sound played when a code was recognized.
 *
 * vibrate: true
 * Enables or disables the vibration when a code was recognized.
 *
 * torch: true
 * Enables or disables the icon that let's the user activate the LED torch
 * mode. If the device does not support torch mode, the icon to activate is
 * will not be visible regardless of the value.
 * 
 * textForInitialScanScreenState: "Align code with box"
 * Sets the text that will be displayed above the viewfinder to tell the user 
 * to align it with the barcode that should be recognized.
 *
 * textForBarcodePresenceDetected: "Align code and hold still"
 * Sets the text that will be displayed above the viewfinder to tell the user 
 * to align it with the barcode and hold still because a potential code seems 
 * to be on the screen.
 *
 * textForBarcodeDecodingInProgress: "Decoding ..."
 * Sets the text that will be displayed above the viewfinder to tell the user 
 * to hold still because a barcode is aligned with the box and the recognition 
 * is trying to recognize it.
 *
 * titleMessage: "Scan a barcode"
 * Sets the title shown at the top of the screen.
 *
 * leftButtonCaption: "KEYPAD"
 * Sets the caption of the left button.
 * Deprecated: This string is only used in the old GUI.
 *
 * leftButtonCaptionWhenKeypadVisible: "OK"
 * Sets the caption of the left button when the keypad is visible.
 *
 * rightButtonCaption: "CANCEL"
 * Sets the caption of the right button.
 *
 * rightButtonCaptionWhenKeypadVisible: "CANCEL"
 * Sets the caption of the right button when the keypad is visible.
 *
 * searchBarHint: "Scan barcode or enter it here"
 * Sets the text shown in the manual entry field when nothing has been
 * entered yet.
 *
 * viewfinderColor: "FFFFFF"
 * Sets the color of the viewfinder when no code has been recognized yet.
 * 
 * viewfinderDecodedColor: "00FF00"
 * Sets the color of the viewfinder once the barcode has been recognized.
 * 
 */

ScanditSDK.prototype.scan = function(success, fail, appKey, options) {
    return PhoneGap.exec(success, fail, 'ScanditSDK', 'scan', 
                         [appKey, options]);
};

PhoneGap.addConstructor(function() {
    PhoneGap.addPlugin('ScanditSDK', new ScanditSDK());
});