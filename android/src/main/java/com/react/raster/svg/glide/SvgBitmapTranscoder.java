package com.react.raster.svg.glide;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.caverock.androidsvg.SVG;

public class SvgBitmapTranscoder implements ResourceTranscoder<SVG, Bitmap> {
    @Nullable
    @Override
    public Resource<Bitmap> transcode(@NonNull Resource<SVG> toTranscode, @NonNull Options options) {
        SVG svg = toTranscode.get();
        float width = 0;
        float height = 0;
        if (svg.getDocumentWidth() > 0) {
            width = svg.getDocumentWidth();
        } else {
            width = svg.getDocumentViewBox().right - svg.getDocumentViewBox().left;
        }

        if (svg.getDocumentHeight() > 0) {
            height = svg.getDocumentHeight();
        } else {
            height = svg.getDocumentViewBox().bottom - svg.getDocumentViewBox().top;
        }

        Picture picture = svg.renderToPicture((int) width, (int) height);
        PictureDrawable drawable = new PictureDrawable(picture);

        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawPicture(drawable.getPicture());
        return new SimpleResource(bitmap);
    }
}
