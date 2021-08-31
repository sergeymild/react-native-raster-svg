# react-native-raster-svg.podspec

require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-raster-svg"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-raster-svg
                   DESC
  s.homepage     = "https://github.com/sergeymild/react-native-raster-svg"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "sergeymild" => "sergeymild@yandex.ru" }
  s.platforms    = { :ios => "12.0" }
  s.source       = { :git => "https://github.com/sergeymild/react-native-raster-svg.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,cc,cpp,m,mm,swift}"
  s.requires_arc = true

  s.dependency "React-Core"
  s.dependency "SVGKit", :modular_headers => true
  s.dependency "Kingfisher"
end

