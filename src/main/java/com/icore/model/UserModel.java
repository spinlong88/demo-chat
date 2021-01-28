package com.icore.model;

import com.icore.sensitive.Desensitized;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import static com.icore.sensitive.SensitiveTypeEnum.CHINESE_NAME;
import static com.icore.sensitive.SensitiveTypeEnum.MOBILE_PHONE;

@Data
@ApiModel
public class UserModel extends BaseModel implements Serializable {
    @ApiModelProperty(value = "id")
    public Long id;
    @ApiModelProperty(value = "姓名")
    @Desensitized(value=CHINESE_NAME)
    public String username;
    @ApiModelProperty(value = "年龄")
    public Integer age;
    @ApiModelProperty(value = "手机")
    @Desensitized(value=MOBILE_PHONE)
    public String mobile;
    @ApiModelProperty(value = "薪水")
    public BigDecimal salary;
    @ApiModelProperty(value = "生日")
    public Date birthday;

}
