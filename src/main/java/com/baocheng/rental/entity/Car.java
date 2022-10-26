package com.baocheng.rental.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("car_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @TableId("car_id")
    private Integer carId;
    private Integer catId;
}
