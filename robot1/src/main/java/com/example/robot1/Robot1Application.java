package com.example.robot1;

import com.example.robot1.equipment.domain.Equipment;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.example.robot1.equipment")
@MapperScan("com.example.robot1.equipment.mapper")
@PropertySource("classpath:application.properties")
public class Robot1Application {

    @Autowired
    private Equipment equipment;

    public static void main(String[] args) {

        SpringApplication.run(Robot1Application.class, args);

    }

    /*
    @PostConstruct
    public void initializeExampleClass() {
        //在Equipment实例中的equipment_status
        // 使用setter方法设置SET('1', '0')
        Set<String> set1 = new HashSet<>();
        set1.add("1");
        set1.add("0");
        equipment.setEquipment_status(set1);
        // 使用getter方法获取SET('1', '0')
        Set<String> equipment_status = equipment.getEquipment_status();

        //在Equipment实例中的network_status
        // 使用setter方法设置SET('1','2','3')
        Set<String> set2 = new HashSet<>();
        set2.add("1");
        set2.add("2");
        set2.add("3");
        equipment.setEquipment_status(set2);
        // 使用getter方法获取SET('1', '0')
        Set<String> network_status = equipment.getNetwork_status();
    }

     */

}

