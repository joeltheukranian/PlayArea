package com.mkyong.model;

import java.util.List;

public class AjaxResponseBody {

    String msg;
    List<?> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }

}
