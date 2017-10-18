package com.dodonew.dao;

import com.dodonew.domain.User;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/9/8.
 */
public interface UserDao {
    // 根据登录名和密码查询用户
    /**
     * 查询SQL语句的时候，之间一定要留足空格，否则的话，SQL语句是不正确的。
     * 在写SQL语句的时候，这个是重点。
     */
    @Select("select * from "+ HrmConstants.USERTABLE + " where loginname = #{loginname} and password = #{password}")
    User selectByLoginNameAndPassword(@Param("loginname") String loginName, @Param("password") String password);

    // 根据id查询用户
    @Select("select * from " + HrmConstants.USERTABLE + " where id = #{id}")
    User selectById(Integer id);

    // 根据id删除用户
    /**
     * 在这里插入一条数据的话，不需要返回是否插入成功的状态，比如返回Integer。
     * 是否插入成功，可以放在Service里面来进行判断，到时候捕获一个异常即可。
     * @param id
     */
    @Delete("delete from " + HrmConstants.USERTABLE + " where id = #{id}")
    void deleteById(Integer id);

    // 动态修改用户
    @UpdateProvider(type = UserDynaSqlProvider.class, method = "updateUser")
    void update(User user);

    // 分页动态查询
    @SelectProvider(type = UserDynaSqlProvider.class, method = "selectWithParams")
    List<User> selectByPage(Map<String, Object> params);

    // 根据参数查询用户总数
    @SelectProvider(type = UserDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    // 动态插入用户
    @InsertProvider(type = UserDynaSqlProvider.class, method = "insertUser")
    void save(User user);
}
