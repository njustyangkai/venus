package com.venus.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.venus.pojo.Student;
import com.venus.pojo.StudentInfo;
import com.venus.pojo.User;

public class StudentListRowMapper implements RowMapper<StudentInfo>
{
	public StudentInfo mapRow(ResultSet rs, int arg0) throws SQLException
	{

		StudentInfo t = new StudentInfo();
		User u = new User();
		Student s = new Student();
		u.setId(rs.getString("user.id"));
		u.setUsername(rs.getString("user.username"));
		u.setPassword(rs.getString("user.password"));
		u.setCreateTime(rs.getTimestamp("user.createtime"));
		u.setLastLogTime(rs.getTimestamp("user.lastlogtime"));
		u.setState(rs.getObject("user.state") == null ? null : Integer.valueOf(rs
				.getInt("user.state")));
		t.setUser(u);
		s.setId(rs.getString("student.id"));
		s.setName(rs.getString("student.name"));
		s.setGrade(rs.getString("student.grade"));
		s.setSex(rs.getString("student.sex"));
		s.setEmail(rs.getString("email"));
		s.setBirthday(new java.util.Date(rs.getDate("student.birthday").getTime()));
		s.setPhone(rs.getObject("student.phone") == null ? null : rs.getString("student.phone"));
		s.setParentPhone(rs.getObject("student.parent_phone") == null ? null : rs
				.getString("student.parent_phone"));
		t.setStudent(s);
		return t;
	}
}