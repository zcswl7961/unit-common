package com.zcswl.jta.mapper.db1;

import com.zcswl.jta.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucg
 * @date 2021-01-15 14:21
 */
@Repository
public interface TOrderMapper {

    int insertList(@Param("order") Order order);
}
