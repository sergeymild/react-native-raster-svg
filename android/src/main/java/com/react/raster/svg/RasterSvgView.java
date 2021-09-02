package com.react.raster.svg;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import com.facebook.react.views.view.ReactViewGroup;
import com.react.raster.svg.glide.GlideSource;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class RasterSvgView extends ReactViewGroup {
    private final ImageView imageView;

    public RasterSvgView(Context context) {
        super(context);
        imageView = new ImageView(context);

        addView(imageView);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        imageView.layout(0, 0, right - left, bottom - top);
    }

    void setParams(ReadableMap map) {
        String type = map.getString("type");
        String source = map.getString("source");
        assert type != null;
        if (type.equals("local") && !BuildConfig.DEBUG) {
            throw new RuntimeException("local assets is not supported");
        }
        String cacheKey = map.getString("cacheKey");
        if (cacheKey != null) {
            Glide.with(this).load(source).signature(new S(cacheKey)).into(imageView);
            return;
        }
        Glide.with(this).load(source).into(imageView);
    }


    static class S implements Key {
        private final String cacheKey;

        public S(String cacheKey) {
            this.cacheKey = cacheKey;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(cacheKey.getBytes(StandardCharsets.UTF_8));
        }
    }
}
