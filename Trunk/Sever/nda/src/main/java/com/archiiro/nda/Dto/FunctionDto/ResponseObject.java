package com.archiiro.nda.Dto.FunctionDto;

import java.util.List;

public class ResponseObject {
    private String status;
    private String message;
    private Object data;

    public ResponseObject() {

    }

    public ResponseObject(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private List<Object> listData;

    public ResponseObject(String status, String message, Object data, List<Object> listData) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.listData = listData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Object> getListData() {
        return listData;
    }

    public void setListData(List<Object> listData) {
        this.listData = listData;
    }
}
