package com.react.raster.svg.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SvgStringModelLoaderFactory implements ModelLoaderFactory<String, InputStream> {


    @NonNull
    @Override
    public ModelLoader<String, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
        return new ModelLoader<String, InputStream>() {
            @Nullable
            @Override
            public LoadData<InputStream> buildLoadData(@NonNull String s, int width, int height, @NonNull Options options) {
                return new LoadData<>(
                        (Key) messageDigest -> messageDigest.update(("svg_string_"+s).getBytes(StandardCharsets.UTF_8)),
                        new SvgDataFetcher(s));
            }

            @Override
            public boolean handles(@NonNull String s) {
                return s.startsWith("<svg");
            }
        };
    }

    @Override
    public void teardown() {

    }
}
