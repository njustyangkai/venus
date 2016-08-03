package com.venus.rowMapper;

import com.venus.pojo.CourseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CourseListRowMapper implements RowMapper<CourseEvent>
{
	public CourseEvent mapRow(ResultSet rs, int arg0) throws SQLException
	{

		CourseEvent e = new CourseEvent();

		e.setEventId(rs.getString("id"));
		e.setStartTime(rs.getTimestamp("start_time"));
		e.setEndTime(rs.getTimestamp("end_time"));
		e.setStudentId(rs.getString("student_id"));
		e.setStudentName(rs.getString("student_name"));
		e.setTeacherId(rs.getString("teacher_id"));
		e.setTeacherName(rs.getString("teacher_name"));
		e.setColor(rs.getString("color"));
		
		return e;
	}
}