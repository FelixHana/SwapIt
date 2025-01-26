package com.cswap.common.constant;

import java.time.Duration;

public class SecurityConstants {
    public static final String AUTHORIZATION_SERVER = "http://127.0.0.1:9011";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";
    public static final Duration ACCESS_TOKEN_EXPIRE_TIME = Duration.ofHours(24);
    public static final Duration REFRESH_TOKEN_EXPIRE_TIME = Duration.ofDays(30);



}
