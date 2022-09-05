package com.senko.framework.config.swagger;

import com.senko.framework.config.SenkoConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

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
                .build();
        // TODO HEADER附带token

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
