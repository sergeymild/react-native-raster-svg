//
//  RasterSvgView.swift
//  RasterSvg
//
//  Created by Sergei Golishnikov on 31/08/2021.
//  Copyright © 2021 Facebook. All rights reserved.
//

import Foundation
import React
import Kingfisher
import SVGKit

private struct SVGImgProcessor: ImageProcessor {
    
    public var identifier: String = "com.appidentifier.webpprocessor"
    public func process(
        item: ImageProcessItem,
        options: KingfisherParsedOptionsInfo
    ) -> KFCrossPlatformImage? {
        switch item {
        case .image(let image):
            return image
        case .data(let data):
//            let image = try! SVGParser.parse(text: String(data: data, encoding: .utf8)!).toNativeImage(size: .init(200, 100))
            let image = SVGKImage(data: data).uiImage!
            return image
        }
    }
}

@objc
final class RasterSvgView: RCTView {
    private let image = UIImageView()
    
    @objc
    var params: [String: Any] = [:] {
        didSet {
            let type = params["type"] as? String
            let source = params["source"] as? String
            if source == nil { return }
            if type == "local" {
                image.kf.setImage(with: URL(string: source!), options: [.processor(SVGImgProcessor())])
            }
        }
    }
    
    init() {
        super.init(frame: .zero)
        addSubview(image)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        image.frame = .init(x: 0, y: 0, width: frame.width, height: frame.height)
    }
}
