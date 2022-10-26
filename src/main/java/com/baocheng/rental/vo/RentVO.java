package com.baocheng.rental.vo;

import lombok.Data;


@Data
public class RentVO {

    private Integer customerId;

    private Integer carId;

    private String start;

    private String end;
}
