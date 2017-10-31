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

/**
 * Created by Bruce on 2017/10/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTests {
    /**
     * 测试下事务的回滚
     */
    @Autowired
    private DeptService deptService;

    @Test
    public void testAddDeptTransactionIsWork() {
        Dept dept = new Dept();
        dept.setDepartName("手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部手机事业部");
        dept.setRemark("手机事业部负责全球的手机业务");
        deptService.addDept(dept);
        System.out.println("增加部门测试成功");
    }

    @Test
    @Transactional
    public void testAddDeptTransaction() {
        // 增加部门的数据，不会插入到数据库中去，因为加入@Transactional会启用回滚机制的
        deptService.addDept(new Dept("手机事业部1", "手机事业部1"));
        deptService.addDept(new Dept("手机事业部2", "手机事业部2"));
        Assert.assertEquals(11, deptService.findDeptList(1, 20).size());
    }

    /**
     * 上面两个方法的区别：第一个方法作用是验证在插入失败的情况下，service方法本身的事务会不会起作用。
     * 第二个方法作用就是在测试增、删、改的时候，并不会对真正的数据库数据产生影响，因为
     * @Transaction注解，会自动插入完成后，自动对插入的数据进行回滚。
     */
}
