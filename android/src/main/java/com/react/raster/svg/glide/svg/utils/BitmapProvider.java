package com.react.raster.svg.glide.svg.utils;

import android.graphics.Bitmap;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface BitmapProvider {

    @NonNull
    Bitmap get(
            @IntRange(from = 0) int width,
            @IntRange(from = 0) int height,
            @NonNull Bitmap.Config config
    );
}
