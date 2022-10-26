package com.baocheng.rental.controller;

import com.baocheng.rental.entity.Car;
import com.baocheng.rental.entity.Order;
import com.baocheng.rental.mapper.OrderMapper;
import com.baocheng.rental.service.CarService;
import com.baocheng.rental.service.OrderService;
import com.baocheng.rental.vo.CarsVO;
import com.baocheng.rental.vo.RentVO;
import com.baocheng.rental.vo.Response;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/car")
@Slf4j
@CrossOrigin
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMapper orderMapper;

    /**
     * 查询出 start 到 end 这段时间内可租用的空闲车辆.
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/cars")
    public ResponseEntity cars(@RequestParam("start") String start, @RequestParam("end") String end) {
        //参数检查 TODO
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Car> cars = carService.list(queryWrapper);
        if(cars.size() == 0){
            return ResponseEntity.ok().body(Response.builder().success(true).code(201).message("No cars").build());
        }
        List<Car> freeCars = new ArrayList<>(cars);
        List<Order> orders = orderMapper.queryOrderedCars();
        //每辆车检查日期是否交叉
            for(int j=0; j< orders.size(); j++){
                if(Long.valueOf(end) < orders.get(j).getStartDate() || Long.valueOf(start) > orders.get(j).getEndDate()){
                    //无交叉
                }else {
                    //交叉,移除car
                    long toRemove = orders.get(j).getCarId();
                    freeCars.removeIf(next -> (long)next.getCarId() == toRemove);
                }
            }
        return ResponseEntity.ok().body(CarsVO.builder().success(true).code(200).total(freeCars.size()).rows(freeCars).build());
    }

    /**
     * 选择一个空闲的车辆，下单租用
     * @param rentVO
     * @return
     */
    @PostMapping("/order")
    public ResponseEntity order(@RequestBody RentVO rentVO){
        //参数检查 TODO
        Order order = Order.builder().carId(rentVO.getCarId()).customerId(rentVO.getCustomerId())
                .startDate(Long.valueOf(rentVO.getStart())).endDate(Long.valueOf(rentVO.getEnd())).build();
        boolean result = orderService.save(order);
        return ResponseEntity.ok().body(Response.builder().success(result).code(200).build());
    }
    /**
     * 查询出车辆订单.
     *
     * @return
     */
    @GetMapping("/allOrders")
    public ResponseEntity allOrders(@RequestParam("customerId") String customerId) {
        //参数检查 TODO
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("customer_id", customerId);
        List<Order> orders = orderMapper.selectList(queryWrapper);

        return ResponseEntity.ok().body(CarsVO.builder().success(true).code(200).total(orders.size()).rows(orders).build());
    }
    /**
     * 还车接口，更新订单状态
     * @param orderMap
     * @return
     */
    @PutMapping("/returns")
    public ResponseEntity returns(@RequestBody Map<String, Object> orderMap){
        //参数检查 TODO
        Order order = orderService.getById((Integer)orderMap.get("orderId"));
        order.setOrderStatus(2);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("order_id", order.getOrderId());

        boolean result = orderService.update(order, updateWrapper);
        return ResponseEntity.ok().body(Response.builder().success(result).code(200).build());
    }

}
