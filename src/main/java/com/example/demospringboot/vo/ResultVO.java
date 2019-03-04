package com.example.demospringboot.vo;

/**
 * 请求处理结果对象，ajax处理等
 *
 * @author haoxz11
 * @created Jul 4, 2014 3:25:13 PM
 */
public class ResultVO<T> {
    private static final long serialVersionUID = -1222614520893986846L;

    private T result;

    /**
     * 请求处理结果
     */
    private Boolean success;

    /**
     * 请求回传code
     */
    private String code;

    /**
     * 请求处理结果描述
     */
    private String message;


    public ResultVO() {
        success = false;
    }

    public ResultVO(boolean isSuccess, String code, String message) {
        this.setSuccess(isSuccess);
        this.setCode(code);
        this.setMessage(message);
    }

    public ResultVO(boolean isSuccess, T value) {
        this.setSuccess(isSuccess);
        this.setResult(value);
    }

    public ResultVO(boolean isSuccess) {
        this.setSuccess(isSuccess);
    }

    public T getResult() {
        return result;
    }

    public ResultVO setResult(T result) {
        this.result = result;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ResultVO setCode(String code) {
        this.code = code;
        return this;
    }

    public boolean getSuccess() {
        return success;
    }

    public ResultVO setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultVO setMessage(String message) {
        this.message = message;
        return this;
    }
}
