package com.mermakov.testgithubclient.services.rest;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ItemTypeAdapterFactory implements TypeAdapterFactory {

    public static final String DATA_WRAPPER_KEY = "data";


    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {

            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {

                JsonElement jsonElement = elementAdapter.read(in);
                if (jsonElement.isJsonArray()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.add(DATA_WRAPPER_KEY,jsonElement);
                    jsonElement = jsonObject;
                }

                return delegate.fromJsonTree(jsonElement);
            }
        }.nullSafe();

    }
}
