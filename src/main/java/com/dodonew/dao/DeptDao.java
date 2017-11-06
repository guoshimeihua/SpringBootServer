package com.dodonew.dao;

import com.dodonew.domain.Dept;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/9/10.
 */
public interface DeptDao {
    /**
     * 查询所有部门
     * @return 所有部门
     */
    @Select("select * from " + HrmConstants.DEPTTABLE + " ")
    List<Dept> selectAllDept();

    /**
     * 根据id查询部门
     * @param id 部门id
     * @return 某个部门
     */
    @Select("select * from " + HrmConstants.DEPTTABLE + " where id = #{id}")
    Dept selectById(Integer id);

    /**
     * 根据id删除部门
     * @param id 部门id
     */
    @Delete("delete from " + HrmConstants.DEPTTABLE + " where id = #{id}")
    Integer deleteById(Integer id);

    /**
     * 查询总数量
     * @param params
     * @return 部门总数量
     */
    @SelectProvider(type = DeptDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    /**
     * 分页动态查询
     * @param params
     * @return 部门列表
     */
    @SelectProvider(type = DeptDynaSqlProvider.class, method = "selectWithParams")
    List<Dept> selectByPage(Map<String, Object> params);

    /**
     * 动态插入部门
     * @param dept
     *
     * @SelectKey 注解的主要作用就是把当前插入对象的主键值，赋值给对应的id属性(id代表对应的主键)
     */
    @InsertProvider(type = DeptDynaSqlProvider.class, method = "insertDept")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() AS id", keyProperty = "id", keyColumn = "id", before = false, resultType = Integer.class)
    Integer save(Dept dept);

    /**
     * 更新某个部门的信息
     * @param dept
     */
    @UpdateProvider(type = DeptDynaSqlProvider.class, method = "updateDept")
    Integer update(Dept dept);
}
