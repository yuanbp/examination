package pers.chieftain.examination.cas;

import java.io.Serializable;

/**
 * @author chieftain
 * @date 2020/4/11 17:04
 */
public class CasResponseBo<T> implements Serializable {

    private static final long serialVersionUID = -348251688368409533L;

    private boolean status;
    private int code;
    private T data;
    private String msg;

    public void success(String msg) {
        this.setStatus(true);
        this.setMsg(msg);
    }
    public void success( String msg, T data) {
        this.setStatus(true);
        this.setMsg(msg);
        this.setData(data);
    }

    public void failure(String msg) {
        this.setStatus(false);
        this.setMsg(msg);
    }
    public void failure(String msg, T data) {
        this.setStatus(false);
        this.setMsg(msg);
        this.setData(data);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
