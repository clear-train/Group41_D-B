package com.example.robot1.equipment.service;

import com.example.robot1.equipment.domain.Equipment;
import com.example.robot1.equipment.mapper.EquipmentMapper;
import com.example.robot1.equipment.vo.EquipmentVO;
import com.example.robot1.exception.EquipmentException;
import com.example.robot1.pagedata.PageData;
import com.example.robot1.pages.Pages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class EquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    public EquipmentService(EquipmentMapper equipmentMapper) {
        this.equipmentMapper = equipmentMapper;
    }

    //分页查询
    public PageData<Equipment> getEquipmentPageData(EquipmentVO equipmentVO, Pages pages) {
        List<Equipment> equipmentList = equipmentMapper.selectEquipmentList(equipmentVO, pages.getStartIndex(), pages.getEndIndex());
        int totalItems = equipmentMapper.countEquipmentList(equipmentVO); // 查询总记录数
        return new PageData<>(true, equipmentList, totalItems);
    }


    @Transactional
    public PageData<Equipment> insertEquipment(Equipment equipment) {
        PageData<Equipment> insertData = new PageData<>();
        Integer equipmentId = equipment.getEquipmentId();
        Equipment existingEquipment = equipmentMapper.findById(equipmentId);
        try {
            if (existingEquipment != null) {
                insertData.setSuccess(false);
                insertData.setErrorFix("设备ID已存在，请输入其他设备ID");
            } else {
                // 其他插入操作
                if (equipment.getEquipmentStatus() != 1 && equipment.getEquipmentStatus() != 2) {
                    insertData.setSuccess(false);
                    insertData.setErrorFix("请输入正确的设备状态，1表示运行，2表示禁用");
                    if (equipment.getNetworkStatus() != 1 && equipment.getNetworkStatus() != 2 && equipment.getNetworkStatus() != 3) {
                        insertData.setErrorFix(insertData.getErrorFix() + "，请输入正确的网络状态，内网为1，外网为2，科研网为3");
                    }
                } else if (equipment.getNetworkStatus() != 1 && equipment.getNetworkStatus() != 2 && equipment.getNetworkStatus() != 3) {
                    insertData.setSuccess(false);
                    insertData.setErrorFix("请输入正确的网络状态，内网为1，外网为2，科研网为3");
                } else {
                    insertData.setErrorFix(null);
                    equipmentMapper.insertEquipment(equipment);
                    insertData.setEquipmentList(Collections.singletonList(equipment));
                    insertData.setSuccess(true);
                    log.info("设备成功插入");
                }


            }
        } catch (Exception e) {
            insertData.setSuccess(false);
            insertData.setErrorFix("插入设备失败：" + e.getMessage());
            log.error("插入设备失败", e);
        }
        return insertData;
    }

    //删除某个资产(id)
    public PageData<Equipment> deleteById(Integer equipmentId) {
        Equipment existingEquipment = equipmentMapper.findById(equipmentId);
        PageData<Equipment> deleteData = new PageData<>();

        try {
            if (existingEquipment == null) {
                deleteData.setSuccess(false);
                deleteData.setErrorFix("设备ID不存在，请输入其他设备ID");
            } else {
                equipmentMapper.deleteById(equipmentId);
                deleteData.setSuccess(true);
                log.info("设备成功删除");
            }
        } catch (Exception e) {
            log.error("删除设备失败", e);
        }
        return deleteData;
    }


    //修改某个资产（id）
    //根据id查询单个数据
    public Equipment findById(Integer equipmentId) {
        return equipmentMapper.findById(equipmentId);
    }

    @Transactional(rollbackFor = Exception.class)
    // 事务
    public void updateEquipment(Equipment updatedEquipment) {
        Equipment existingEquipment = findById(updatedEquipment.getEquipmentId());
        if (Objects.isNull(existingEquipment)) {
            throw new EquipmentException("输入的id不存在，请重新输入");
        }
        // 业务校验
        String check = check(updatedEquipment);
        if (StringUtils.hasLength(check)) {
            throw new EquipmentException(check);
        }
        // 正确逻辑
        existingEquipment.setNetworkStatus(updatedEquipment.getNetworkStatus());
        existingEquipment.setEquipmentStatus(updatedEquipment.getEquipmentStatus());
        int i = equipmentMapper.updateEquipment(updatedEquipment.getEquipmentId(), existingEquipment);
        if (i != 1) {
            log.error("更新设备信息影响数据量异常，期望1行，实际{}行", i);
            throw new EquipmentException("更新设备信息影响数据量异常");
        }
        log.info("设备修改成功");
    }


    private String check(Equipment updatedEquipment) {
        List<Integer> statusList = Arrays.asList(1, 2);
        if (!statusList.contains(updatedEquipment.getEquipmentStatus())) {
            return "请输入正确的设备状态，1表示运行，2表示禁用";
        }
        if (updatedEquipment.getNetworkStatus() != 1 && updatedEquipment.getNetworkStatus() != 2 && updatedEquipment.getNetworkStatus() != 3) {
            return "请输入正确的网络状态，内网为1，外网为2，科研网为3";
        }
        return null;
    }
}

    /*


if (updatedEquipment.getEquipmentNum() != null) {
                    existingEquipment.setEquipmentNum(updatedEquipment.getEquipmentNum());
                }
                if (updatedEquipment.getSerialNum() != null) {
                    existingEquipment.setSerialNum(updatedEquipment.getSerialNum());
                }
                if (updatedEquipment.getEquipmentStatus() != null) {
                    existingEquipment.setEquipmentStatus(updatedEquipment.getEquipmentStatus());
                }
                if (updatedEquipment.getNetworkStatus() != null) {
                    existingEquipment.setNetworkStatus(updatedEquipment.getNetworkStatus());
                }
                if (updatedEquipment.getRefer() != null) {
                    existingEquipment.setRefer(updatedEquipment.getRefer());
                }
                if (updatedEquipment.getBelongTo() != null) {
                    existingEquipment.setBelongTo(updatedEquipment.getBelongTo());
                }
                if (updatedEquipment.getUseBy() != null) {
                    existingEquipment.setUseBy(updatedEquipment.getUseBy());
                }
                if (updatedEquipment.getStartUse() != null) {
                    existingEquipment.setStartUse(updatedEquipment.getStartUse());
                }
                if (updatedEquipment.getBrand() != null) {
                    existingEquipment.setBrand(updatedEquipment.getBrand());
                }
                if (updatedEquipment.getSpecification() != null) {
                    existingEquipment.setSpecification(updatedEquipment.getSpecification());
                }
                if (updatedEquipment.getComponent() != null) {
                    existingEquipment.setComponent(updatedEquipment.getComponent());
                }
                if (updatedEquipment.getCostCenter() != null) {
                    existingEquipment.setCostCenter(updatedEquipment.getCostCenter());
                }
                if (updatedEquipment.getSN() != null) {
                    existingEquipment.setSN(updatedEquipment.getSN());
                }
                if (updatedEquipment.getCenterAddress() != null) {
                    existingEquipment.setCenterAddress(updatedEquipment.getCenterAddress());
                }
                if (updatedEquipment.getIP() != null) {
                    existingEquipment.setIP(updatedEquipment.getIP());
                }
                if (updatedEquipment.getOperatingSystem() != null) {
                    existingEquipment.setOperatingSystem(updatedEquipment.getOperatingSystem());
                }



*/



    /*

    //根据条件筛选查询
    public List<Equipment> getByConditions(EquipmentVO equipmentVO) {
        return equipmentMapper.selectByConditions(equipmentVO);
    }

    使用 PageHelper 进行分页查询
    public PageInfo<Equipment> searchEquipment(EquipmentVO equipmentVO, int page_index, int page_size) {
        // 使用PageHelper进行分页
        PageHelper.startPage(page_index, page_size);
        List<Equipment> equipmentList = equipmentMapper.searchEquipment(equipmentVO);
        return new PageInfo<>(equipmentList);
    }

    public List<Equipment> selectByList() {
        List<Employee> employeeList = employeeMapper.selectByExampleWithDepartment(null);
        return employeeList;
    }
 */


