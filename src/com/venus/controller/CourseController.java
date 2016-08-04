package com.venus.controller;

import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.venus.pojo.CourseEvent;
import com.venus.pojo.CourseEventWrapper;
import com.venus.pojo.CourseEvents;
import com.venus.service.CourseService;
import com.venus.support.Result;

@Controller
@RequestMapping("course")
public class CourseController
{

	@Resource
	private CourseService courseService;

	private Result result;

	/**
	 * 批量保存
	 * 
	 * @param events
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping("saveEvents")
	@ResponseBody
	public String saveEvents(CourseEvents events, Timestamp start, Timestamp end)
	{

		try
		{
			return this.courseService.saveEvents(events, start, end);
		} catch (Exception e)
		{
		}
		return null;
	}

	/**
	 * 获得
	 * 
	 * @param start
	 * @param end
	 * @param studentId
	 * @return
	 */
	@RequestMapping(value = "getEvents", method = RequestMethod.GET)
	@ResponseBody
	public Object getEvents(Timestamp start, Timestamp end)
	{

		try
		{
			result = Result.getSuccess("",
					JSONObject.fromObject(courseService.getEvents(start, end)));
		} catch (Exception e)
		{
			result = Result.getFailure("系统异常！", null);
		}
		return result;
	}

	@RequestMapping(value = "getMyEvents", method = RequestMethod.GET)
	@ResponseBody
	public Object getMyEvents(Timestamp start, Timestamp end, HttpSession session)
	{

		try
		{
			String userId = (String) session.getAttribute("userId");
			String authType = (String) session.getAttribute("authType");

			if ("teacher".equals(authType))
			{
				result = Result.getSuccess("",
						JSONObject.fromObject(courseService.getEvents(start, end)));
			} else
			{
				result = Result.getSuccess("",
						JSONObject.fromObject(courseService.getMyEvents(start, end, userId)));
			}
		} catch (Exception e)
		{
			result = Result.getFailure("系统异常！", null);
		}
		return result;
	}

	/**
	 * 复制课程
	 * 
	 * @param start
	 * @param end
	 * @param startLast
	 * @param endLast
	 * @return
	 */
	@RequestMapping(value = "copyEvents", method = RequestMethod.POST)
	@ResponseBody
	public Object copyEvents(Timestamp start, Timestamp end, Timestamp startLast, Timestamp endLast)
	{

		try
		{
			if (courseService.copyEvents(start, end, startLast, endLast))
			{

				result = Result.getSuccess("", null);
			} else
			{
				result = Result.getFailure("", null);
			}

		} catch (Exception e)
		{
			result = Result.getFailure("系统异常", null);
		}
		return result;
	}

	/**
	 * 保存单个
	 * 
	 * @param event
	 * @return
	 */
	@RequestMapping(value = "saveEvent", method = RequestMethod.POST)
	@ResponseBody
	public Object saveEvent(CourseEventWrapper event, HttpSession session)
	{

		try
		{
			CourseEvent e = event.getEvent();
			e.setEventId(UUID.randomUUID().toString());
			e.setTeacherId(session.getAttribute("userId").toString());
			e.setTeacherName(session.getAttribute("userName").toString());
			e.setStatus(0);

			if (courseService.saveEvent(e))
			{
				result = Result.getSuccess("", null);
			} else
			{
				result = Result.getFailure("保存失败！", null);
			}

		} catch (Exception e)
		{
			result = Result.getFailure("系统异常！", null);
		}
		return result;
	}

	/**
	 * remove event
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "removeEvent", method = RequestMethod.POST)
	@ResponseBody
	public Object removeEvent(String id)
	{

		try
		{
			if (courseService.removeEvent(id))
			{
				result = Result.getSuccess("", null);
			} else
			{
				result = Result.getFailure("删除失败！", null);
			}

		} catch (Exception e)
		{
			result = Result.getFailure("系统异常！", null);
		}

		return result;

	}
}