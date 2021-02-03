package com.yinglongyhy.fang.exception;

public class RestApiException extends RuntimeException {
    /**
     * 父错误码
     */
    private String superCode;
    /**
     * 子错误码
     */
    private String subCode;

    public RestApiException(String superCode, String subCode) {
        this.superCode = superCode;
        this.subCode = subCode;
    }

    public RestApiException(String superCode, String subCode, String message) {
        super(message);
        this.superCode = superCode;
        this.subCode = subCode;
    }

    public String getSuperCode() {
        return superCode;
    }

    public void setSuperCode(String superCode) {
        this.superCode = superCode;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }
}
