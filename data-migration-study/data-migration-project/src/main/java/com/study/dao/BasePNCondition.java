package com.study.dao;

import lombok.Data;

import java.util.List;

/**
 * @description: 基础统计查询对象
 */
@Data
public class BasePNCondition {
    private List<String> pns;
    private String chargeLineName;
    private String planCategoryName;
}
