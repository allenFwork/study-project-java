package com.study.dao;

import lombok.Data;

import java.time.LocalDate;

/**
 * @description: 基础统计查询对象
 */
@Data
public class BasicDataModel {

    private Long qty;
    private LocalDate date;

}
