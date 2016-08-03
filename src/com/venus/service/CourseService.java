package com.venus.service;

import com.venus.dao.CourseDao;
import com.venus.pojo.CourseEvent;
import com.venus.pojo.CourseEvents;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CourseService
{

	@Resource
	private CourseDao courseDao;

	/**
	 * save events
	 * 
	 * @param events
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public String saveEvents(CourseEvents events, Timestamp start, Timestamp end) throws Exception
	{

		return String.valueOf(this.courseDao.saveEvents(events, start, end));
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

		return this.courseDao.getEvents(start, end);
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

		return this.courseDao.getMyEvents(start, end, studentId);
	}

	/**
	 * copy events
	 * 
	 * @param start
	 * @param end
	 * @param startLast
	 * @param endLast
	 * @return
	 * @throws Exception
	 */
	public CourseEvents copyEvents(Timestamp start, Timestamp end, Timestamp startLast,
			Timestamp endLast) throws Exception
	{

		boolean delResult = this.courseDao.deleteEvents(start, end);

		if (delResult)
		{
			CourseEvents courseEvents = this.courseDao.getEvents(startLast, endLast);
			CourseEvents newCourseEvents = new CourseEvents();
			List<CourseEvent> newList = new ArrayList<CourseEvent>();

			for (CourseEvent e : courseEvents.getEvents())
			{
				CourseEvent newCourseEvent = new CourseEvent();

				newCourseEvent.setEventId(UUID.randomUUID().toString());
				newCourseEvent.setStartTime(new Timestamp(e.getStartTime().getTime() + 604800000L));
				newCourseEvent.setEndTime(new Timestamp(e.getEndTime().getTime() + 604800000L));
				newCourseEvent.setStudentId(e.getStudentId());
				newCourseEvent.setStudentName(e.getStudentName());
				newCourseEvent.setTeacherId(e.getTeacherId());
				newCourseEvent.setTeacherName(e.getTeacherName());
				newCourseEvent.setRemark(e.getRemark());
				newCourseEvent.setStatus(e.getStatus());
				newCourseEvent.setColor(e.getColor());

				newList.add(newCourseEvent);
			}

			newCourseEvents.setEvents(newList);

			this.courseDao.saveCopyEvents(newCourseEvents);

			return newCourseEvents;
		}

		return null;
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

		return courseDao.saveEvent(e);
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

		return courseDao.removeEvent(id);
	}
}