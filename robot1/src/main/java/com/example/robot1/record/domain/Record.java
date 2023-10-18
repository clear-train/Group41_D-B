package com.example.robot1.record.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Record {
private Integer record_id;
private String record_type;
private String record_operation;
private Integer manager_id;
private LocalDateTime record_time;
private LocalDateTime create_time;
}
