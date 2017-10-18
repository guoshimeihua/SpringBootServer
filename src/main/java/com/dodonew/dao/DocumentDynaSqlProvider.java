package com.dodonew.dao;

import com.dodonew.domain.Document;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by Bruce on 2017/10/11.
 */
public class DocumentDynaSqlProvider {
    public String selectWithParams(final Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(HrmConstants.DOCUMENTTABLE);
                if (params.get("document") != null) {
                    Document document = (Document) params.get("document");
                    if (document.getTitle() != null && !"".equals(document.getTitle())) {
                        WHERE("title like concat ('%', #{document.title}, '%')");
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
                FROM(HrmConstants.DOCUMENTTABLE);
                if (params.get("document") != null) {
                    Document document = (Document) params.get("document");
                    if (document.getTitle() != null && !"".equals(document.getTitle())) {
                        WHERE("title like concat ('%', #{document.title}, '%')");
                    }
                }
            }
        }.toString();
    }

    public String insertDocument(final Document document) {
        return new SQL() {
            {
                INSERT_INTO(HrmConstants.DOCUMENTTABLE);
                if (document.getTitle() != null && !"".equals(document.getTitle())) {
                    VALUES("title", "#{title}");
                }
                if (document.getFileName() != null && !"".equals(document.getFileName())) {
                    VALUES("filename", "#{fileName}");
                }
                if (document.getRemark() != null && !"".equals(document.getRemark())) {
                    VALUES("remark", "#{remark}");
                }
                if (document.getUser() != null && document.getUser().getId() != null) {
                    VALUES("user_id", "#{user.id}");
                }
            }
        }.toString();
    }

    public String updateDocument(final Document document) {
        return new SQL(){
            {
                UPDATE(HrmConstants.DOCUMENTTABLE);
                if (document.getTitle() != null && !"".equals(document.getTitle())) {
                    SET("title = #{title}");
                }
                if (document.getFileName() != null && !"".equals(document.getFileName())) {
                    SET("filename = #{fileName}");
                }
                if (document.getRemark() != null && !"".equals(document.getRemark())) {
                    SET("remark = #{remark}");
                }
                if (document.getUser() != null && document.getUser().getId() != null) {
                    SET("user_id = #{user.id}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
