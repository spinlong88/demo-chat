package com.icore.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class UserModel extends BaseModel {
    @ApiModelProperty(value = "id")
    public Long id;
    @ApiModelProperty(value = "姓名")
    public String username;
    @ApiModelProperty(value = "年龄")
    public Integer age;
    @ApiModelProperty(value = "手机")
    public String mobile;
    @ApiModelProperty(value = "薪水")
    public BigDecimal salary;
    @ApiModelProperty(value = "生日")
    public Date birthday;

}
