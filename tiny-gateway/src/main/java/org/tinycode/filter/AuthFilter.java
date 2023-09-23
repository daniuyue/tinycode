package org.tinycode.filter;

import com.alibaba.fastjson.JSONObject;
import org.tinycode.conf.GatewayConfiguration;
import org.tinycode.data.ApiResponse;
import org.tinycode.enmu.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
public class AuthFilter extends BaseFilter implements Ordered {

    @Autowired
    private GatewayConfiguration gatewayConfiguration;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();

        String authorization = request.getHeaders().getFirst(AUTHORIZATION);

        if (specialTokenCheck(authorization)) {
            return chain.filter(exchange);
        }

        return checkToken(exchange, chain, authorization, requestUri);
    }

    private Boolean specialTokenCheck(String authorization) {
        List<String> specialTokenList = gatewayConfiguration.getSpecialTokenList();
        if (specialTokenList.contains(authorization)) {
            return true;
        }
        return false;
    }


    /**
     * 校验token合法性
     *
     * @param exchange
     * @param chain
     * @param authorization
     * @return
     */
    private Mono<Void> checkToken(ServerWebExchange exchange, GatewayFilterChain chain, String authorization, String requestUri) {


        ServerHttpRequest request = exchange.getRequest();

        ServerHttpRequest.Builder mutate = request.mutate();

        ServerHttpRequest build = mutate.build();


        //服务端token或用户token
        if (checkUrlFormat(authorization)) {

            ApiResponse<String> authUserInfo = new ApiResponse<>();
            authUserInfo.setData("user01");
            authUserInfo.setSuccess(true);

            if (authUserInfo.getSuccess()) {
                Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                    try {
                        httpHeader.set("2B_HEADER_INFO",
                                Base64.getEncoder().encodeToString(JSONObject.toJSONString(authUserInfo.getData()).getBytes("utf-8")));
                    } catch (UnsupportedEncodingException e) {
                        log.error("gateway Set Header ERROR authorization->{} url->{}", authorization, requestUri);
                    }
                };
                mutate.headers(httpHeaders);
                return chain.filter(exchange.mutate().request(build).build());
            } else {
                return getVoidMono(exchange,
                        ApiResponse.failed(authUserInfo.getCode(), authUserInfo.getMessage()), HttpStatus.UNAUTHORIZED);
            }
        } else {
            return getVoidMono(exchange,
                    ApiResponse.failed(ErrorCode.TOKEN_FORMAT_ILLEGAL.getValue(), ErrorCode.TOKEN_FORMAT_ILLEGAL.getName()), HttpStatus.UNAUTHORIZED);
        }


    }


    private boolean checkUrlFormat(String authorization) {
        if (authorization.startsWith("5480446439437f998d9a21a353f1738044a1c5aa7da87b0c1bf8c77a154cc56f")) {
            return true;
        }
        return false;

    }


    @Override
    public int getOrder() {
        return 1;
    }
}
