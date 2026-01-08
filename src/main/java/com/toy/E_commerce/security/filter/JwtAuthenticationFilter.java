package com.toy.E_commerce.security.filter;

import com.toy.E_commerce.auth.exception.AccessTokenBlackListErrorCode;
import com.toy.E_commerce.auth.exception.AccessTokenBlackListException;
import com.toy.E_commerce.auth.service.query.AccessTokenBlackListQueryService;
import com.toy.E_commerce.auth.util.JwtUtil;
import com.toy.E_commerce.global.annotation.enums.SecurityRole;
import com.toy.E_commerce.global.annotation.expression.ApiRoleUrlCollector;
import com.toy.E_commerce.global.util.PathMatcherUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userService;
    private final AccessTokenBlackListQueryService blackListService;
    private final ApiRoleUrlCollector apiRoleUrlCollector;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        return apiRoleUrlCollector.getUrls(SecurityRole.PUBLIC)
                .stream()
                .anyMatch(pattern ->
                        PathMatcherUtil.match(pattern, requestUri)
                );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtil.resolveTokenFromHttpServletRequest(request);

        // 토큰 검증
        jwtUtil.validateAccessToken(token);
        // Subject에서 UserId(UUID) 가져오기
        UUID userIdentificationId = jwtUtil.getSubjectFromAccessToken(token);
        UserDetails userDetails =
                userService.loadUserByUsername(userIdentificationId.toString());

        //로그아웃리스트 확인
        UUID jwtJti = jwtUtil.getJtiFromAccessToken(token);
        if (blackListService.isExistByToken(jwtJti)) {
            AccessTokenBlackListErrorCode e = AccessTokenBlackListErrorCode.TOKEN_IS_BLACK_LIST;
            throw new AccessTokenBlackListException(e);
        }

        // Spring Security 인증 객체 생성
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // SecurityContext에 인증 정보 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
