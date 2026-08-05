package com.studentrent.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Ghi JSON response cho cac endpoint AJAX.
 *
 * <p>LocalDate/LocalDateTime can adapter rieng: Gson mac dinh dung reflection
 * doc truong noi bo cua hai lop nay, nhung module java.time tren JDK 17 chan
 * truy cap do ({@code InaccessibleObjectException}/{@code JsonIOException}) -
 * moi model co cot DATE/DATETIME (Tenant, User...) se nem loi khi serialize
 * neu thieu adapter tuong minh nhu duoi day.</p>
 */
public final class JsonUtil {

    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, type, ctx) ->
                            src == null ? null : new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, type, ctx) ->
                            src == null ? null : new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .create();

    private JsonUtil() {
    }

    public static String toJson(Object data) {
        return GSON.toJson(data);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return GSON.fromJson(json, type);
    }

    /** Ghi truc tiep object ra response duoi dang application/json. */
    public static void write(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(toJson(data));
    }

    /** Ghi loi kem HTTP status. */
    public static void writeError(HttpServletResponse resp, int status, String message)
            throws IOException {
        resp.setStatus(status);
        write(resp, java.util.Map.of("success", false, "message", message));
    }
}
