package com.dodonew.domain;

import java.io.Serializable;

/**
 * Created by Bruce on 2017/9/6.
 */
public class Dept implements Serializable {
    private static final long serialVersionUID = -4243387151355500160L;
    private Integer id;
    private String departName;
    private String remark;

    public Dept(String departName, String remark) {
        this.departName = departName;
        this.remark = remark;
    }

    public Dept() {

    }

    public Integer getId() {
        return id;
    }

    public String getDepartName() {
        return departName;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", departName='" + departName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
