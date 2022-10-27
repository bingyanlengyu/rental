package com.baocheng.rental.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderReturnVO {
    @Pattern(regexp = "^[1-9]\\d*|0$")
    private String orderId;
}
