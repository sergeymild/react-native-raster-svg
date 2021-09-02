package com.react.raster.svg.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.caverock.androidsvg.SVG;

/**
 * Convert the {@link SVG}'s internal representation to an Android-compatible one ({@link Picture}).
 */
public class SvgDrawableTranscoder implements ResourceTranscoder<SVG, Drawable> {
    private final Context context;
    public SvgDrawableTranscoder(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public Resource<Drawable> transcode(
            @NonNull Resource<SVG> toTranscode, @NonNull Options options) {
        Bitmap bitmap = new SvgBitmapTranscoder().transcode(toTranscode, options).get();
        return new SimpleResource(new BitmapDrawable(context.getResources(), bitmap));
    }
}