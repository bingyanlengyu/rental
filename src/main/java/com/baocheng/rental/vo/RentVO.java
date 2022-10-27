package com.baocheng.rental.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class RentVO {
    @NotNull
    private Integer customerId;
    @NotNull
    private Integer carId;
    @NotEmpty
    private String start;
    @NotEmpty
    private String end;
}
