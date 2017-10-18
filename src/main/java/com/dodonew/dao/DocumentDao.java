package com.dodonew.dao;

import com.dodonew.domain.Document;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/10/11.
 */
public interface DocumentDao {
    @SelectProvider(type = DocumentDynaSqlProvider.class, method = "selectWithParams")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "filename", property = "fileName"),
            @Result(column = "remark", property = "remark"),
            @Result(column = "createdate", property = "createDate"),
            @Result(column = "user_id", property = "user", one = @One(select = "com.dodonew.hrm.dao.UserDao.selectById", fetchType = FetchType.EAGER))
    })
    List<Document> selectByPage(Map<String, Object> params);

    @Select("select * from " + HrmConstants.DOCUMENTTABLE + " where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "filename", property = "fileName"),
            @Result(column = "remark", property = "remark"),
            @Result(column = "createdate", property = "createDate"),
            @Result(column = "user_id", property = "user", one = @One(select = "com.dodonew.hrm.dao.UserDao.selectById", fetchType = FetchType.EAGER))
    })
    Document selectById(Integer id);

    @SelectProvider(type = DocumentDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    @InsertProvider(type = DocumentDynaSqlProvider.class, method = "insertDocument")
    void save(Document document);

    @UpdateProvider(type = DocumentDynaSqlProvider.class, method = "updateDocument")
    void update(Document document);

    @Delete("delete from "+ HrmConstants.DOCUMENTTABLE + " where id = #{id}")
    void deleteById(Integer id);
}
