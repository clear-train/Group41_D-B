package com.example.robot1.pagedata;

import lombok.Data;

import java.util.List;

@Data
public class PageData<E> {
    private List<E> equipmentList;//符合查询条件的每个结果的列表
    private int account;//符合查询条件的结果数量
    private boolean success;//是否成功获取请求
    private String errorFix;//如果未成果获取，显示错误来源

    public PageData() {
    }

    public PageData(boolean success, List<E> equipmentList, int account) {
        this.equipmentList = equipmentList;
        this.account = account;
        this.success = success;
    }

    public PageData(boolean success, String errorFix) {
        this.success = success;
        this.errorFix = errorFix;
    }

}
