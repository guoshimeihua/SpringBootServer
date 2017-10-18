package com.dodonew.dao;

import com.dodonew.domain.Employee;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/9/17.
 */
public interface EmployeeDao {
    @Select("select * from " + HrmConstants.EMPLOYEETABLE + " where id = #{id}")
    @Results({
            @Result(id=true, column = "id", property = "id"),
            @Result(column = "card_id", property = "cardId"),
            @Result(column = "name", property = "name"),
            @Result(column = "address", property = "address"),
            @Result(column = "post_code", property = "postCode"),
            @Result(column = "tel", property = "tel"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "qq_num", property = "qqNum"),
            @Result(column = "email", property = "email"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "papty", property = "papty"),
            @Result(column = "birthday", property = "birthday", javaType = Date.class),
            @Result(column = "race", property = "race"),
            @Result(column = "education", property = "education"),
            @Result(column = "speciality", property = "speciality"),
            @Result(column = "hobby", property = "hobby"),
            @Result(column = "remark", property = "remark"),
            @Result(column = "createdate", property = "createdate", javaType = Date.class),
            @Result(column = "dept_id", property = "dept", one = @One(select = "com.dodonew.hrm.dao.DeptDao.selectById", fetchType = FetchType.EAGER)),
            @Result(column = "job_id", property = "job", one = @One(select = "com.dodonew.hrm.dao.JobDao.selectById", fetchType = FetchType.EAGER))
    })
    Employee selectById(Integer id);

    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "selectWithParams")
    @Results({
            @Result(id=true, column = "id", property = "id"),
            @Result(column = "card_id", property = "cardId"),
            @Result(column = "name", property = "name"),
            @Result(column = "address", property = "address"),
            @Result(column = "post_code", property = "postCode"),
            @Result(column = "tel", property = "tel"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "qq_num", property = "qqNum"),
            @Result(column = "email", property = "email"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "papty", property = "papty"),
            @Result(column = "birthday", property = "birthday", javaType = Date.class),
            @Result(column = "race", property = "race"),
            @Result(column = "education", property = "education"),
            @Result(column = "speciality", property = "speciality"),
            @Result(column = "hobby", property = "hobby"),
            @Result(column = "remark", property = "remark"),
            @Result(column = "createdate", property = "createdate", javaType = Date.class),
            @Result(column = "dept_id", property = "dept", one = @One(select = "com.dodonew.hrm.dao.DeptDao.selectById", fetchType = FetchType.EAGER)),
            @Result(column = "job_id", property = "job", one = @One(select = "com.dodonew.hrm.dao.JobDao.selectById", fetchType = FetchType.EAGER))
    })
    List<Employee> selectByPage(Map<String, Object> params);

    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    @InsertProvider(type = EmployeeDynaSqlProvider.class, method = "insert")
    void save(Employee employee);

    @Delete("delete from " + HrmConstants.EMPLOYEETABLE + " where id = #{id}")
    void deleteById(Integer id);

    @UpdateProvider(type = EmployeeDynaSqlProvider.class, method = "update")
    void update(Employee employee);
}
