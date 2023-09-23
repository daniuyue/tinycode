package org.tinycode.enmu;


public enum ErrorCode {


    /**
     * 系统相关错误码[0~1000]区间。例如： 500，服务出现异常，请稍后再试
     */
    CODE_SUCCESS(10000, "Success"),
    RESULT_FAILED(403, "Failed"),
    NO_CONTENT(204, "No Content"),
    REDIS_RATE_LIMITER(429, "Too many request , please try later."),
    TIME_LIMITER(429, "Could not request it this time"),
    UNKNOWN_EXCEPTION(-1, "Unknown Exception"),
    PARAM_ERROR(400, "Parameter Error"),
    SERVICE_INTERNAL_EXCEPTION(500, "The service encountered an exception. Please try again later"),


    /**
     * 业务相关异常，按不同业务模块划分
     * 有六位组成 前两位为微服务编号 中间两位为业务编号 最后两位为错误编号
     */

    CLINET_ID_EMPTY(100001, "clientId is empty"),
    TOKEN_FORMAT_ILLEGAL(100002, "token is error"),
    DISALLOWED_REQUEST_METHOD(100003,"request way is error"),
    X_APPLICATION_ID_IS_EMPTY(100004,"X_APPLICATION_ID_IS_EMPTY is error"),
    AUTHORIZATION_IS_EMPTY(100004,"AUTHORIZATION_IS_EMPTY is error")
    ;


    private int value;
    private String name;

    ErrorCode(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


    /**
     * @param code
     * @return
     */
    public static ErrorCode getEnumByCode(int code) {
        if (code == 0) {
            return null;
        }
        for (ErrorCode authErrorCode : ErrorCode.values()) {
            if (authErrorCode.getValue() == code) {
                return authErrorCode;
            }
        }
        return null;
    }

}
