package com.dodonew;

import com.dodonew.domain.Dept;
import com.dodonew.service.DeptService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Bruce on 2017/10/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptServiceTests {
    @Autowired
    private DeptService deptService;

    @Test
    public void testDeptList() {
        List<Dept> deptList = deptService.findDeptList(1, 10);
        System.out.println("测试部门列表 : " + deptList);
        Assert.assertTrue(deptList.size() > 0);
    }

    @Test
    public void testAddDept() {
        Dept dept = new Dept("测试部1", "测试部1");
        boolean isSuccess = deptService.addDept(dept);
        System.out.println("增加部门的主键id：" + dept.getId());
        Assert.assertEquals(true, isSuccess);
    }

    @Test
    @Transactional
    public void testAddDept2() {
        Dept dept = new Dept("测试部2", "测试部2");
        boolean isSuccess = deptService.addDept(dept);
        System.out.println("增加部门的主键id: " + dept.getId());
        Assert.assertEquals(true, isSuccess);
    }

    @Test
    public void testUpdateDeptSuccess() {
        boolean isSuccess = deptService.modifyDept(new Dept(10, "行政部", "行政部"));
        Assert.assertEquals(true, isSuccess);
    }

    @Test
    public void testUpdateDeptFailure() {
        boolean isSuccess = deptService.modifyDept(new Dept(100, "行政部", "行政部"));
        Assert.assertEquals(false, isSuccess);
    }

    @Test
    public void testUpdateDeptException() {
        boolean isSuccess = deptService.modifyDept(new Dept(10, "行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部", "行政部"));
        Assert.assertEquals(false, isSuccess);
    }

    @Test
    public void testDeleteDeptFailure() {
        boolean isSuccess = deptService.removeDept(100);
        Assert.assertEquals(false, isSuccess);
    }

    @Test
    public void testDeleteDeptSuccess() {
        boolean isSuccess = deptService.removeDept(25);
        Assert.assertEquals(true, isSuccess);
    }

    @Test
    public void testAddDeptSuccess() {
        Dept dept = new Dept("手机部2", "手机部2");
        boolean isSuccess = deptService.addDept(dept);
        // 是在这里取得插入部门的主键id
        System.out.println("插入部门的deptId : " + dept.getId());
        Assert.assertEquals(true, isSuccess);
    }

    @Test
    public void testAddDeptException() {
        boolean isSuccess = deptService.addDept(new Dept("行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部行政部", "手机部3"));
        Assert.assertEquals(false, isSuccess);
    }
}
