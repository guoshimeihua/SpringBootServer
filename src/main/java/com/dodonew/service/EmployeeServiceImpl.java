package com.dodonew.service;

import com.dodonew.dao.EmployeeDao;
import com.dodonew.domain.Employee;
import com.dodonew.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/11/1.
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<Employee> getEmployeeList(Integer pageIndex, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        if (pageSize <= 0) {
            pageSize = 10;
        }
        pageModel.setPageSize(pageSize);
        Integer count = employeeDao.count(params);
        pageModel.setRecordCount(count);
        params.put("pageModel", pageModel);
        return employeeDao.selectByPage(params);
    }

    @Override
    public Employee getEmployee(Integer id) {
        return employeeDao.selectById(id);
    }

    @Override
    public boolean addEmployee(Employee employee) {
        boolean isSuccess = false;
        try {
            int row = employeeDao.save(employee);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean removeEmployee(Integer id) {
        boolean isSuccess = false;
        try {
            int row = employeeDao.deleteById(id);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean modifyEmployee(Employee employee) {
        boolean isSuccess = false;
        try {
            int row = employeeDao.update(employee);
            if (row > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }
}
