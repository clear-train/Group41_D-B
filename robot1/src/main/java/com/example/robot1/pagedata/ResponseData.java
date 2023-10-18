package com.example.robot1.pagedata;

import lombok.Data;

@Data
public class ResponseData {
    private Object data;//符合查询条件的每个结果的列表
    private boolean success;//是否成功获取请求
    private String errorFix;//如果未成果获取，显示错误来源
}
