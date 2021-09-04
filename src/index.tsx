// main index.js

import {
  Image,
  ImageRequireSource,
  requireNativeComponent,
  ViewProps,
} from 'react-native';
import React from 'react';

const ReactNativeRasterSvg = requireNativeComponent('ReactNativeRasterSvg');

type Type = 'unspecified' | 'local' | 'remote' | 'file';
interface Params {
  source: string | ImageRequireSource;
  width: number;
  height: number;
  cacheKey?: string;
}
interface Props extends ViewProps {
  params: Params;
}

export const RasterSvgView: React.FC<Props> = (props) => {
  let params: Params & { type: Type } = {
    ...props.params,
    type: 'unspecified',
  };
  if (typeof params.source !== 'string') {
    params.source = Image.resolveAssetSource(params.source).uri;
    params.type = 'local';
  } else if (params.source.startsWith('http')) {
    params.type = 'remote';
  } else {
    params.type = 'file';
  }
  //@ts-ignore
  return <ReactNativeRasterSvg {...props} rasterParams={params} />;
};
