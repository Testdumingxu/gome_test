package com.gome.oa.common;

import lombok.Data;

@Data
public class Result {
    private String status; // 1: 成功 0: 失败
    private Object data;
    private String message;

    public Result(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(String status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
