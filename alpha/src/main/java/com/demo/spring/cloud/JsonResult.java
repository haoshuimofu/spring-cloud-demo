package com.demo.spring.cloud;

import java.io.Serializable;

/**
 * @ResponseBody Controller返回结构体包装类
 * @Author wude
 * @Create 2019-07-25 14:00
 */
public class JsonResult<T> implements Serializable {

    private static final Integer SUCCESS_CODE = 0;

    private boolean success;
    private Integer code;
    private String msg;
    private T data;

    public JsonResult() {
    }

    public JsonResult(boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> JsonResult<T> success() {
        return new JsonResult<>(true, SUCCESS_CODE, null, null);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(true, SUCCESS_CODE, null, data);
    }

    public static <T> JsonResult<T> fail(Integer code, String msg) {
        return new JsonResult<>(false, code, msg, null);
    }

    public static <T> JsonResult<T> fail(Integer code, String msg, T data) {
        return new JsonResult<>(false, code, msg, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}