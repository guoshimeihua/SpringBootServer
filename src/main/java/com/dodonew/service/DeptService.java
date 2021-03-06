package com.dodonew.service;

import com.dodonew.domain.Dept;

import java.util.List;

/**
 * Created by Bruce on 2017/10/17.
 */
public interface DeptService {
    public List<Dept> findDeptList(Integer pageIndex, Integer pageSize);

    public Dept findDept(Integer id);

    public boolean addDept(Dept dept);

    public boolean removeDept(Integer id);

    public boolean modifyDept(Dept dept);
}
