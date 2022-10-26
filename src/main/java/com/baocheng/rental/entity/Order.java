package com.baocheng.rental.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("order_master")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @TableId(value = "order_id",type= IdType.AUTO)
    private Integer orderId;
    private Integer customerId;
    private Integer carId;
    private Long startDate;
    private Long endDate;
    private Integer orderStatus;
    private Date createTime;
}
