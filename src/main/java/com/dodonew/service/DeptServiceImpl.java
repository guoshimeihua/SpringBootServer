package com.dodonew.service;

import com.dodonew.dao.DeptDao;
import com.dodonew.domain.Dept;
import com.dodonew.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/10/17.
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("deptService")
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @Override
    @Transactional(readOnly = true)
    public Dept findDept(Integer id) {
        return deptDao.selectById(id);
    }

    @Override
    public boolean addDept(Dept dept) {
        boolean isSuccess = false;
        try {
            // 这里row永远是返回受影响的行数
            int row = deptDao.save(dept);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean removeDept(Integer id) {
        boolean isSucces = false;
        try {
            Integer row = deptDao.deleteById(id);
            if (row > 0) {
                isSucces = true;
            }
        } catch (Exception e) {
            isSucces = false;
        }
        return isSucces;
    }

    @Override
    public boolean modifyDept(Dept dept) {
        boolean isSuccess = false;
        try {
            int row = deptDao.update(dept);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dept> findDeptList(Integer pageIndex, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        if (pageSize <= 0) {
            pageSize = 10;
        }
        pageModel.setPageSize(pageSize);
        Integer count = deptDao.count(params);
        pageModel.setRecordCount(count);
        params.put("pageModel", pageModel);
        return deptDao.selectByPage(params);
    }
}
