package com.study.util.excel.easy_excel_util.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

/**
 * 门店信息excel对象
 */
@Data
public class StoreInformation {

    @ExcelProperty("店面名称")
    private String storeName;

    @ExcelProperty("门店代码")
    private String storeCode;

    @ExcelProperty("店面类型")
    private String storeType;

    @ExcelProperty("经销商编码")
    private String dealerCode;

    @ExcelProperty("经销商名称")
    private String dealerName;

    @ExcelProperty("经销商类型")
    private String dealerType;

    @ExcelProperty("店面经度")
    private String storeLongitude;

    @ExcelProperty("店面纬度")
    private String storeLatitude;

    @ExcelProperty("店面城市级别")
    private String cityLevel;

    @ExcelProperty("商圈名称")
    private String businessAreaName;

    @ExcelProperty("店面级别名称")
    private String storeLevelName;

    @ExcelProperty("店面属性")
    private String storeProperty;

    @ExcelProperty("店面特征")
    private String storeFeature;

    @ExcelProperty("店面位置")
    private String storeLocation;

    @ExcelProperty("装修时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private String decorateTime;

    @ExcelProperty("店面大区")
    private String storeDistrict;

    @ExcelProperty("店面战区")
    private String storeWarZone;

    @ExcelProperty("店面编号")
    private String shopCode;

    @ExcelProperty("营业面积")
    private String storeArea;

    @ExcelProperty("省（四级地址）")
    private String province;

    @ExcelProperty("市（四级地址）")
    private String city;

    @ExcelProperty("区县（四级地址）")
    private String country;

    @ExcelProperty("详细地址（四级地址）")
    private String detailAddress;

    @ExcelProperty("店面地址（合并）")
    private String storeAddress;

    @ExcelProperty("店面电话")
    private String storePhone;

    @ExcelProperty("店面状态")
    private String storeStatus;

}
