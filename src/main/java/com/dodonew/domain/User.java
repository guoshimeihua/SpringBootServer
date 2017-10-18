package com.dodonew.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bruce on 2017/9/6.
 */
public class User implements Serializable {
    private static final long serialVersionUID = -3897211053698389919L;
    private Integer id;
    private String loginName;
    private String password;
    private Integer status;
    private String userName;
    private Date createDate;

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getStatus() {
        return status;
    }

    public String getUserName() {
        return userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", userName='" + userName + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
