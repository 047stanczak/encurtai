package com.encurtai.api;

public record ApiResponse<T>(
    boolean success,
    int status,
    String message,
    T data
) {
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, 200, message, data);
    }

    public static <T> ApiResponse<T> ok(String message) {
        return new ApiResponse<>(true, 200, message, null);
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(false, status, message, null);
    }
    public static <T> ApiResponse<T> urlGenerated(String message, T hash) {
        return new ApiResponse<T>(true, 201, message, hash);
    }
}
