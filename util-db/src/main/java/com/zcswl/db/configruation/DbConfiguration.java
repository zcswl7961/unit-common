package com.zcswl.db.configruation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhoucg
 * @date 2019-11-15 16:18
 */
@Data
@Component
@ConfigurationProperties(prefix = "build")
public class DbConfiguration {

    /**
     * 构建的数据库的默认数据库名称
     */
    private String schema;
}
