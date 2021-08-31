import Foundation
import React

@objc(ReactNativeRasterSvg)
class RasterSvgManager: RCTViewManager {
    
    override class func requiresMainQueueSetup() -> Bool {
        true
    }

    override func view() -> UIView! {
        return RasterSvgView()
    }
}
