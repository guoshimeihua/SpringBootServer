package com.dodonew.service;

import com.dodonew.dao.UserDao;
import com.dodonew.domain.User;
import com.dodonew.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/11/1.
 */
@Service("userSevice")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    
    @Override
    public List<User> getUserList(Integer pageIndex, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        if (pageSize <= 0) {
            pageSize = 10;
        }
        pageModel.setPageSize(pageSize);
        Integer count = userDao.count(params);
        pageModel.setRecordCount(count);
        params.put("pageModel", pageModel);
        return userDao.selectByPage(params);
    }

    @Override
    public User getUser(Integer id) {
        return userDao.selectById(id);
    }

    @Override
    public boolean addUser(User user) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = userDao.save(user);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean removeUser(Integer id) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = userDao.deleteById(id);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean modifyUser(User user) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = userDao.update(user);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }
}
