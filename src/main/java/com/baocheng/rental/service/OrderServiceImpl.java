package com.baocheng.rental.service;

import com.baocheng.rental.entity.Order;
import com.baocheng.rental.mapper.OrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> queryOrderedCars() {
        return orderMapper.queryOrderedCars();
    }

    @Override
    public List<Order> selectList(QueryWrapper queryWrapper) {
        return orderMapper.selectList(queryWrapper);
    }
}
