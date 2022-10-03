package com.senko.framework.config.swagger;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.senko.framework.config.SenkoConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Swagger配置
 *
 * @author senko
 * @date 2022/9/4 12:27
 */
@Configuration
public class SwaggerConfig {

    @Autowired
    private SenkoConfig senkoConfig;

    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.OAS_30)
                // 启用
                .enable(senkoConfig.isSwaggerEnabled())
                // 配置Swagger网页简介
                .apiInfo(getApiInfo())
                // 配置选择器，选择哪些接口暴露给Swagger展示
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                // 安全配置：页面上出现一个Authorization选项，可以给每次请求附带Header
                .securityContexts(buildSecurityContexts())
                .securitySchemes(buildSecuritySchemes());

    }

    /**
     * 指定上下文
     */
    private List<SecurityContext> buildSecurityContexts() {
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultReference())
                .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                .build();
        return Collections.singletonList(securityContext);
    }


    private List<SecurityReference> defaultReference() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    /**
     * 生成页面里的选项
     */
    private List<SecurityScheme> buildSecuritySchemes() {
        // 所属模块、key的名字、放在哪里
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        return Collections.singletonList(apiKey);
    }

    /**
     * Swagger 网页简介
     */
    private ApiInfo getApiInfo() {

        SenkoConfig.Author author = senkoConfig.getAuthor();
        return new ApiInfoBuilder()
                // 标题
                .title("标题：" + senkoConfig.getProjectName())
                // 描述
                .description("描述：" + senkoConfig.getProjectDescription())
                // 联系
                .contact(new Contact(author.getName(), author.getUrl(), author.getEmail()))
                // 项目版本
                .version(senkoConfig.getVersion())
                .build();

    }

}
