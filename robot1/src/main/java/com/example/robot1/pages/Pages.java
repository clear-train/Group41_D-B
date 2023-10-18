package com.example.robot1.pages;

import lombok.Data;

@Data
public class Pages {
    private int pageSize;
    private int pageIndex;
    private int startIndex;
    private int endIndex;

    public int getStartIndex() {
        return (pageIndex - 1) * pageSize;
    }

    public int getEndIndex() {
        return pageIndex * pageSize;
    }

}
