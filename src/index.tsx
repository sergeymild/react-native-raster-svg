// main index.js

import {
  Image,
  ImageRequireSource,
  requireNativeComponent,
  ViewProps,
} from 'react-native';
import React from 'react';

const ReactNativeRasterSvg = requireNativeComponent('ReactNativeRasterSvg');

interface Props extends ViewProps {
  params: { source: string | ImageRequireSource };
}

export const RasterSvgView: React.FC<Props> = (props) => {
  if (typeof props.params.source === 'number') {
    props.params.source = Image.resolveAssetSource(props.params.source).uri;
    //@ts-ignore
    props.params.type = 'local';
  }
  return <ReactNativeRasterSvg {...props} />;
};
