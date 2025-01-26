package com.cswap.common.constant;



public class GlobalConstants {
    public static final String GATEWAY_SERVER = "http://127.0.0.1:8080";

    public static final long ROLE_SYSTEM_ADMIN_ID = 1L;
    public static final long ROLE_USER_ID = 5L;
    public static final String REDIS_PERM_ENDPOINT_KEY= "permission:endpoints";

    public static final String REDIS_ORDER_TOKEN_PREFIX = "order:token:";

    public static final String REDIS_MESSAGE_STORE_PREFIX = "rabbitmq:message:";
    public static final String REDIS_MESSAGE_INDEX_PREFIX = "rabbitmq:status:";

    public static final String REDIS_MESSAGE_CONSUMER_RETRY = "rabbitmq:consumer:retry:";

    public static final int MQ_SEND_MAX_RETRY_COUNT = 3;
    public static final int MQ_SEND_RETRY_INTERVAL_MS = 1000;


    public static final int MQ_CONSUME_MAX_RETRY_COUNT = 3;
    public static final int MQ_CONSUME_RETRY_INTERVAL_MS = 1000;




}
