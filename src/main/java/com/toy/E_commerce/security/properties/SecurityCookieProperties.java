package com.toy.E_commerce.security.properties;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.Duration;
import java.util.Objects;


@Getter
@Configuration
public class SecurityCookieProperties {

    private final Environment env;

    private final String REFRESH_TOKEN_COOKIE_NAME;
    private final String REFRESH_TOKEN_COOKIE_PATH;
    private final boolean REFRESH_TOKEN_HTTP_ONLY;
    private final boolean REFRESH_TOKEN_SECURE;
    private final String REFRESH_TOKEN_SAME_SITE;
    private final Duration REFRESH_TOKEN_MAX_AGE;

    public SecurityCookieProperties(Environment env) {
        this.env = env;

        this.REFRESH_TOKEN_COOKIE_NAME =
                getRequired("security.cookie.refresh-token.name");

        this.REFRESH_TOKEN_COOKIE_PATH =
                getRequired("security.cookie.refresh-token.path");

        this.REFRESH_TOKEN_HTTP_ONLY =
                getBoolean("security.cookie.refresh-token.http-only");

        this.REFRESH_TOKEN_SECURE =
                getBoolean("security.cookie.refresh-token.secure");

        this.REFRESH_TOKEN_SAME_SITE =
                getRequired("security.cookie.refresh-token.same-site");

        long maxAgeSeconds =
                getLong("security.cookie.refresh-token.max-age-seconds");

        this.REFRESH_TOKEN_MAX_AGE =
                Duration.ofSeconds(maxAgeSeconds);
    }

}
