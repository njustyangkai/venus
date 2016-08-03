package com.venus.rowMapper;

import com.venus.pojo.Student;
import com.venus.pojo.StudentInfo;
import com.venus.pojo.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class StudentListRowMapper
  implements RowMapper<StudentInfo>
{
  public StudentInfo mapRow(ResultSet rs, int arg0)
    throws SQLException
  {
    StudentInfo t = new StudentInfo();
    User u = new User();
    Student s = new Student();
    u.setId(rs.getString("u.id"));
    u.setUsername(rs.getString("u.username"));
    u.setPassword(rs.getString("u.password"));
    u.setCreateTime(rs.getTimestamp("u.createtime"));
    u.setLastLogTime(rs.getTimestamp("u.lastlogtime"));
    u.setState(rs.getObject("u.state") == null ? null : Integer.valueOf(rs.getInt("u.state")));
    t.setUser(u);
    s.setId(rs.getString("s.id"));
    s.setName(rs.getString("s.name"));
    s.setGrade(rs.getString("s.grade"));
    s.setSex(rs.getString("s.sex"));
    s.setEmail(rs.getString("email"));
    s.setBirthday(new java.util.Date(rs.getDate("s.birthday").getTime()));
    s.setPhone(rs.getObject("s.phone") == null ? null : rs.getString("s.phone"));
    s.setParentPhone(rs.getObject("s.parent_phone") == null ? null : rs.getString("s.parent_phone"));
    t.setStudent(s);
    return t;
  }
}