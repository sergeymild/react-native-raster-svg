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

private class SVGImgProcessor: ImageProcessor {
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
            return svg?.uiImage
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
        DispatchQueue.global(qos: .background).async { [weak self] in
            guard let self = self else { return }
            guard let data = self.file.data(using: .utf8) else { return }
            handler(.success(data))
        }
    }
}

@objc
final class RasterSvgView: UIImageView {
    private var task: DownloadTask?
    
    @objc
    var rasterParams: [String: Any] = [:] {
        didSet {
            task?.cancel()
            task = nil
            let type = rasterParams["type"] as! String
            let source = rasterParams["source"] as! String
            let width = CGFloat(rasterParams["width"] as! Int)
            let height = CGFloat(rasterParams["width"] as! Int)


            var options: KingfisherOptionsInfo = []
            options.append(.processor(SVGImgProcessor(width: width, height: height)))
            if type == "local" || type == "remote" {
                task = kf.setImage(
                    with: URL(string: source),
                    options: options
                )
            } else if type == "file" {
                let cacheKey = rasterParams["cacheKey"] as? String
                task = kf.setImage(
                    with: SVGDataProvider(file: source, cacheKey: cacheKey),
                    options: options
                )
            }
        }
    }
    
    deinit {
        task?.cancel()
        task = nil
    }
}
