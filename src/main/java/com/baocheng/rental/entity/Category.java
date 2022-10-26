package com.baocheng.rental.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@TableName("car_category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @TableId("cat_id")
    private Integer catId;
    private String categoryName;
    private Integer stock;
}
