package com.dodonew.dao;

import com.dodonew.domain.User;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by Bruce on 2017/9/8.
 */
public class UserDynaSqlProvider {
    // 动态更新
    public String updateUser(final User user) {
        return new SQL() {
            {
                UPDATE(HrmConstants.USERTABLE);
                if (user.getUserName() != null) {
                    SET("username = #{userName}");
                }
                if (user.getLoginName() != null) {
                    SET("loginname = #{loginName}");
                }
                if (user.getPassword() != null) {
                    SET("password = #{password}");
                }
                if (user.getStatus() != null) {
                    SET("status = #{status}");
                }
                if (user.getCreateDate() != null) {
                    SET("createdate = #{createDate}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }

    // 分页动态查询
    public String selectWithParams(final Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(HrmConstants.USERTABLE);
                if (params.get("user") != null) {
                    User user = (User) params.get("user");
                    if (user.getUserName() != null && !"".equals(user.getUserName())) {
                        WHERE("username like concat ('%', #{user.userName}, '%')");
                    }
                    if (user.getStatus() != null && !"".equals(user.getStatus())) {
                        WHERE("status like concat ('%', #{user.status}, '%')");
                    }
                }
            }
        }.toString();
        if (params.get("pageModel") != null) {
            sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize} ";
        }
        return sql;
    }

    // 动态查询总数量
    public String count(final Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("count(*)");
                FROM(HrmConstants.USERTABLE);
                if (params.get("user") != null) {
                    User user = (User)params.get("user");
                    if (user.getUserName() != null && !"".equals(user.getUserName())) {
                        WHERE("username like concat ('%', #{user.userName}, '%')");
                    }
                    if (user.getStatus() != null && !"".equals(user.getStatus())) {
                        WHERE("status like concat ('%', #{user.status}, '%')");
                    }
                }
            }
        }.toString();
    }

    // 动态插入
    public String insertUser(final User user) {
        return new SQL() {
            {
                INSERT_INTO(HrmConstants.USERTABLE);
                if (user.getUserName() != null && !"".equals(user.getUserName())) {
                    VALUES("username", "#{userName}");
                }
                if (user.getStatus() != null && !"".equals(user.getStatus())) {
                    VALUES("status", "#{status}");
                }
                if (user.getLoginName() != null && !"".equals(user.getLoginName())) {
                    VALUES("loginname", "#{loginName}");
                }
                if (user.getPassword() != null && !"".equals(user.getPassword())) {
                    VALUES("password", "#{password}");
                }
            }
        }.toString();
    }
}
