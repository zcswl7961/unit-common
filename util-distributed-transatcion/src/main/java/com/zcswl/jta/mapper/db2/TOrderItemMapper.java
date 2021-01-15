package com.zcswl.jta.mapper.db2;

import com.zcswl.jta.entity.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucg
 * @date 2021-01-15 14:30
 */
@Repository
public interface TOrderItemMapper {

    int insertList(@Param("orderItem") OrderItem orderItem);

}
