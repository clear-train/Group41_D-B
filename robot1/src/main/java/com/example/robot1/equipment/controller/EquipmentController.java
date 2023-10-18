package com.example.robot1.equipment.controller;

import com.example.robot1.equipment.domain.Equipment;
import com.example.robot1.equipment.service.EquipmentService;
import com.example.robot1.equipment.vo.EquipmentVO;
import com.example.robot1.exception.EquipmentException;
import com.example.robot1.pagedata.PageData;
import com.example.robot1.pagedata.ResponseData;
import com.example.robot1.pages.Pages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("equipment")
@Slf4j
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/search")
    public PageData<Equipment> searchEquipment(@ModelAttribute EquipmentVO equipmentVO, @ModelAttribute Pages pages) {
        PageData<Equipment> pageData;
        try {
            // 调用Service层方法获取分页查询结果
            pageData = equipmentService.getEquipmentPageData(equipmentVO, pages);
        } catch (RuntimeException e) {
            log.error("查询设备信息列表发生异常", e);
            return new PageData<>(false, e.getMessage());
        } catch (Exception e) {
            log.error("查询设备信息列表发生异常", e);
            return new PageData<>(false, "发生异常");
        }

        return pageData;
    }

    @RequestMapping(value = "/insert")
    public PageData<Equipment> insertEquipment(@RequestBody Equipment equipment) {
        PageData<Equipment> insertData = equipmentService.insertEquipment(equipment);
        return insertData;
    }
/*
   //插入新的资产,只能一条一条插入
    @RequestMapping(value = "/insert")
    public PageData<Equipment> insertEquipment(@RequestBody Equipment equipment) {
        PageData<Equipment> insertData=new PageData<>();
        try {
            Integer equipmentId = equipment.getEquipmentId();
            Equipment existingEquipment = equipmentService.findById(equipmentId);
            if (existingEquipment != null) {
                insertData.setSuccess(false);
                insertData.setErrorFix("设备ID已存在，请输入其他设备ID");
            } else {
                equipmentService.insertEquipment(equipment);
                insertData.setEquipmentList(Collections.singletonList(equipment));
                insertData.setSuccess(true);
                log.info("设备成功插入");
            } }catch (Exception e) {
                insertData.setSuccess(false);
                insertData.setErrorFix("插入设备失败：" + e.getMessage());
                log.error("插入设备失败", e);
            }
        return insertData;
    }

 */



    //删除某个资产（id）
    @DeleteMapping("/delete/{id}")
    public  PageData<Equipment> deleteEquipment(@PathVariable("id") Integer equipmentId){
            return equipmentService.deleteById(equipmentId);
    }

    //修改页面
    //响应到达更新数据的页面
    @GetMapping("/toUpdate/{id}")
    public Equipment toUpdate(@PathVariable("id") Integer equipmentId) {
        try {
             equipmentService.findById(equipmentId);
            log.info("修改成功");
        } catch (Exception e) {
            log.error("修改失败", e);
        }
        return equipmentService.findById(equipmentId);
    }


    //更新数据请求并返回getall
    @PostMapping("update")
    public ResponseData update(@RequestBody Equipment updatedEquipment) {
        ResponseData updateData = new ResponseData();
        try {
            equipmentService.updateEquipment(updatedEquipment);
            updateData.setSuccess(true);
            updateData.setData("成功");
            return updateData;
        } catch (EquipmentException e) {
            log.error("修改设备失败,{}", e.getMessage(), e);
            updateData.setSuccess(false);
            updateData.setErrorFix("修改设备失败：" + e.getMessage());
            return updateData;
        } catch (Exception e) {
            log.error("修改设备失败", e);
            updateData.setSuccess(false);
            updateData.setErrorFix("修改设备失败");
            return updateData;
        }
    }
}





/*

//修改
    @PutMapping("/update")
    public String updateUser(Equipment equipment) {
        equipmentService.updateEquipment(equipment);
        System.out.println(equipment);
        return "redirect:/equipmentList";
    }

    // 根据下拉框选项的值执行筛选设备状态操作getEquipmentStatus（）
    @GetMapping("/getEquipmentStatus")
    public List<Equipment> getEquipmentStatus(@RequestParam String selection) {
        List<Equipment> getEquipmentStatus = equipmentService.getEquipmentStatus(selection);
        return getEquipmentStatus;
    }

    // 根据下拉框选项的值执行筛选网络状态操作getNetworkStatus（）
    @GetMapping("/getNetworkStatus")
    public List<Equipment> getNetworkStatus(@RequestParam String selection) {
        List<Equipment> getNetworkStatus = equipmentService.getNetworkStatus(selection);
        return getNetworkStatus;
    }

    //输入框输入资产编号查找资产信息getEquipmentNum()
    @GetMapping("/getEquipmentNum")
    public List<Equipment> getEquipmentNum(@RequestParam String selection){
        List<Equipment> getEquipmentNum=equipmentService.getEquipmentStatus(selection);
        return getEquipmentNum;
    }

    //根据使用人查找资产信息
    @GetMapping("/getUseBy")
    public  List<Equipment> getUseBy(@RequestParam String selection){
        List<Equipment> getUseBy=equipmentService.getUseBy(selection);
        return getUseBy;
    }

    //根据资产拥有人的id查找资产id
    @GetMapping("/getBelongTo")
    public List<Equipment> getBelongTo(@RequestParam String selection){
        List<Equipment> getBelongTo=equipmentService.getBelongTo(selection);
        return getBelongTo;
    }


 */


    /*修改某个资产

     * 用户修改前页面跳转-----用户点击"修改"按钮时，进入此处进行跳转到编辑页面
     * 因为修改与添加页面一致，所以复制一份新的，当然可以采用同一个页面。
     *
     * @param userId ：待修改的用户id
     * @param model
     * @return

    @GetMapping(value="userCRUD/{userId}")
    public String EditUser(@PathVariable(value="userId") Integer userId,Model model)
    {
        User user=userDaoImpl.getUserById(userId);
        List<Department> departmentList=departmentDao.getDepartmentList();
        model.addAttribute("departmentList",departmentList);
        model.addAttribute("user",user);
        return "editUser";
    }





     */



    /*根据条件查询
    @GetMapping("/equipmentVO")
    public List<Equipment> getMyVOByConditions(EquipmentVO equipmentVO) {
        return equipmentService.getByConditions(equipmentVO);
    }

    //分页
    @GetMapping("/page")
    public PageInfo<Equipment> searchEquipment(EquipmentVO equipmentVO, @RequestParam(defaultValue = "1") int page_index, @RequestParam(defaultValue = "10") int page_size) {
        return equipmentService.searchEquipment(equipmentVO, page_index, page_size);
    }

     */





    /*
    @GetMapping("/equipment/status")
    public static ResponseEntity<List<String>> getEquipmentStatus() {
        Set<String> equipmentStatusSet = new HashSet<>();
        equipmentStatusSet.add("1");
        equipmentStatusSet.add("0");
        List<String> equipmentStatusList = new ArrayList<>(equipmentStatusSet);
        return ResponseEntity.ok(equipmentStatusList);
    }

     */

