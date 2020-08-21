package com.gome.oa.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                // 指定此package中的接口显示在接口文档中
                .apis(RequestHandlerSelectors.basePackage("com.gome.oa.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder().
                // 文档标题
                title("Swagger接口文档")
                // 联系人
                .contact(new Contact("dmx",null,"315832247@qq.com"))
                // 文档描述
                .description("测试平台接口文档")
                // 版本
                .version("1.0")
                .build();
    }

}
