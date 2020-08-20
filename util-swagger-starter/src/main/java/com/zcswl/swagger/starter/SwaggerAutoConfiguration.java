package com.zcswl.swagger.starter;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * swagger 扫描
 *
 * @author zhoucg
 * @date 2020-08-19 17:32
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@AllArgsConstructor
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration{

    private final SwaggerProperties swaggerProperties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "util.swagger.enabled", havingValue = "true", matchIfMissing = true)
    public List<Docket> restApi() {
        List<Docket> docketList = new LinkedList<>();

        if (swaggerProperties.getDocket().isEmpty()) {
            docketList.add(createDocket(swaggerProperties));
            return docketList;
        }
        // 分组创建
        for (String groupName : swaggerProperties.getDocket().keySet()) {
            SwaggerProperties.DocketInfo docketInfo = swaggerProperties.getDocket().get(groupName);

            ApiInfo apiInfo = new ApiInfoBuilder()
                    .title(docketInfo.getTitle().isEmpty() ? swaggerProperties.getTitle() : docketInfo.getTitle())
                    .description(docketInfo.getDescription().isEmpty() ? swaggerProperties.getDescription() : docketInfo.getDescription())
                    .version(docketInfo.getVersion().isEmpty() ? swaggerProperties.getVersion() : docketInfo.getVersion())
                    .license(docketInfo.getLicense().isEmpty() ? swaggerProperties.getLicense() : docketInfo.getLicense())
                    .licenseUrl(docketInfo.getLicenseUrl().isEmpty() ? swaggerProperties.getLicenseUrl() : docketInfo.getLicenseUrl())
                    .contact(
                            new Contact(
                                    docketInfo.getContact().getName().isEmpty() ? swaggerProperties.getContact().getName() : docketInfo.getContact().getName(),
                                    docketInfo.getContact().getUrl().isEmpty() ? swaggerProperties.getContact().getUrl() : docketInfo.getContact().getUrl(),
                                    docketInfo.getContact().getEmail().isEmpty() ? swaggerProperties.getContact().getEmail() : docketInfo.getContact().getEmail()
                            )
                    )
                    .termsOfServiceUrl(docketInfo.getTermsOfServiceUrl().isEmpty() ? swaggerProperties.getTermsOfServiceUrl() : docketInfo.getTermsOfServiceUrl())
                    .build();


            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    .host(swaggerProperties.getHost())
                    .apiInfo(apiInfo)
                    .groupName(docketInfo.getGroup())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
                    .build();
            docketList.add(docket);
        }
        return docketList;

    }


    /**
     * 创建 Docket对象
     *
     * @param swaggerProperties swagger配置
     * @return Docket
     */
    private Docket createDocket(SwaggerProperties swaggerProperties) {
        //API 基础信息
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(),
                        swaggerProperties.getContact().getUrl(),
                        swaggerProperties.getContact().getEmail()))
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .build();

        /**
         * @See Docket most params
         */
        return new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo)
                .groupName(swaggerProperties.getGroup())
                .select()
                // 基础包路径
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                // 路径排除
                .build();
    }
}
