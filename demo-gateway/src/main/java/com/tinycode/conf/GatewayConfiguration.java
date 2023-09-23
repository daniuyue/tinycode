package com.tinycode.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "write.url")
public class GatewayConfiguration {

    private List<String> ignoreUrlList;

    private List<String> authorizationIgnoreUrlList;

    private List<String> x_application_idIgnoreUrlList;

    private List<String> specialTokenList;

}
