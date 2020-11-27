package com.example.apputils.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: GsonUtil
 * @author: wangwc
 * @date: 2020/11/27 17:11
 */
public class GsonUtil {
    public static Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping()
            .registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).create();

    public GsonUtil() {
    }

    public static Map convertJsonToMap(String jsonString) {
        return (Map)gson.fromJson(jsonString, Map.class);
    }

    public static String convertMapToJson(Map<String, Object> map) {
        return gson.toJson(map);
    }

    public static <T> T convertJsonToObject(String jsonString, Class<T> modelCalss) {
        return gson.fromJson(jsonString, modelCalss);
    }

    public static Map<String, String> getMap4Json(String jsonString) {
        return (Map)gson.fromJson(jsonString, (new TypeToken<Map<String, String>>() {
        }).getType());
    }

    public static Map<String, Object> getMap4JsonObject(String jsonString) {
        return (Map)gson.fromJson(jsonString, (new TypeToken<Map<String, Object>>() {
        }).getType());
    }

    public static String getJsonValue(String jsonStr, String key) {
        JsonElement jsonElement = (new JsonParser()).parse(jsonStr).getAsJsonObject().get(key);
        String data = "";
        if (jsonElement != null) {
            data = jsonElement.toString().substring(1, jsonElement.toString().length() - 1);
        }

        return data;
    }

    public static String convertObjectToJson(Object javaObj) {
        return gson.toJson(javaObj);
    }

    public static <T> List<T> convertJsonToList(String jsonString, Class<T> pojoClass) {
        List<T> list = new ArrayList();
        JsonArray jsonArray = (JsonArray)gson.fromJson(jsonString, JsonArray.class);

        for(int i = 0; i < jsonArray.size(); ++i) {
            list.add(gson.fromJson(jsonArray.get(i), pojoClass));
        }

        return list;
    }

    public static <T> T convertJsonToObject(String jsonString, Type typeofObject) {
        return gson.fromJson(jsonString, typeofObject);
    }
}
