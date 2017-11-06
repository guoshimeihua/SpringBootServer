package com.dodonew.service;

import com.dodonew.dao.NoticeDao;
import com.dodonew.domain.Notice;
import com.dodonew.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/11/2.
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Override
    public List<Notice> getNoticeList(Integer pageIndex, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        if (pageSize <= 0) {
            pageSize = 10;
        }
        pageModel.setPageSize(pageSize);
        Integer count = noticeDao.count(params);
        pageModel.setRecordCount(count);
        params.put("pageModel", pageModel);
        return noticeDao.selectByPage(params);
    }

    @Override
    public Notice getNotice(Integer id) {
        return noticeDao.selectNoticeById(id);
    }

    @Override
    public boolean addNotice(Notice notice) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = noticeDao.save(notice);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean removeNotice(Integer id) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = noticeDao.deleteById(id);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean modifyNotice(Notice notice) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = noticeDao.update(notice);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }
}
