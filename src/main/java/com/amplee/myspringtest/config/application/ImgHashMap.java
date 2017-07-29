package com.amplee.myspringtest.config.application;

import java.util.LinkedHashMap;
import java.util.Map;

public class ImgHashMap<K, V> extends LinkedHashMap<K, V> {

    private final int maxSize;

    public ImgHashMap(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}
