package com.dodonew.service;

import com.dodonew.domain.Employee;

import java.util.List;

/**
 * Created by Bruce on 2017/11/1.
 */
public interface EmployeeService {
    public List<Employee> getEmployeeList(Integer pageIndex, Integer pageSize);

    public Employee getEmployee(Integer id);

    public boolean addEmployee(Employee employee);

    public boolean removeEmployee(Integer id);

    public boolean modifyEmployee(Employee employee);
}
