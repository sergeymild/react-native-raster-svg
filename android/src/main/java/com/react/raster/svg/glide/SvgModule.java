package com.react.raster.svg.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.LibraryGlideModule;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

/** Module for the SVG sample app. */
@GlideModule
public class SvgModule extends LibraryGlideModule {
    @Override
    public void registerComponents(
            @NonNull Context context,
            @NonNull Glide glide,
            @NonNull Registry registry
    ) {
        registry.register(SVG.class, Drawable.class, new SvgDrawableTranscoder(context))
            .register(SVG.class, Bitmap.class, new SvgBitmapTranscoder())
            .prepend(String.class, InputStream.class, new SvgStringModelLoaderFactory())
            .append(InputStream.class, SVG.class, new SvgInputStreamDecoder());
    }
}
