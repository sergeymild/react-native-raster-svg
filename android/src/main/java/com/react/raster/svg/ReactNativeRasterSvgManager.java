package com.react.raster.svg;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ReactNativeRasterSvgManager extends SimpleViewManager<RasterSvgView> {

    public static final String REACT_CLASS = "ReactNativeRasterSvg";

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    public RasterSvgView createViewInstance(@NonNull ThemedReactContext c) {
        return new RasterSvgView(c);
    }

    @ReactProp(name = "rasterParams")
    public void setParams(RasterSvgView view, ReadableMap params) {
        view.setParams(params);
    }
}
