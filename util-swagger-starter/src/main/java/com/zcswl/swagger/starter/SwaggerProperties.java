package com.zcswl.swagger.starter;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 通用配置
 * @author zhoucg
 * @date 2020-08-19 16:58
 */
@Data
@ConfigurationProperties(prefix = SwaggerProperties.PREFIX)
public class SwaggerProperties {
    public static final String PREFIX = "util.swagger";
    /**
     * 是否开启swagger
     **/
    private Boolean enabled = true;

    /**
     * 是否生产环境
     */
    private Boolean production = false;

    /**
     * 标题
     **/
    private String title = "在线文档";
    private String group = "";
    /**
     * 描述
     **/
    private String description = "zuihou-admin-cloud 在线文档";
    /**
     * 版本
     **/
    private String version = "1.0";
    /**
     * 许可证
     **/
    private String license = "";
    /**
     * 许可证URL
     **/
    private String licenseUrl = "";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";

    private Contact contact = new Contact();

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "com.github.zuihou";
    private String basePath = "/";

    /**
     * 分组文档
     **/
    private Map<String, DocketInfo> docket = new LinkedHashMap<>();

    /**
     * host信息
     **/
    private String host = "";

    /**
     * 排序
     */
    private Integer order = 1;

    public String getGroup() {
        if (group == null || "".equals(group)) {
            return title;
        }
        return group;
    }

    @Data
    public static class DocketInfo {
        /**
         * 标题
         **/
        private String title = "在线文档";
        /**
         * 自定义组名
         */
        private String group = "";
        /**
         * 描述
         **/
        private String description = "zuihou-admin-cloud 在线文档";
        /**
         * 版本
         **/
        private String version = "";
        /**
         * 许可证
         **/
        private String license = "";
        /**
         * 许可证URL
         **/
        private String licenseUrl = "";
        /**
         * 服务条款URL
         **/
        private String termsOfServiceUrl = "";

        private Contact contact = new Contact();

        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";

        private String basePath = "/";

        /**
         * 排序
         */
        private Integer order = 1;

        public String getGroup() {
            if (group == null || "".equals(group)) {
                return title;
            }
            return group;
        }
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact {
        /**
         * 联系人
         **/
        private String name = "zuihou";
        /**
         * 联系人url
         **/
        private String url = "https://github.com/zuihou/zuihou-admin-cloud";
        /**
         * 联系人email
         **/
        private String email = "244387066@qq.com";
    }
}


