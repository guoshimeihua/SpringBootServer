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
    void deleteById(Integer id);

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
     */
    @InsertProvider(type = DeptDynaSqlProvider.class, method = "insertDept")
    void save(Dept dept);

    /**
     *
     * @param dept
     */
    @UpdateProvider(type = DeptDynaSqlProvider.class, method = "updateDept")
    void update(Dept dept);
}
