package com.zcswl.mybatis.mapper;


import com.zcswl.mybatis.controller.BaseMapper;
import com.zcswl.mybatis.entity.AnalysisSampleDO;
import com.zcswl.mybatis.entity.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 */
public interface AnalysisSampleMapper extends BaseMapper<AnalysisSampleDO> {

    void updateBatch(@Param("classifyId") Long classifyId, @Param("ids") List<Long> ids);


    List<Select<Long, Long>> searchClassifyId(@Param("jobId") Long jobId);

    void updateClassifId(List<Select<Long, Long>> list);


    void xx(@Param("ids") List<Long> ids);

    void yyy(@Param("jobId") Long jobId, @Param("configId") Long configId);

    void zzz(@Param("id") Long id, @Param("jobId") Long jobId);

    void ppp(@Param("jobId") Long jobId);

    void saveClassifyId(@Param("jobId") Long jobId);

}
