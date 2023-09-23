package com.tinycode.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tinycode.conf.GatewayConfiguration;
import com.tinycode.data.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

/**
 * @Author：zzh
 * @Description: 通用filter
 * @Date: 2021/8/20 6:12 下午
 */
@Slf4j
public abstract class BaseFilter implements GlobalFilter {

    protected final static String AUTHORIZATION = "Authorization";

    protected final static String X_APPLICATION_ID = "X-APPLICATION-ID";

    @Autowired
    private GatewayConfiguration gatewayConfiguration;

    /**
     *  header authorization 是否忽略的url
     *
     * @param requestUri
     * @return
     */
    protected boolean authorizationIsIgnoreUrl(String requestUri) {
        PathMatcher matcher = new AntPathMatcher();
        for (String url : gatewayConfiguration.getAuthorizationIgnoreUrlList()) {
            if (matcher.match(url,requestUri)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  header x_application_id 是否忽略的url
     *
     * @param requestUri
     * @return
     */
    protected boolean x_application_idIsIgnoreUrl(String requestUri) {
        PathMatcher matcher = new AntPathMatcher();
        for (String url : gatewayConfiguration.getX_application_idIgnoreUrlList()) {
            if (matcher.match(url,requestUri)) {
                return true;
            }
        }
        return false;
    }



    @NotNull
    public static Mono<Void> getVoidMono(ServerWebExchange serverWebExchange, ApiResponse body, HttpStatus httpStatus) {
        log.warn(JSON.toJSONString(body));
        serverWebExchange.getResponse().setStatusCode(httpStatus);
        byte[] bytes = JSONObject.toJSONString(body).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }
}