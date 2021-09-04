package com.react.raster.svg;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.views.view.ReactViewGroup;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class RasterSvgView extends ReactViewGroup implements RequestListener<Drawable> {
    @Nullable
    private Drawable resource;

    public RasterSvgView(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    void setParams(ReadableMap map) {
        String type = map.getString("type");
        String source = map.getString("source");
        int width = map.getInt("width");
        int height = map.getInt("height");
        assert type != null;
        if (type.equals("local") && !BuildConfig.DEBUG) {
            throw new RuntimeException("local assets is not supported");
        }
        RequestBuilder<Drawable> builder = Glide
                .with(this)
                .load(source)
                .override((int) PixelUtil.toPixelFromDIP(width), (int) PixelUtil.toPixelFromDIP(height))
                .addListener(this);

        String cacheKey = map.getString("cacheKey");
        if (cacheKey != null) builder = builder.signature(new S(cacheKey));
        builder.preload();
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        RasterSvgView.this.resource = resource;
        RasterSvgView.this.invalidate();
        return false;
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (resource != null) {
            resource.setBounds(0, 0, getWidth(), getHeight());
            resource.draw(canvas);
        }
    }
}
