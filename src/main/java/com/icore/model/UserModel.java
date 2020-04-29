package com.icore.model;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserModel {
    public Long id;
    public String username;
    public int age;
    public String mobile;
    public BigDecimal salary;
    public Date birthday;


}
