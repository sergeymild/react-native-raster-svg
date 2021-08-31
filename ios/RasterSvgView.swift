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
            let image = SVGKImage(data: data).uiImage!
            return image
        }
    }
}

class SVGDataProvider: ImageDataProvider {
    private let cacheKey: String
    private let file: String
    
    init(file: String, cacheKey: String?) {
        self.file = file
        self.cacheKey = cacheKey ?? "key2"
    }
    
    func data(handler: @escaping (Result<Data, Error>) -> Void) {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            guard let data = self.file.data(using: .utf8) else { return }
            handler(.success(data))
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
            if type == "local" || type == "remote" {
                image.kf.setImage(with: URL(string: source!), options: [.processor(SVGImgProcessor())])
            } else if type == "file" {
                let cacheKey = params["cacheKey"] as? String
                image.kf.setImage(
                    with: SVGDataProvider(file: source!, cacheKey: cacheKey),
                    options: [.processor(SVGImgProcessor())]
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
