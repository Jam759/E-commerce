package com.toy.E_commerce.global.annotation.expression;

import com.toy.E_commerce.global.annotation.ApiRole;
import com.toy.E_commerce.global.annotation.enums.SecurityRole;
import com.toy.E_commerce.global.annotation.exception.ApiRoleConfigurationException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiRoleUrlCollector {

    //URL정보를 갖고 있는 핵심 컴포넌트
    private final RequestMappingHandlerMapping handlerMapping;

    //접근 권한 별 리스트 맵
    private final Map<SecurityRole, List<String>> roleUrlMap =
            new EnumMap<>(SecurityRole.class);

    @PostConstruct
    public void collect() {

        // role별 리스트 초기화
        for (SecurityRole role : SecurityRole.values()) {
            roleUrlMap.put(role, new ArrayList<>());
        }

        handlerMapping.getHandlerMethods().forEach((info, method) -> {

            ApiRole apiRole = method.getMethodAnnotation(ApiRole.class);
            if (apiRole == null) {
                apiRole = method.getBeanType().getAnnotation(ApiRole.class);
            }

            String pkg = method.getBeanType().getPackageName();

            // 직접 개발한 API만 수집
            // ApiRole은 비즈니스 API만
            if (!pkg.startsWith("com.toy.E_commerce")) {
                return;
            }

            //ApiRole 없으면 AUTHENTICATED로 처리
            SecurityRole[] roles =
                    (apiRole == null)
                            ? new SecurityRole[]{SecurityRole.AUTHENTICATED}
                            : apiRole.roles();

            //ApiRole 있을 때만 규칙 검증
            if (apiRole != null) {
                validateRoles(apiRole, method);
            }

            for (SecurityRole role : roles) {
                info.getPatternValues()
                        .forEach(pattern -> roleUrlMap.get(role).add(pattern));
            }
        });

        logCollectedUrls();
    }

    public List<String> getUrls(SecurityRole role) {
        return List.copyOf(roleUrlMap.get(role));
    }

    private void validateRoles(ApiRole apiRole, HandlerMethod method) {

        SecurityRole[] roles = apiRole.roles();

        if (roles.length <= 1) return;

        boolean hasPublic = Arrays.asList(roles).contains(SecurityRole.PUBLIC);

        if (hasPublic) {
            throw new ApiRoleConfigurationException(
                    buildErrorMessage(apiRole, method)
            );
        }
    }

    private void logCollectedUrls() {
        log.info("========== ApiRole URL Mapping ==========");

        roleUrlMap.forEach((role, urls) -> {
            if (urls.isEmpty()) {
                log.info("[{}] - (no urls)", role);
                return;
            }

            List<String> sortedUrls = urls.stream()
                    .distinct()
                    .sorted()
                    .toList();

            log.info("[{}]", role);
            sortedUrls.forEach(url ->
                    log.info("  └─ {}", url)
            );
        });

        log.info("========================================");
    }

    private String buildErrorMessage(ApiRole apiRole, HandlerMethod method) {

        return """
                Invalid @ApiRole configuration detected.
                PUBLIC role must be used alone.
                
                location : %s#%s
                roles    : %s
                """
                .formatted(
                        method.getBeanType().getSimpleName(),
                        method.getMethod().getName(),
                        Arrays.toString(apiRole.roles())
                );
    }

}
