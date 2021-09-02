package com.react.raster.svg.glide;

import com.bumptech.glide.load.model.GlideUrl;

public class GlideSource extends GlideUrl {
    private final String cacheKey;
    public GlideSource(String url, String cacheKey) {
        super(url);
        this.cacheKey = cacheKey;
    }

    @Override
    public String getCacheKey() {
        return cacheKey;
    }
}
