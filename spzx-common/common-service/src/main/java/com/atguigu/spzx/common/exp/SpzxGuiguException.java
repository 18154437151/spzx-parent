package com.atguigu.spzx.common.exp;

import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class SpzxGuiguException extends RuntimeException {
    private Integer code;
    private String message;
    public SpzxGuiguException(Integer code,String message){
        this.code = code;
        this.message = message;
    }
    public SpzxGuiguException(ResultCodeEnum resultCodeEnum){
        this(resultCodeEnum.getCode(),resultCodeEnum.getMessage());
    }
}
