package com.tinycode.data;


import lombok.Data;

@Data
public class GatewayLog {

    private String authorization;
    private String requestPath;
    private String requestMethod;
    private String schema;
    private String requestBody;
    private String responseBody;
    private String ip;
}
