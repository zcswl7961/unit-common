package com.zcswl.mybatis.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Table(name = "`analysis_sample`")
public class AnalysisSampleDO {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 源数据发现作业id
     */
    @Column(name = "`job_id`")
    private Long jobId;

    /**
     * 数据源id
     */
    @Column(name = "`config_id`")
    private Integer configId;

    /**
     * 数据源名称
     */
    @Column(name = "`config_name`")
    private String configName;

    /**
     * schema
     */
    @Column(name = "`schema`")
    private String schema;

    /**
     * 表格名字
     */
    @Column(name = "`table_name`")
    private String tableName;

    /**
     * 字段名称
     */
    @Column(name = "`column_name`")
    private String columnName;

    /**
     * 业务类型id
     */
    @Column(name = "`biz_id`")
    private Long bizId;

    /**
     * 业务类型名称
     */
    @Column(name = "`biz_name`")
    private String bizName;

    /**
     * 备注
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 数据信息id
     */
    @Column(name = "`meta_id`")
    private Long metaId;

    /**
     * 唯一分类id值
     */
    @Column(name = "`classify_id`")
    private Long classifyId;

}
