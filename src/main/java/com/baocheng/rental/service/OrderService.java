package com.baocheng.rental.service;

import com.baocheng.rental.entity.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OrderService extends IService<Order> {
    List<Order> queryOrderedCars();

    List<Order> selectList(QueryWrapper queryWrapper);
}
