package com.react.raster.svg.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;

public class SvgInputStreamDecoder implements ResourceDecoder<InputStream, SVG> {
    @Override
    public boolean handles(@NonNull InputStream source, @NonNull Options options) throws IOException {
        return true;
    }

    @Nullable
    @Override
    public Resource<SVG> decode(@NonNull InputStream source, int width, int height, @NonNull Options options) throws IOException {
        try {
            SVG svg = SVG.getFromInputStream(source);
            if (svg.getDocumentViewBox() == null) {
                svg.setDocumentViewBox(0f, 0f, svg.getDocumentWidth(), svg.getDocumentHeight());
            }
            svg.setDocumentWidth(width);
            svg.setDocumentHeight(height);
            return new SimpleResource<>(svg);
        } catch (SVGParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
