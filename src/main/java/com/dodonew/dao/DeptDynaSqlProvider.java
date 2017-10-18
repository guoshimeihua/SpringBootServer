package com.dodonew.dao;

import com.dodonew.domain.Dept;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by Bruce on 2017/9/10.
 */
public class DeptDynaSqlProvider {
    // 分页动态查询
    public String selectWithParams(final Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(HrmConstants.DEPTTABLE);
                if (params.get("dept") != null) {
                    Dept dept = (Dept) params.get("dept");
                    if (dept.getDepartName() != null && !"".equals(dept.getDepartName())) {
                        WHERE(" departname like concat ('%', #{dept.departName}, '%') ");
                    }
                }
            }
        }.toString();

        if (params.get("pageModel") != null) {
            sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
        }

        return sql;
    }

    // 动态查询总数量
    public String count(final Map<String, Object> params) {
        return new SQL(){
            {
                SELECT("count(*)");
                FROM(HrmConstants.DEPTTABLE);
                if (params.get("dept") != null) {
                    Dept dept = (Dept) params.get("dept");
                    if (dept.getDepartName() != null && !"".equals(dept.getDepartName())) {
                        WHERE(" departname like concat ('%', #{dept.departName}, '%') ");
                    }
                }
            }
        }.toString();
    }

    // 动态插入
    public String insertDept(final Dept dept) {
        return new SQL(){
            {
                INSERT_INTO(HrmConstants.DEPTTABLE);
                if (dept.getDepartName() != null && !"".equals(dept.getDepartName())) {
                    VALUES("departname", "#{departName}");
                }
                if (dept.getRemark() != null && !"".equals(dept.getRemark())) {
                    VALUES("remark", "#{remark}");
                }
            }
        }.toString();
    }

    // 动态更新
    public String updateDept(final Dept dept) {
        return new SQL(){
            {
                UPDATE(HrmConstants.DEPTTABLE);
                if (dept.getDepartName() != null) {
                    SET(" departname = #{departName}");
                }
                if (dept.getRemark() != null) {
                    SET(" remark = #{remark}");
                }
                WHERE(" id = #{id}");
            }
        }.toString();
    }
}
