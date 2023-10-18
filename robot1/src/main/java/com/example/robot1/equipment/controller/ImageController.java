package com.example.robot1.equipment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ImageController {
    @Value("${image.folder.path}")
    private String imageFolderPath;

    private List<String> imageList = new ArrayList<>();

    @GetMapping("/api/images")
    public List<String> getImages() {
        refreshImageList();
        return imageList;
    }

    private void refreshImageList() {
        File folder = new File(imageFolderPath);
        File[] imageFiles = folder.listFiles((dir, name) -> name.endsWith(".jpg")); // 修改为你的图片格式
        imageList.clear();

        if (imageFiles != null) {
            for (File file : imageFiles) {
                imageList.add("D:\\desk\\yolov8\\runs\\detect\\predict" + file.getName());
            }
        }
    }
}