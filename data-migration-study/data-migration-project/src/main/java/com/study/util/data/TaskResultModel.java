package com.study.util.data;

import lombok.Data;

@Data
public class TaskResultModel {

    private String taskName;
    private String startTime;
    private String endTime;
    private String costTime;
    private Integer taskNum;

}
