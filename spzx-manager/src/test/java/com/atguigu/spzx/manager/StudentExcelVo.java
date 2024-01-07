package com.atguigu.spzx.manager;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StudentExcelVo {
    // index: 表示第几列
    @ExcelProperty(index = 0,value = "学生编号")
    private Integer stuId;
    @ExcelProperty(index = 1,value = "学生姓名")
    private String stuName;
    @ExcelProperty(index = 2,value = "学生年龄")
    private Integer stuAge;
}
