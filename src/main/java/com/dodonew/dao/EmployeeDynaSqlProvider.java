package com.dodonew.dao;

import com.dodonew.domain.Employee;
import com.dodonew.util.common.HrmConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by Bruce on 2017/9/18.
 */
public class EmployeeDynaSqlProvider {
    public String selectWithParams(final Map<String, Object> params) {
        String sql = new SQL(){
            {
                SELECT("*");
                FROM(HrmConstants.EMPLOYEETABLE);
                if (params.get("employee") != null) {
                    Employee employee = (Employee) params.get("employee");
                    if (employee.getDept() != null && employee.getDept().getId() != null && employee.getDept().getId() != 0) {
                        WHERE("dept_id = #{employee.dept.id}");
                    }
                    if (employee.getJob() != null && employee.getJob().getId() != null && employee.getJob().getId() != 0) {
                        WHERE("job_id = #{employee.job.id}");
                    }
                    if (employee.getName() != null && !"".equals(employee.getName())) {
                        WHERE("name like concat ('%', #{employee.name}, '%')");
                    }
                    if (employee.getPhone() != null && !"".equals(employee.getPhone())) {
                        WHERE("phone like concat ('%', #{employee.phone}, '%')");
                    }
                    if (employee.getCardId() != null && !"".equals(employee.getCardId())) {
                        WHERE(("card_id like concat ('%', #{employee.cardId}, '%')"));
                    }
                    if (employee.getSex() != null && employee.getSex() != 0) {
                        WHERE("sex = #{employee.sex}");
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
        return new SQL(){
            {
                SELECT("count(*)");
                FROM(HrmConstants.EMPLOYEETABLE);
                if (params.get("employee") != null) {
                    Employee employee = (Employee) params.get("employee");
                    if (employee.getDept() != null && employee.getDept().getId() != null && employee.getDept().getId() != 0) {
                        WHERE("dept_id = #{employee.dept.id}");
                    }
                    if (employee.getJob() != null && employee.getJob().getId() != null && employee.getJob().getId() != 0) {
                        WHERE("job_id = #{employee.job.id}");
                    }
                    if (employee.getName() != null && !"".equals(employee.getName())) {
                        WHERE("name like concat ('%', #{employee.name}, '%')");
                    }
                    if (employee.getPhone() != null && !"".equals(employee.getPhone())) {
                        WHERE("phone like concat ('%', #{employee.phone}, '%')");
                    }
                    if (employee.getCardId() != null && !"".equals(employee.getCardId())) {
                        WHERE(("card_id like concat ('%', #{employee.cardId}, '%')"));
                    }
                    if (employee.getSex() != null && employee.getSex() != 0) {
                        WHERE("sex = #{employee.sex}");
                    }
                }
            }
        }.toString();
    }

    public String insert(final Employee employee) {
        return new SQL(){
            {
                INSERT_INTO(HrmConstants.EMPLOYEETABLE);
                if (employee.getName() != null) {
                    VALUES("name", "#{name}");
                }
                if (employee.getCardId() != null) {
                    VALUES("card_id", "#{cardId}");
                }
                if (employee.getAddress() != null) {
                    VALUES("address", "#{address}");
                }
                if (employee.getPostCode() != null) {
                    VALUES("post_code", "#{postCode}");
                }
                if (StringUtils.isBlank(employee.getTel())) {
                    System.out.println("是null符串");
                } else {
                    System.out.println("是空字符串");
                }
                if (employee.getTel() != null) {
                    VALUES("tel", "#{tel}");
                }
                if (employee.getPhone() != null) {
                    VALUES("phone", "#{phone}");
                }
                if (employee.getQqNum() != null) {
                    VALUES("qq_num", "#{qqNum}");
                }
                if (employee.getEmail() != null) {
                    VALUES("email", "#{email}");
                }
                if (employee.getSex() != null) {
                    VALUES("sex", "#{sex}");
                }
                if (employee.getPapty() != null) {
                    VALUES("papty", "#{papty}");
                }
                if (employee.getBirthday() != null) {
                    VALUES("birthday", "birthday");
                }
                if (employee.getRace() != null) {
                    VALUES("race", "#{race}");
                }
                if (employee.getEducation() != null) {
                    VALUES("education", "#{education}");
                }
                if (employee.getSpeciality() != null) {
                    VALUES("speciality", "#{speciality}");
                }
                if (employee.getHobby() != null) {
                    VALUES("hobby", "#{hobby}");
                }
                if (employee.getRemark() != null) {
                    VALUES("remark", "#{remark}");
                }
                if (employee.getCreatedate() != null) {
                    VALUES("createdate", "#{createdate}");
                }
                if (employee.getDept() != null) {
                    VALUES("dept_id", "#{dept.id}");
                }
                if (employee.getJob() != null) {
                    VALUES("job_id", "#{job.id}");
                }
            }
        }.toString();
    }

    public String update(final Employee employee) {
        return new SQL(){
            {
                UPDATE(HrmConstants.EMPLOYEETABLE);
                if (employee.getName() != null) {
                    SET("name = #{name}");
                }
                if (employee.getCardId() != null) {
                    SET("card_id = #{cardId}");
                }
                if (employee.getAddress() != null) {
                    SET("address = #{address}");
                }
                if (employee.getPostCode() != null) {
                    SET("post_code = #{postCode}");
                }
                if (employee.getTel() != null) {
                    SET("tel = #{tel}");
                }
                if (employee.getPhone() != null) {
                    SET("phone = #{phone}");
                }
                if (employee.getQqNum() != null) {
                    SET("qq_num = #{qqNum}");
                }
                if (employee.getEmail() != null) {
                    SET("email = #{email}");
                }
                if (employee.getSex() != null) {
                    SET("sex = #{sex}");
                }
                if (employee.getPapty() != null) {
                    SET("papty = #{papty}");
                }
                if (employee.getBirthday() != null) {
                    SET("birthday = #{birthday}");
                }
                if (employee.getRace() != null) {
                    SET("race = #{race}");
                }
                if (employee.getEducation() != null) {
                    SET("education = #{education}");
                }
                if (employee.getSpeciality() != null) {
                    SET("speciality = #{speciality}");
                }
                if (employee.getHobby() != null) {
                    SET("hobby = #{hobby}");
                }
                if (employee.getRemark() != null) {
                    SET("remark = #{remark}");
                }
                if (employee.getCreatedate() != null) {
                    SET("createdate = #{createdate}");
                }
                if (employee.getDept() != null) {
                    SET("dept_id = #{dept.id}");
                }
                if (employee.getJob() != null) {
                    SET("job_id = #{job.id}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
