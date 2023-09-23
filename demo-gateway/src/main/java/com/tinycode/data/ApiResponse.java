package com.tinycode.data;


import com.tinycode.enmu.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> implements Serializable {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    private static <T> ApiResponse<T> createInstance(boolean success, int code, String message, T data) {
        return new ApiResponse<>(success, code, message, data);
    }

    public static <T> ApiResponse<T> succeeded() {
        return succeeded(null);
    }

    public static <T> ApiResponse<T> succeeded(T data) {
        return createInstance(true, ErrorCode.CODE_SUCCESS.getValue(), ErrorCode.CODE_SUCCESS.getName(), data);
    }

    public static <T> ApiResponse<T> failed(int code, String message) {
        return createInstance(false, code, message, null);
    }

    public static <T> ApiResponse<T> failed(ErrorCode errorCode) {
        return createInstance(false, errorCode.getValue(), errorCode.getName(), null);
    }

    public static <T> T checkAndGet(ApiResponse<T> response) {
        if (Objects.nonNull(response) && response.getSuccess() && Objects.nonNull(response.getData())) {
            return response.getData();
        }
        return null;
    }
}