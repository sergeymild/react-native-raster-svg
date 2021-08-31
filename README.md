# react-native-raster-svg

## Getting started

`
pod 'Kingfisher', '~>6.3.1'
pod 'SVGKit', :modular_headers => true
pod 'CocoaLumberjack'
`

### Mostly automatic installation

`$ react-native link react-native-raster-svg`

## Usage

```javascript
import { RasterSvgView } from 'react-native-raster-svg';

<RasterSvgView
  style={{ width: 200, height: 100 }}
  params={{ source: require('./assets/dd.svg') }}
/>
```
