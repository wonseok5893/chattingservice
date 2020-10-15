package com.playground.th.controller.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseData <T> {
    private int result;
    private T data;

}
