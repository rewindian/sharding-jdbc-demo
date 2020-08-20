package io.ian.demo.web.config;

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
public class Swagger2Configuration {

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.ian.demo.web.controller"))//要扫描的API(Controller)基础包
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf() {
        Contact ct = new Contact("shardingjdbc", "", "");

        return new ApiInfoBuilder()
                .title("API文档")
                .contact(ct)
                .version("1.0")
                .build();
    }
}
