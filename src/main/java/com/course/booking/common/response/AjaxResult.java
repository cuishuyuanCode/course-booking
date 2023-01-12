package com.course.booking.common.response;

import com.course.booking.common.entity.HttpStatus;

public class AjaxResult {

    private int code;

    private String msg;

    private Object data;

    public AjaxResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public AjaxResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static AjaxResult success(){
        return AjaxResult.success("操作成功");
    }

    public static AjaxResult success(String msg){
        return AjaxResult.success(msg, null);
    }

    public static AjaxResult success(Object data){
        return AjaxResult.success("操作成功", data);
    }

    public static AjaxResult success(String msg,Object data){
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    public static AjaxResult error(String msg){
        return new AjaxResult(HttpStatus.ERROR,msg);
    }


}
