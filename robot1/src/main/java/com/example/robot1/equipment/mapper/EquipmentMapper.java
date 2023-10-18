package com.example.robot1.equipment.mapper;

import com.example.robot1.equipment.domain.Equipment;
import com.example.robot1.equipment.vo.EquipmentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EquipmentMapper {
    @Select("<script>" +
            "SELECT COUNT(*) " +
            "FROM equipment " +
            "<where>" +
            // 根据条件判断是否拼接对应的查询条件
            "<if test='equipmentVO.equipmentNum != null'> AND equipment_num = #{equipmentVO.equipmentNum}</if>" +
            "<if test='equipmentVO.useBy != null'> AND use_by = #{equipmentVO.useBy}</if>" +
            "<if test='equipmentVO.belongTo != null'> AND belong_to = #{equipmentVO.belongTo}</if>" +
            "<if test='equipmentVO.equipmentStatus != null'> AND equipment_status = #{equipmentVO.equipmentStatus}</if>" +
            "<if test='equipmentVO.networkStatus != null'> AND network_status = #{equipmentVO.networkStatus}</if>" +
            "</where>" +
            "</script>")
    int countEquipmentList(@Param("equipmentVO") EquipmentVO equipmentVO);

    // 分页查询设备列表
    @Select("<script>" +
            "SELECT * " +
            "FROM equipment " +
            "<where>" +
            "<if test='equipmentVO.equipmentNum != null'> AND equipment_num = #{equipmentVO.equipmentNum}</if>" +
            "<if test='equipmentVO.useBy != null'> AND use_by = #{equipmentVO.useBy}</if>" +
            "<if test='equipmentVO.belongTo != null'> AND belong_to = #{equipmentVO.belongTo}</if>" +
            "<if test='equipmentVO.equipmentStatus != null'> AND equipment_status = #{equipmentVO.equipmentStatus}</if>" +
            "<if test='equipmentVO.networkStatus != null'> AND network_status = #{equipmentVO.networkStatus}</if>" +
            "</where>" +
            "LIMIT #{startIndex}, #{endIndex}" +
            "</script>")
    List<Equipment> selectEquipmentList(@Param("equipmentVO") EquipmentVO equipmentVO,
                                        @Param("startIndex") int startIndex,
                                        @Param("endIndex") int endIndex);


    //插入新的资产数据
    @Insert("INSERT INTO equipment(equipment_id,equipment_num,serial_num,equipment_status,network_status,refer,belong_to,use_by,start_use,brand,specification,component,cost_center,SN,center_address,IP,operating_system)" +
            "VALUES(#{equipment.equipmentId},#{equipment.equipmentNum},#{equipment.serialNum},#{equipment.equipmentStatus},#{equipment.networkStatus},#{equipment.refer},#{equipment.belongTo},#{equipment.useBy},#{equipment.startUse},#{equipment.brand},#{equipment.specification},#{equipment.component},#{equipment.costCenter},#{equipment.SN},#{equipment.centerAddress},#{equipment.IP},#{equipment.operatingSystem})")
    void insertEquipment(@Param("equipment") Equipment equipment);

    //删除资产数据（根据这行的id）
    @Delete("DELETE FROM equipment WHERE equipment_id = #{equipmentId}")
    int deleteById(Integer equipmentId);

    //修改
    //先展示根据id查询出单个数据
    @Select("SELECT * FROM equipment WHERE equipment_id=#{equipmentId}")
    Equipment findById(Integer equipmentId);

    @Update("UPDATE equipment SET equipment_num = #{updatedEquipment.equipmentNum},serial_num=#{updatedEquipment.serialNum}, equipment_status=#{updatedEquipment.equipmentStatus}, network_status=#{updatedEquipment.networkStatus}, refer=#{updatedEquipment.refer}, belong_to=#{updatedEquipment.belongTo}, use_by=#{updatedEquipment.useBy}, start_use=#{updatedEquipment.startUse}, brand=#{updatedEquipment.brand}, specification=#{updatedEquipment.specification}, component=#{updatedEquipment.component}, cost_center=#{updatedEquipment.costCenter},SN=#{updatedEquipment.SN}, center_address=#{updatedEquipment.centerAddress},IP=#{updatedEquipment.IP},operating_system=#{updatedEquipment.operatingSystem} WHERE equipment_id = #{equipmentId}")
    int updateEquipment(Integer equipmentId,Equipment updatedEquipment);

}

    /*

    //修改某个资产数据（根据id）
    @Update("UPDATE equipment SET equipment_id = #{equipmentId}, equipment_num = #{equipmentNum},serial_num=#{serialNum}, equipment_status=#{equipmentStatus}, network_status=#{networkStatus}, refer=#{refer}, belong_to=#{belongTo}, use_by=#{useBy}, start_use=#{startUse}, brand=#{brand}, specification=#{specification}, component=#{component}, cost_center=#{costCenter},SN=#{SN}, center_address=#{centerAddress},IP=#{IP},operating_system=#{operatingSystem} WHERE equipment_id = #{equipmentId}")
    int updateById(Equipment equipmentId);



    //多条件查询
    @SelectProvider(type = EquipmentVOProvider.class, method = "selectByConditions")
    List<Equipment> selectEquipmentList(@Param("equipmentVO") EquipmentVO equipmentVO,
                                        @Param("startIndex") int startIndex,
                                        @Param("pageSize") int pageSize);

"<where>" +
            "<if test='equipment.equipmentId != null'>AND equipment_id = #{equipment.equipmentId}</if>" +
            "<if test='equipment.equipmentNum != null'>AND equipment_num = #{equipment.equipmentNum}</if>" +
            "<if test='equipment.serialNum!= null'>AND serial_num = #{equipment.serialNum}</if>" +
            "<if test='equipment.equipmentStatus != 0'>AND equipment_status = #{equipment.equipmentStatus}</if>" +
            "<if test='equipment.networkStatus != 0'>AND network_status = #{equipment.networkStatus}</if>" +
            "<if test='equipment.describe != null'>AND describe = #{equipment.describe}</if>" +
            "<if test='equipment.belongTo != null'>AND belong_to = #{equipment.belongTo}</if>" +
            "<if test='equipment.useBy != null'>AND use_by = #{equipment.useBy}</if>" +
            "<if test='equipment.startUse != null'>AND start_use = #{equipment.startUse}</if>" +
            "<if test='equipment.brand != null'>AND brand = #{equipment.brand}</if>" +
            "<if test='equipment.specification != null'>AND specification = #{equipment.specification}</if>" +
            "<if test='equipment.component != null'>AND component = #{equipment.component}</if>" +
            "<if test='equipment.costCenter != null'>AND cost_Center = #{equipment.costCenter}</if>" +
            "<if test='equipment.SN != null'>AND SN = #{equipment.SN}</if>" +
            "<if test='equipment.centerAddress != null'>AND center_address = #{equipment.centerAddress}</if>" +
            "<if test='equipment.IP != null'>AND IP = #{equipment.IP}</if>" +
            "<if test='equipment.operatingSystem != null'>AND operating_System = #{equipment.operatingSystem}</if>" +
            "</where>"+

    class EquipmentVOProvider {
        public String selectByConditions(EquipmentVO equipmentVO) {
            SQL sql = new SQL()
                    .SELECT("*")
                    .FROM("equipment");

            if (equipmentVO.getEquipment_num() != null) {
                sql.WHERE("equipment_num = #{equipment_num}");
            }
            if (equipmentVO.getUse_by() != null) {
                sql.WHERE("use_by = #{use_by}");
            }
            if (equipmentVO.getBelong_to() != null) {
                sql.WHERE("belong_to = #{belong_to}");
            }
            if (equipmentVO.getEquipment_status() != null) {
                sql.WHERE("equipment_status = #{equipment_status}");
            }
            if (equipmentVO.getNetwork_status() != null) {
                sql.WHERE("network_status = #{network_status}");
            }
            // 添加筛选条件字段对应的WHERE语句

            return sql.toString();
        }
    }

插入新的资产数据
    @Insert("INSERT INTO equipment(equipment_id,equipment_num,serial_num,equipment_status,network_status,describe,belong_to,use_by,start_use,brand,specification,component,cost_center,SN,center_address,IP,operating_system,create_time,update_time) VALUES(#{equipment_id},#{equipment_num},#{serial_num},#{equipment_status},#{network_status},#{describe},#{belong_to},#{use_by},#{start_use},#{brand},#{specification},#{component},#{cost_center},#{SN},#{center_address},#{IP},#{operating_system},#{create_time},#{update_time})")
    void insertEquipment(Equipment equipment);







/*
    //查看-筛选设备状态操作getEquipmentStatus（）
    @Select("SELECT * FROM equipment WHERE equipment_status = #{selection}")
    List<Equipment> getEquipmentStatus(@Param("equipment_status") String selection);

    //查看-筛选网络状态操作getNetworkStatus（）
    @Select("SELECT * FROM equipment WHERE network_status = #{selection}")
    List<Equipment> getNetworkStatus(@Param("network_status") String selection);

    //查看-筛选资产编号（输入框）
    @Select("SELECT * FROM equipment WHERE equipment_num = #{selection}")
    List<Equipment> getEquipmentNum(@Param("equipment_num") String selection);

    //查看-筛选使用人
    @Select("SELECT * FROM equipment WHERE use_by=#{selection}")
    List<Equipment> getUseBy(@Param("use_by") String selection);

    //查看-筛选归属人
    @Select("SELECT * FROM equipment WHERE belong_to=#{selection}")
    List<Equipment> getBelongTo(@Param("belong to") String selection);

     */


