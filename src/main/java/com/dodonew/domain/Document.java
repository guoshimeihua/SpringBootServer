package com.dodonew.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bruce on 2017/9/6.
 */
public class Document implements Serializable {
    private static final long serialVersionUID = 5265483385725673266L;
    private Integer id;
    private String title;
    private MultipartFile file; // 文件
    private String fileName;
    private String remark;
    private Date createDate;
    private User user;

    public Document() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRemark() {
        return remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", file=" + file +
                ", fileName='" + fileName + '\'' +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                ", user=" + user +
                '}';
    }
}
