package com.dodonew.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bruce on 2017/9/6.
 */
public class Notice implements Serializable {
    private static final long serialVersionUID = -3289649288561627490L;
    private Integer id;
    private String title;
    private String content;
    private Date createDate;
    private User user;

    public Notice() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", user=" + user +
                '}';
    }
}
