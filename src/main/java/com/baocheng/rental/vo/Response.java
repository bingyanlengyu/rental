package com.baocheng.rental.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private boolean success;
    private int code;
    private String message;
    private Object data;
}
