package com.dodonew.service;

import com.dodonew.domain.Notice;

import java.util.List;

/**
 * Created by Bruce on 2017/11/2.
 */
public interface NoticeService {
    public List<Notice> getNoticeList(Integer pageIndex, Integer pageSize);

    public Notice getNotice(Integer id);

    public boolean addNotice(Notice notice);

    public boolean removeNotice(Integer id);

    public boolean modifyNotice(Notice notice);
}
