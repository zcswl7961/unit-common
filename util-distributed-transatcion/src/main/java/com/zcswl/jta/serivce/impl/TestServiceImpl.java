package com.zcswl.jta.serivce.impl;

import com.zcswl.jta.entity.Order;
import com.zcswl.jta.entity.OrderItem;
import com.zcswl.jta.mapper.db1.TOrderMapper;
import com.zcswl.jta.mapper.db2.TOrderItemMapper;
import com.zcswl.jta.serivce.TestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhoucg
 * @date 2021-01-15 14:39
 */
@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final TOrderMapper tOrderMapper;
    private final TOrderItemMapper tOrderItemMapper;


    @Override
    @Transactional
    public void test() {

        Order order = new Order();
        order.setStatus("SUCCESS");
        order.setUserId(100);
        tOrderMapper.insertList(order);

        Long orderId = order.getOrderId();

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setUserId(1);
        tOrderItemMapper.insertList(orderItem);
    }
}
