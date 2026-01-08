package com.toy.E_commerce.security.config;

import com.toy.E_commerce.auth.service.query.AccessTokenBlackListQueryService;
import com.toy.E_commerce.auth.util.JwtUtil;
import com.toy.E_commerce.global.annotation.enums.SecurityRole;
import com.toy.E_commerce.global.annotation.expression.ApiRoleUrlCollector;
import com.toy.E_commerce.security.filter.GlobalExceptionFilter;
import com.toy.E_commerce.security.filter.JwtAuthenticationFilter;
import com.toy.E_commerce.security.handler.CustomAccessDeniedHandler;
import com.toy.E_commerce.security.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userService;
    private final AccessTokenBlackListQueryService blackListService;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final ApiRoleUrlCollector apiRoleUrlCollector;

    @Bean
    public GlobalExceptionFilter globalExceptionFilter() {
        return new GlobalExceptionFilter();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(
                jwtUtil, userService, blackListService, apiRoleUrlCollector
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //기본적인 설정
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                //세션 사용 안 함
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //예외 처리 등록 filter내에서 못잡는 예외 던지면 인마들이 잡음
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/error",
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll();
                    auth.requestMatchers(
                            apiRoleUrlCollector.getUrls(SecurityRole.PUBLIC).toArray(String[]::new)
                    ).permitAll();
                    auth.requestMatchers(
                            apiRoleUrlCollector.getUrls(SecurityRole.AUTHENTICATED).toArray(String[]::new)
                    ).authenticated();
                    auth.requestMatchers(
                            apiRoleUrlCollector.getUrls(SecurityRole.USER).toArray(String[]::new)
                    ).hasRole("USER");
                    auth.requestMatchers(
                            apiRoleUrlCollector.getUrls(SecurityRole.SELLER).toArray(String[]::new)
                    ).hasRole("SELLER");
                    auth.requestMatchers(
                            apiRoleUrlCollector.getUrls(SecurityRole.ADMIN).toArray(String[]::new)
                    ).hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(globalExceptionFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }

    // 비밀번호 암호화용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

