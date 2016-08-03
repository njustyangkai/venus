package com.venus.dao;

import com.venus.pojo.CourseEvent;
import com.venus.pojo.CourseEvents;
import com.venus.rowMapper.CourseListRowMapper;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDao
{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * save events
	 * 
	 * @param events
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public boolean saveEvents(CourseEvents events, Timestamp start, Timestamp end) throws Exception
	{

		String delSql = "delete from v_calendar_event where start_time>? and end_time<?";

		this.jdbcTemplate.update(delSql, new Object[]
		{ start, end });

		List<CourseEvent> courses = events.getEvents();

		if (CollectionUtils.isNotEmpty(courses))
		{
			int length = courses.size();

			String[] sqls = new String[length];

			for (int i = 0; i < length; i++)
			{
				CourseEvent e = courses.get(i);

				sqls[i] = ("insert into v_calendar_event(event_id,start_time,end_time,student_id,student_name,teacher_id,teacher_name,color,status) values('"
						+ e.getEventId()
						+ "','"
						+ e.getStartTime()
						+ "','"
						+ e.getEndTime()
						+ "','"
						+ e.getStudentId()
						+ "','"
						+ e.getStudentName()
						+ "','"
						+ e.getTeacherId()
						+ "','"
						+ e.getTeacherName()
						+ "','"
						+ e.getColor()
						+ "'," + e.getStatus() + ")");
			}

			this.jdbcTemplate.batchUpdate(sqls);
		}

		return true;
	}

	/**
	 * get events
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public CourseEvents getEvents(Timestamp start, Timestamp end) throws Exception
	{

		String sql = "select * from v_event e where e.start_time>? and e.end_time<?";

		List<CourseEvent> list = this.jdbcTemplate.query(sql, new CourseListRowMapper(),
				new Object[]
				{ start, end });

		if (CollectionUtils.isNotEmpty(list))
		{
			CourseEvents events = new CourseEvents();

			events.setEvents(list);

			return events;
		}

		return null;
	}

	/**
	 * get my events
	 * 
	 * @param start
	 * @param end
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public CourseEvents getMyEvents(Timestamp start, Timestamp end, String studentId)
			throws Exception
	{

		String sql = "select * from v_event e where e.start_time>? and e.end_time<? and e.student_id=?";

		List<CourseEvent> list = this.jdbcTemplate.query(sql, new CourseListRowMapper(),
				new Object[]
				{ start, end, studentId });

		if (CollectionUtils.isNotEmpty(list))
		{
			CourseEvents events = new CourseEvents();

			events.setEvents(list);

			return events;
		}

		return null;
	}

	/**
	 * delete events
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public boolean deleteEvents(Timestamp start, Timestamp end) throws Exception
	{

		String delSql = "delete from v_calendar_event where start_time>? and end_time<?";

		this.jdbcTemplate.update(delSql, new Object[]
		{ start, end });

		return true;
	}

	/**
	 * save copy events
	 * 
	 * @param events
	 * @throws Exception
	 */
	public void saveCopyEvents(CourseEvents events) throws Exception
	{

		List<CourseEvent> courses = events.getEvents();

		if (CollectionUtils.isNotEmpty(courses))
		{
			int length = courses.size();

			String[] sqls = new String[length];

			for (int i = 0; i < length; i++)
			{
				CourseEvent e = (CourseEvent) courses.get(i);

				sqls[i] = ("insert into v_calendar_event(event_id,start_time,end_time,student_id,student_name,teacher_id,teacher_name,color,status) values('"
						+ e.getEventId()
						+ "','"
						+ e.getStartTime()
						+ "','"
						+ e.getEndTime()
						+ "','"
						+ e.getStudentId()
						+ "','"
						+ e.getStudentName()
						+ "','"
						+ e.getTeacherId()
						+ "','"
						+ e.getTeacherName()
						+ "','"
						+ e.getColor()
						+ "'," + e.getStatus() + ")");
			}

			this.jdbcTemplate.batchUpdate(sqls);
		}
	}

	/**
	 * save event
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public boolean saveEvent(CourseEvent e) throws Exception
	{

		String sql = "insert into v_event(id,student_id,student_name,teacher_id,teacher_name,color,start_time,end_time) values('"
				+ e.getEventId()
				+ "','"
				+ e.getStudentId()
				+ "','"
				+ e.getStudentName()
				+ "','"
				+ e.getTeacherId()
				+ "','"
				+ e.getTeacherName()
				+ "','"
				+ e.getColor()
				+ "','"
				+ e.getStartTime() + "','" + e.getEndTime() + "')";

		return jdbcTemplate.update(sql) > 0;

	}

	/**
	 * remove event
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean removeEvent(String id) throws Exception
	{

		String sql = "delete from v_event where id=?";
		return jdbcTemplate.update(sql, id) > 0;
	}
}