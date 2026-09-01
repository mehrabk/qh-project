package ir.bank.qh.common.controller;

import java.time.Instant;
import java.util.Map;

public record ApiError(int status, String error, String message, Instant timestamp, Map<String, String> fieldErrors) {
    public static ApiError of(int status, String error, String message) {
        return new ApiError(status, error, message, Instant.now(), null);
    }

    public static ApiError of(int status, String error, String message, Map<String, String> fieldErrors) {
        return new ApiError(status, error, message, Instant.now(), fieldErrors);
    }
}
