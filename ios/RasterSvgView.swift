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
    let width: CGFloat
    let height: CGFloat
    
    init(width: CGFloat, height: CGFloat) {
        self.width = width
        self.height = height
    }
    
    public var identifier: String = "com.appidentifier.webpprocessor"
    public func process(
        item: ImageProcessItem,
        options: KingfisherParsedOptionsInfo
    ) -> KFCrossPlatformImage? {
        switch item {
        case .image(let image):
            return image
        case .data(let data):
            let svg = SVGKImage(data: data)
            svg?.size = .init(width: width, height: height)
            let image = svg?.uiImage!
            return image
        }
    }
}

class SVGDataProvider: ImageDataProvider {
    var cacheKey: String
    private let file: String
    
    init(file: String, cacheKey: String?) {
        self.file = file
        self.cacheKey = cacheKey ?? "key2"
    }
    
    func data(handler: @escaping (Result<Data, Error>) -> Void) {
        DispatchQueue.global(qos: .background).async {
            guard let data = self.file.data(using: .utf8) else { return }
            handler(.success(data))
        }
    }
    
    
}

@objc
final class RasterSvgView: RCTView {
    private let image = UIImageView()
    
    @objc
    var rasterParams: [String: Any] = [:] {
        didSet {
            let type = rasterParams["type"] as! String
            let source = rasterParams["source"] as! String
            let width = CGFloat(rasterParams["width"] as! Int)
            let height = CGFloat(rasterParams["width"] as! Int)
            
            let scale = UIScreen.main.scale
            let resizingProcessor = ResizingImageProcessor(
                referenceSize: CGSize(width: width * scale, height: height * scale))
            KingfisherManager.shared.cache.clearCache()
            var options: KingfisherOptionsInfo = []
            options.append(.processor(SVGImgProcessor(width: width * scale, height: height * scale)))
            if type == "local" || type == "remote" {
                image.kf.setImage(
                    with: URL(string: source),
                    options: options
                )
            } else if type == "file" {
                let cacheKey = rasterParams["cacheKey"] as? String
                image.kf.setImage(
                    with: SVGDataProvider(file: source, cacheKey: cacheKey),
                    options: options
                )
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
