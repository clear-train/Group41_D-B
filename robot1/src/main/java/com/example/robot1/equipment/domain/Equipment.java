package com.example.robot1.equipment.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Builder
@Component
public class Equipment {
    private Integer equipmentId;
    private String equipmentNum;
    private String serialNum;
    private Integer equipmentStatus;
    private Integer networkStatus;
    private String refer;
    private String belongTo;
    private String useBy;
    private String startUse;
    private String brand;
    private String specification;
    private String component;
    private String costCenter;
    private String SN;
    private String centerAddress;
    private String IP;
    private String operatingSystem;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    public Equipment() {
    }

    public Equipment(Integer equipmentId, String equipmentNum, String serialNum, Integer equipmentStatus, Integer networkStatus, String refer, String belongTo, String useBy,String startUse, String brand, String specification, String component, String costCenter, String SN, String centerAddress, String IP, String operatingSystem, LocalDateTime createTime, LocalDateTime updateTime) {
        this.equipmentId=equipmentId;
        this.equipmentNum=equipmentNum;
        this.serialNum=serialNum;
        this.equipmentStatus=equipmentStatus;
        this.networkStatus=networkStatus;
        this.refer=refer;
        this.belongTo=belongTo;
        this.useBy=useBy;
        this.startUse=startUse;
        this.brand=brand;
        this.specification=specification;
        this.component=component;
        this.costCenter=costCenter;
        this.SN=SN;
        this.centerAddress=centerAddress;
        this.IP=IP;
        this.operatingSystem=operatingSystem;
        this.createTime=createTime;
        this.updateTime=updateTime;
    }




}
