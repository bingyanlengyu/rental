package com.baocheng.rental.mapper;

import com.baocheng.rental.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select * from order_master t where t.order_status=1")
    List<Order> queryOrderedCars();
}
