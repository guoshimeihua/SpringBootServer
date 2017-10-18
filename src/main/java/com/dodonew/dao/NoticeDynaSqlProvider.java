package com.dodonew.dao;

import com.dodonew.domain.Notice;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by Bruce on 2017/9/22.
 */
public class NoticeDynaSqlProvider {
    public String selectWithParams(final Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(HrmConstants.NOTICETABLE);
                if (params.get("notice") != null) {
                    Notice notice = (Notice) params.get("notice");
                    if (notice.getTitle() != null && !"".equals(notice.getTitle())) {
                        WHERE("title like concat ('%', #{notice.title}, '%')");
                    }
                    if (notice.getContent() != null && !"".equals(notice.getContent())) {
                        WHERE("content like concat ('%', #{notice.content}, '%')");
                    }
                }
            }
        }.toString();

        if (params.get("pageModel") != null) {
            sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize} ";
        }

        return sql;
    }

    public String count(final Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("count(*)");
                FROM(HrmConstants.NOTICETABLE);
                if (params.get("notice") != null) {
                    Notice notice = (Notice) params.get("notice");
                    if (notice.getTitle() != null && !"".equals(notice.getTitle())) {
                        WHERE("title like concat ('%', #{notice.title}, '%')");
                    }
                    if (notice.getContent() != null && !"".equals(notice.getContent())) {
                        WHERE("content like concat ('%', #{notice.content}, '%')");
                    }
                }
            }
        }.toString();
    }

    public String insertNotice(final Notice notice) {
        return new SQL(){
            {
                INSERT_INTO(HrmConstants.NOTICETABLE);
                if (notice.getTitle() != null && !"".equals(notice.getTitle())) {
                    VALUES("title", "#{title}");
                }
                if (notice.getContent() != null && !"".equals(notice.getContent())) {
                    VALUES("content", "#{content}");
                }
                if (notice.getUser() != null && notice.getUser().getId() != null) {
                    VALUES("user_id", "#{user.id}");
                }
            }
        }.toString();
    }

    public String updateNotice(final Notice notice) {
        return new SQL(){
            {
                UPDATE(HrmConstants.NOTICETABLE);
                if (notice.getTitle() != null && !"".equals(notice.getTitle())) {
                    SET("title = #{title}");
                }
                if (notice.getContent() != null && !"".equals(notice.getContent())) {
                    SET("content = #{content}");
                }
                if (notice.getUser() != null && notice.getUser().getId() != null) {
                    SET("user_id = #{user.id}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
