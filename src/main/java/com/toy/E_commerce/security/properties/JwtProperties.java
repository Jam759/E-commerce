package com.toy.E_commerce.security.properties;


import com.toy.E_commerce.global.util.PropertyUtils;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.security.Key;
import java.time.Duration;
import java.util.Objects;

@Getter
@Configuration
public class JwtProperties {

    private final Environment env;

    private final Key ACCESS_TOKEN_SECRET_KEY;
    private final Key REFRESH_TOKEN_SECRET_KEY;
    private final Duration REFRESH_TOKEN_TTL;
    private final Duration ACCESS_TOKEN_TTL;
    private final String TOKEN_ISSUER;

    public JwtProperties(Environment env) {
        this.env = env;

        this.ACCESS_TOKEN_SECRET_KEY =
                PropertyUtils.getRequired(env,
                        "security.jwt.key.access-key",
                        this::parseKey);

        this.REFRESH_TOKEN_SECRET_KEY =
                PropertyUtils.getRequired(env,
                        "security.jwt.key.refresh-key",
                        this::parseKey);

        this.ACCESS_TOKEN_TTL =
                Duration.ofMinutes(
                        PropertyUtils.getLong(env, "security.jwt.ttl.access")
                );

        this.REFRESH_TOKEN_TTL =
                Duration.ofDays(
                        PropertyUtils.getLong(env, "security.jwt.ttl.refresh")
                );

        this.TOKEN_ISSUER =
                PropertyUtils.getRequired(env, "security.jwt.issuer");
    }

    private Key parseKey(String value) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(value));
    }

}

