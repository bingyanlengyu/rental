package com.baocheng.rental.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarsVO {
    private boolean success;
    private int code;
    private String message;
    private Integer total;
    private Object rows;
}
