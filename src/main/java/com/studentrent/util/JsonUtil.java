package com.studentrent.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Ghi JSON response cho cac endpoint AJAX. */
public final class JsonUtil {

    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class,
                    (com.google.gson.JsonSerializer<LocalDate>) (src, type, ctx) ->
                            src == null ? null
                                        : new com.google.gson.JsonPrimitive(
                                              src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
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
