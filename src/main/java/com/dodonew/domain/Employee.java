package com.dodonew.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bruce on 2017/9/17.
 */
public class Employee implements Serializable {
    /**
     * 创建的域对象一定要和表中的列有对应的关系，否则的话，就会报无效绑定的错误。
     */
    private static final long serialVersionUID = 1027938542596341926L;
    private Integer id;
    private String name;
    private String cardId;
    private String address;
    private String postCode;
    private String tel;
    private String phone;
    private String qqNum;
    private String email;
    private Integer sex;
    private String papty;
    private String race;
    private String education;
    private String speciality;
    private String hobby;
    private String remark;
    private Date createdate;
    private Dept dept;
    private Job job;
    /**
     * 使用@ModelAttribute接收参数时
     * form表单中有日期，Spring不知道该如何转换，
     * 要在实体类的日期属性上加@DateTimeFormat(pattern="yyyy-MM-dd")注解
     *
     * 这只是说在存储的时候，@DateTimeFormat注解才会起作用。从mysql数据库中读取数据的时候，
     * @DateTimeFormat注解是不会起作用的。
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getTel() {
        return tel;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Integer getSex() {
        return sex;
    }

    public String getPapty() {
        return papty;
    }

    public String getRace() {
        return race;
    }

    public String getEducation() {
        return education;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getHobby() {
        return hobby;
    }

    public String getRemark() {
        return remark;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setPapty(String papty) {
        this.papty = papty;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Dept getDept() {
        return dept;
    }

    public Job getJob() {
        return job;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cardId='" + cardId + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", tel='" + tel + '\'' +
                ", phone='" + phone + '\'' +
                ", qqNum='" + qqNum + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", papty='" + papty + '\'' +
                ", race='" + race + '\'' +
                ", education='" + education + '\'' +
                ", speciality='" + speciality + '\'' +
                ", hobby='" + hobby + '\'' +
                ", remark='" + remark + '\'' +
                ", createdate=" + createdate +
                ", dept=" + dept +
                ", job=" + job +
                ", birthday=" + birthday +
                '}';
    }
}
