package com.cswap.auth.constents;

import java.time.Duration;

/**
 * @author ZCY-
 */

public final class TokenExpirationTime {
    public static final Duration ACCESS_TOKEN_EXPIRE_TIME = Duration.ofHours(12);
    public static final Duration REFRESH_TOKEN_EXPIRE_TIME = Duration.ofDays(30);

}
