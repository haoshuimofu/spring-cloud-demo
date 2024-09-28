package com.demo.spring.cloud;

import java.io.Serializable;

/**
 * @Author wude
 * @Create 2019-07-25 14:00
 */
public class ResponseEntity<T> implements Serializable {

    private static final String SUCCESS_CODE = "0";

    private String code;
    private String message;
    private T data;

    public ResponseEntity() {
    }

    public ResponseEntity(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<T> success() {
        return new ResponseEntity<>(SUCCESS_CODE, null, null);
    }

    public static <T> ResponseEntity<T> success(T data) {
        return new ResponseEntity<>(SUCCESS_CODE, null, data);
    }

    public static <T> ResponseEntity<T> fail(String code, String message) {
        return new ResponseEntity<>(code, message, null);
    }

    public static <T> ResponseEntity<T> fail(String code, String message, T data) {
        return new ResponseEntity<>(code, message, data);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}