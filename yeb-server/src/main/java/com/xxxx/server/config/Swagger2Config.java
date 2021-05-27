package com.xxxx.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import sun.security.util.SecurityConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger 配置类
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    //规定扫描哪些包下面哪些文档
    @Bean
    public Docket createRestApi(){
        //DocumentationType.SWAGGER_2 是swagger 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()//扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.xxxx.server.controller"))
                //PathSelectors.any()任何路径都可以
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContext())//认证的路径
                //设置请求头消息
                .securitySchemes(securitySchemes());



    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("云E办接口文档")
                .description("云E办接口文档")
                .contact(new Contact("xxxx","http:localhost:8080/doc.html","xxxx@xxxx.com"))
                .version("1.0")
                .build();

    }

    /**
     * 用来设置请求头的信息
     * @return
     */
    private List<ApiKey> securitySchemes(){
        List<ApiKey> result=new ArrayList<>();
        //参数1:apikey的名字,参数2:key具体的名字,参数3:是参数2对应的值
        ApiKey apiKey=new ApiKey("Authorization","Authorization","header");
        result.add(apiKey);
        return result;
    }

    /**
     * 设置需要登入认证的路径
     * @return
     */
    private List<SecurityContext> securityContext(){
        List<SecurityContext> resultPath=new ArrayList<>();
        resultPath.add(getContextByPath("/hello/.*"));
        return resultPath;

    }

    //获取路径
    private SecurityContext getContextByPath(String pathRegx){

        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegx))
                .build();
    }

    /**
     * 默认授权
     * @return
     */
    private List<SecurityReference> defaultAuth(){
        List<SecurityReference> references=new ArrayList<>();
        AuthorizationScope authorizationScope=new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes=new AuthorizationScope[1];
        authorizationScopes[0]=authorizationScope;
        references.add(new SecurityReference("Authorization",authorizationScopes));
        return  references;
    }
}
