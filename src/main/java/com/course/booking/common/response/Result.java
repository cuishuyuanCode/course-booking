package com.course.booking.common.response;

import com.course.booking.common.entity.HttpStatus;

import java.io.Serializable;


public class Result<T> implements Serializable {

    private int code;

    private String msg;

    private T data;


    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public Result(String msg) {
        this.msg = msg;
    }

    public Result(T data) {
        this.data = data;
    }


    //------------------------------------------------Success Response---------------------------------------------
    public static <T> Result<T> success() {
        return new Result<T>(HttpStatus.SUCCESS, "操作成功");
    }

    public static <T> Result<T> success(String msg) {
        return new Result<T>(HttpStatus.SUCCESS, msg);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<T>(HttpStatus.SUCCESS, msg, data);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(HttpStatus.SUCCESS, "操作成功", data);
    }

    //------------------------------------------------failure Response---------------------------------------------
    public static <T> Result<T> failure() {
        return new Result<T>(HttpStatus.ERROR, "操作失败");
    }

    public static <T> Result<T> failure(String msg) {
        return new Result<T>(HttpStatus.ERROR, msg);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
