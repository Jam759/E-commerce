package com.toy.E_commerce.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.AntPathMatcher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PathMatcherUtil {

    public static final AntPathMatcher MATCHER = new AntPathMatcher();

    public static boolean match(String pattern, String path) {
        return MATCHER.match(pattern, path);
    }
}
