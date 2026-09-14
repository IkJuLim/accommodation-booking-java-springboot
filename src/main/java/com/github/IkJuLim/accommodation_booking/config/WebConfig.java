package com.github.IkJuLim.accommodation_booking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.ApiVersionParser;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String[] supportedVersions = {"1"};

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
                // 지원 & 기본 버전 & 필수 여부
                .addSupportedVersions(supportedVersions)
                .setDefaultVersion("1")
                .setVersionRequired(false)

                // "/api/v{version}/..." => index 1
                .usePathSegment(1)

                // 버전 파서
                .setVersionParser(new SimpleVersionParser());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 공통으로 "/api/v{version}" 접두사 적용 (Swagger API 제외)
        configurer.addPathPrefix(
                "/api/v{version:(?:" + String.join("|", supportedVersions) + "}",
                HandlerTypePredicate.forAnnotation(RestController.class)
                        .and(HandlerTypePredicate.forBasePackage("org.springdoc").negate())
        );
    }

    /**
     * version String내 version 파서
     */
    private static class SimpleVersionParser implements ApiVersionParser<String> {

        @Override
        public String parseVersion(String version) {
            if (!StringUtils.hasText(version)) {
                return null;
            }
            if (version.startsWith("v") || version.startsWith("V")) {
                version = version.substring(1);
            }
            int dotIndex = version.indexOf('.');
            return dotIndex == -1 ? version : version.substring(0, dotIndex);
        }
    }
}