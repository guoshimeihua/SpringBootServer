package com.dodonew.dao;

import com.dodonew.domain.Notice;
import com.dodonew.util.common.HrmConstants;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 2017/9/22.
 */
public interface NoticeDao {
    @SelectProvider(type = NoticeDynaSqlProvider.class, method = "selectWithParams")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "createdate", property = "createDate"),
            @Result(column = "user_id", property = "user", one = @One(select = "com.dodonew.dao.UserDao.selectById", fetchType = FetchType.EAGER))
    })
    List<Notice> selectByPage(Map<String, Object> params);

    @SelectProvider(type = NoticeDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    @Select("select * from " + HrmConstants.NOTICETABLE + " where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "createdate", property = "createDate"),
            @Result(column = "user_id", property = "user", one = @One(select = "com.dodonew.dao.UserDao.selectById", fetchType = FetchType.EAGER))
    })
    Notice selectNoticeById(Integer id);

    @Delete("delete from " + HrmConstants.NOTICETABLE + " where id = #{id}")
    Integer deleteById(Integer id);

    @InsertProvider(type = NoticeDynaSqlProvider.class, method = "insertNotice")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() AS id", keyProperty = "id", keyColumn = "id", before = false, resultType = Integer.class)
    Integer save(Notice notice);

    @UpdateProvider(type = NoticeDynaSqlProvider.class, method = "updateNotice")
    Integer update(Notice notice);
}
