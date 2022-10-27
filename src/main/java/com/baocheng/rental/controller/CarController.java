package com.baocheng.rental.controller;

import com.baocheng.rental.entity.Car;
import com.baocheng.rental.entity.Order;
import com.baocheng.rental.mapper.OrderMapper;
import com.baocheng.rental.service.CarService;
import com.baocheng.rental.service.OrderService;
import com.baocheng.rental.util.CommonUtil;
import com.baocheng.rental.vo.CarsVO;
import com.baocheng.rental.vo.OrderReturnVO;
import com.baocheng.rental.vo.RentVO;
import com.baocheng.rental.vo.Response;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/car")
@Slf4j
@CrossOrigin
@Validated
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    OrderService orderService;


    /**
     * 查询出 start 到 end 这段时间内可租用的空闲车辆.
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/cars")
    public ResponseEntity cars(@NotEmpty @RequestParam("start") String start,
                               @NotEmpty @RequestParam("end") String end) {
        //参数检查 TODO
        if(!checkDate(start, end)){
            return ResponseEntity.ok().body(Response.builder().success(false).code(HttpStatus.BAD_REQUEST.value())
                    .message("start date can not be greater than end.").build());
        }
        if(!CommonUtil.checkDateStr(start) || !CommonUtil.checkDateStr(end)){
            return ResponseEntity.ok().body(Response.builder().success(false).code(HttpStatus.BAD_REQUEST.value())
                    .message("start date or end date illegal.").build());
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Car> cars = carService.list(queryWrapper);
        if(cars.size() == 0){
            return ResponseEntity.ok().body(Response.builder().success(true).code(HttpStatus.NO_CONTENT.value()).message("No cars").build());
        }
        List<Car> freeCars = new ArrayList<>(cars);
        List<Order> orders = orderService.queryOrderedCars();
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
        return ResponseEntity.ok().body(CarsVO.builder().success(true).code(HttpStatus.OK.value()).total(freeCars.size()).rows(freeCars).build());
    }


    /**
     * 选择一个空闲的车辆，下单租用
     * @param rentVO
     * @return
     */
    @PostMapping("/order")
    public ResponseEntity order(@Valid @RequestBody RentVO rentVO){
        //参数检查 TODO
        if(!checkDate(rentVO.getStart(),rentVO.getEnd())){
            return ResponseEntity.ok().body(Response.builder().success(false).code(HttpStatus.BAD_REQUEST.value())
                    .message("start date can not be greater than end.").build());
        }
        if(!CommonUtil.checkDateStr(rentVO.getStart()) || !CommonUtil.checkDateStr(rentVO.getEnd())){
            return ResponseEntity.ok().body(Response.builder().success(false).code(HttpStatus.BAD_REQUEST.value())
                    .message("start date or end date illegal.").build());
        }
        Order order = Order.builder().carId(rentVO.getCarId()).customerId(rentVO.getCustomerId())
                .startDate(Long.valueOf(rentVO.getStart())).endDate(Long.valueOf(rentVO.getEnd())).build();
        boolean result = orderService.save(order);
        return ResponseEntity.ok().body(Response.builder().success(result).code(HttpStatus.OK.value()).build());
    }
    /**
     * 查询出车辆订单.
     *
     * @return
     */
    @GetMapping("/allOrders")
    public ResponseEntity allOrders(@NotEmpty @Pattern(regexp = "^[1-9]\\d*|0$") @RequestParam("customerId") String customerId) {
        //参数检查 TODO
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("customer_id", customerId);
        List<Order> orders = orderService.selectList(queryWrapper);

        return ResponseEntity.ok().body(CarsVO.builder().success(true).code(HttpStatus.OK.value()).total(orders.size()).rows(orders).build());
    }
    /**
     * 还车接口，更新订单状态
     * @param orderReturnVO
     * @return
     */
    @PutMapping("/returns")
    public ResponseEntity returns(@Valid @RequestBody OrderReturnVO orderReturnVO){
        //参数检查 TODO
        Order order = orderService.getById(orderReturnVO.getOrderId());
        if(order == null){
            return ResponseEntity.ok().body(Response.builder().success(false).code(HttpStatus.NO_CONTENT.value()).
                    message("order not exists.").build());
        }
        order.setOrderStatus(2);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("order_id", order.getOrderId());

        boolean result = orderService.update(order, updateWrapper);
        return ResponseEntity.ok().body(Response.builder().success(result).code(HttpStatus.OK.value()).build());
    }

    private boolean checkDate(@NotEmpty String start, @NotEmpty String end){
        boolean result = true;
        if(start.compareTo(end) > 0){
            result=false;
        }
        return result;
    }

}
