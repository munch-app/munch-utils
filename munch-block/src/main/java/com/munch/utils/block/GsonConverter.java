package com.munch.utils.block;

import com.google.gson.Gson;

/**
 * Created by: Fuxing
 * Date: 15/9/2016
 * Time: 1:07 AM
 * Project: essential
 */
public class GsonConverter implements JsonConverter {

    // Gson Version: 2.7
    protected final Gson gson;

    public GsonConverter(Gson gson) {
        this.gson = gson;
    }

    public GsonConverter() {
        this(new Gson());
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T fromJson(Class<T> clazz, String content) {
        return gson.fromJson(content, clazz);
    }

    public Gson getGson() {
        return gson;
    }
}
