package org.dollarhide.androidmovieviewer.model;

public class RestResult {
    private Boolean successFlag;
    private Object data;

    public RestResult(Boolean successFlag, Object data) {
        this.successFlag = successFlag;
        this.data = data;
    }

    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Boolean successFlag) {
        this.successFlag = successFlag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
