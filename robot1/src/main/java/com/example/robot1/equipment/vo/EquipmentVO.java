package com.example.robot1.equipment.vo;

import lombok.Data;

//@Param查询条件
@Data
public class EquipmentVO {
    private String equipmentNum;
    private String useBy;
    private String belongTo;
    private Integer equipmentStatus;
    private Integer networkStatus;

    public EquipmentVO() {
    }

    //混合条件查询
    public EquipmentVO(String equipmentNum, String useBy, String belongTo, Integer equipmentStatus, Integer networkStatus) {
        this.equipmentNum = equipmentNum;
        this.useBy = useBy;
        this.belongTo = belongTo;
        this.equipmentStatus = equipmentStatus;
        this.networkStatus = networkStatus;
    }

}
