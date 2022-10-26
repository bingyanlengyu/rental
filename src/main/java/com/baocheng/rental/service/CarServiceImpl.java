package com.baocheng.rental.service;

import com.baocheng.rental.entity.Car;
import com.baocheng.rental.mapper.CarMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService  {
}
