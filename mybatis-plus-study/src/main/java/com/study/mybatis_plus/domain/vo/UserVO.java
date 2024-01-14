package com.study.mybatis_plus.domain.vo;

import com.study.mybatis_plus.domain.po.UserInfo;
import com.study.mybatis_plus.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "用户VO实体")
public class UserVO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("详细信息")
//    private String info;
    private UserInfo info;

    @ApiModelProperty("使用状态（1正常 2冻结）")
//    private Integer status;
    private UserStatus status;

    @ApiModelProperty("账户余额")
    private Integer balance;

    @ApiModelProperty("收货地址列表")
    private List<AddressVO> addresses;
}
