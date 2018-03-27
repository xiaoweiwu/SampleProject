package com.fullshare.basebusiness.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class GsonHelper {
    private static Gson gson;
    private static ReentrantLock lock = new ReentrantLock();

    /**
     * Gson 可能有线程安全问题，在多线程中，同时实用一个Gson会有问题，需要加锁保护
     * {@link #lockGson()} 和 {@link #unLockGson()}
     */
    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(new TypeToken<TreeMap<String, Object>>() {
                            }.getType(),
                            new JsonDeserializer<TreeMap<String, Object>>() {
                                @Override
                                public TreeMap<String, Object> deserialize(JsonElement json, Type typeOfT,
                                                                           JsonDeserializationContext context) throws JsonParseException {
                                    TreeMap<String, Object> treeMap = new TreeMap<>();
                                    JsonObject jsonObject = json.getAsJsonObject();
                                    Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                    for (Map.Entry<String, JsonElement> entry : entrySet) {
                                        treeMap.put(entry.getKey(), entry.getValue());
                                    }
                                    return treeMap;
                                }
                            }).create();
        }
        return gson;
    }

    public static void lockGson() {
        lock.lock();
    }

    public static void unLockGson() {
        lock.unlock();
    }
}
