package com.toy.E_commerce.security.util;

import com.toy.E_commerce.global.util.TimeUtil;
import com.toy.E_commerce.member.entity.Member;
import com.toy.E_commerce.security.exception.JwtUtilErrorCode;
import com.toy.E_commerce.security.exception.JwtUtilException;
import com.toy.E_commerce.security.properties.JwtProperties;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final JwtParser accessTokenParser;
    private final JwtParser refreshTokenParser;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.accessTokenParser = Jwts.parserBuilder()
                .requireIssuer(jwtProperties.getTOKEN_ISSUER())
                .setClock(() -> Date.from(TimeUtil.getNowSeoulInstant()))
                .setAllowedClockSkewSeconds(60)   // 시계 오차 허용 (필요시 조정)
                .setSigningKey(jwtProperties.getACCESS_TOKEN_SECRET_KEY())
                .build();
        this.refreshTokenParser = Jwts.parserBuilder()
                .requireIssuer(jwtProperties.getTOKEN_ISSUER())
                .setClock(() -> Date.from(TimeUtil.getNowSeoulInstant()))
                .setAllowedClockSkewSeconds(60)   // 시계 오차 허용 (필요시 조정)
                .setSigningKey(jwtProperties.getREFRESH_TOKEN_SECRET_KEY())
                .build();
    }

    // ====== 생성 ======
    public String createAccessToken(Member member) {
        return createToken(
                String.valueOf(member.getIdentityId()),
                jwtProperties.getACCESS_TOKEN_SECRET_KEY(),
                jwtProperties.getACCESS_TOKEN_TTL()
        );
    }

    public String createRefreshToken(Member member) {
        return createToken(
                String.valueOf(member.getIdentityId()),
                jwtProperties.getREFRESH_TOKEN_SECRET_KEY(),
                jwtProperties.getREFRESH_TOKEN_TTL()
        );
    }

    // ====== Claims ======
    public Claims getClaimsFromAccessToken(String token) {
        return parse(token, jwtProperties.getACCESS_TOKEN_SECRET_KEY());
    }

    public Claims getClaimsFromRefreshToken(String token) {
        return parse(token, jwtProperties.getREFRESH_TOKEN_SECRET_KEY());
    }

    // ====== subject ======
    public UUID getSubjectFromAccessToken(String token) {
        try {
            return UUID.fromString(getClaimsFromAccessToken(token).getSubject());
        } catch (IllegalArgumentException e) {
            log.warn("[JWT] 잘못된 형식의 토큰  {}", e.getMessage());
            JwtUtilErrorCode ec = JwtUtilErrorCode.TOKEN_ILLEGAL_ARGUMENT;
            throw new JwtUtilException(ec);
        }
    }

    public UUID getSubjectFromRefreshToken(String token) {
        try {
            return UUID.fromString(getClaimsFromRefreshToken(token).getSubject());
        } catch (IllegalArgumentException e) {
            log.warn("[JWT] 잘못된 형식의 토큰  {}", e.getMessage());
            JwtUtilErrorCode ec = JwtUtilErrorCode.TOKEN_ILLEGAL_ARGUMENT;
            throw new JwtUtilException(ec);
        }
    }

    // ====== jti ======
    public UUID getJtiFromAccessToken(String token) {
        try {
            return UUID.fromString(getClaimsFromAccessToken(token).getId());
        } catch (IllegalArgumentException e) {
            log.warn("[JWT] 잘못된 형식의 토큰  {}", e.getMessage());
            JwtUtilErrorCode ec = JwtUtilErrorCode.TOKEN_ILLEGAL_ARGUMENT;
            throw new JwtUtilException(ec);
        }
    }

    public UUID getJtiFromRefreshToken(String token) {
        try {
            return UUID.fromString(getClaimsFromRefreshToken(token).getId());
        } catch (IllegalArgumentException e) {
            log.warn("[JWT] 잘못된 형식의 토큰  {}", e.getMessage());
            JwtUtilErrorCode ec = JwtUtilErrorCode.TOKEN_ILLEGAL_ARGUMENT;
            throw new JwtUtilException(ec);
        }
    }

    // ====== remaining =====
    public String remainingRefreshToken(String refreshToken, Member member) {
        Date refreshTokenExpiration = getExpirationFromRefreshToken(refreshToken);
        LocalDateTime exp = TimeUtil.toLocalDateTime(refreshTokenExpiration);
        LocalDateTime now = TimeUtil.getNowSeoulLocalDateTime();

        Duration remaining = Duration.between(now, exp);
        if (remaining.isNegative() || remaining.isZero()) {
            // 이미 만료
            JwtUtilErrorCode ec = JwtUtilErrorCode.TOKEN_EXPIRED;
            throw new JwtUtilException(ec);
        }
        // 남은 시간이 1일(24시간) 이하이면 새 토큰 발급
        if (remaining.compareTo(Duration.ofDays(1)) <= 0) {
            return createRefreshToken(member);
        }
        return null;
    }

    // ====== expiration ======
    public Date getExpirationFromAccessToken(String token) {
        return getClaimsFromAccessToken(token).getExpiration();
    }

    public Date getExpirationFromRefreshToken(String token) {
        return getClaimsFromRefreshToken(token).getExpiration();
    }

    // ====== 검증(간단: boolean) ======
    public void validateAccessToken(String token) {
        validateInternal(token, jwtProperties.getACCESS_TOKEN_SECRET_KEY());
    }

    public void validateRefreshToken(String token) {
        validateInternal(token, jwtProperties.getREFRESH_TOKEN_SECRET_KEY());
    }

    public String resolveTokenFromHttpServletRequest(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7).trim();
        }
        return null;
    }


    private String createToken(String subject, Key key, Duration ttl) {
        Instant now = TimeUtil.getNowSeoulInstant();
        Instant exp = now.plus(ttl);

        return Jwts.builder()
                .setSubject(subject)                 // PK만 담음
                .setId(UUID.randomUUID().toString())               // jti
                .setIssuer(jwtProperties.getTOKEN_ISSUER())                  // iss
                .setIssuedAt(Date.from(now))        // iat
                .setExpiration(Date.from(exp))      // exp
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private JwtParser getParser(Key key) {
        if (key.equals(jwtProperties.getACCESS_TOKEN_SECRET_KEY())) {
            return accessTokenParser;
        } else if (key.equals(jwtProperties.getREFRESH_TOKEN_SECRET_KEY())) {
            return refreshTokenParser;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Claims parse(String rawToken, Key key) {
        String token = stripBearerPrefix(rawToken);
        JwtParser parser = getParser(key);
        return parser.parseClaimsJws(token).getBody();
    }

    public String stripBearerPrefix(String token) {
        if (token == null) return null;
        String t = token.trim();
        if (t.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return t.substring(7).trim();
        }
        return t;
    }

    private void validateInternal(String rawToken, Key key) {
        try {
            JwtParser parser = getParser(key);
            parser.parseClaimsJws(stripBearerPrefix(rawToken));
        } catch (ExpiredJwtException e) {
            throw new JwtUtilException(JwtUtilErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new JwtUtilException(JwtUtilErrorCode.TOKEN_UNSUPPORTED);
        } catch (SignatureException e) {
            // 시그니처 불일치 -> 맨 아래 EX로 가서 로그 안남길려고 둠
            throw new JwtUtilException(JwtUtilErrorCode.TOKEN_BAD_SIGNATURE);
        } catch (MalformedJwtException | io.jsonwebtoken.io.DecodingException e) {
            throw new JwtUtilException(JwtUtilErrorCode.TOKEN_MALFORMED);
        } catch (SecurityException e) {
            throw new JwtUtilException(JwtUtilErrorCode.TOKEN_BAD_SIGNATURE);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new JwtUtilException(JwtUtilErrorCode.TOKEN_ILLEGAL_ARGUMENT);
        } catch (JwtException e) {
            log.error("[JWT_EXCEPTION] Unknown JWT exception -> {}", e.getMessage(), e);
            throw new JwtUtilException(JwtUtilErrorCode.TOKEN_OTHER);
        } catch (Exception e) {
            log.error("[JWT_EXCEPTION] Unknown exception -> {}", e.getMessage(), e);
            throw new JwtUtilException(JwtUtilErrorCode.TOKEN_OTHER);
        }

    }
}
