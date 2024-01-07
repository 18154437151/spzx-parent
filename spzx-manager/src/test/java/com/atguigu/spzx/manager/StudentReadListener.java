package com.atguigu.spzx.manager;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;

// 读监听器
// public class StudentReadListener implements ReadListener<StudentExcelVo> {
public class StudentReadListener extends AnalysisEventListener<StudentExcelVo> {
    @Override
    public void invoke(StudentExcelVo studentExcelVo, AnalysisContext context) {
        // 用于读取excel中的每行数据（表格中有3行数据，invoke方法就会被执行3次）
        // 参数1就表示读取到的行数据
        System.out.println(studentExcelVo.getStuName());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 所有行都读取完成之后，最后执行的方法
        System.out.println("所有行都读取完成！");
    }
}

