package com.venus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.venus.pojo.StudentInfo;
import com.venus.pojo.User;
import com.venus.service.AuthService;
import com.venus.support.Constants;
import com.venus.support.Result;

@Controller
@RequestMapping("auth")
public class AuthController
{

	@Resource
	private AuthService authService;

	private Result result;

	/**
	 * 获取学生信息
	 * 
	 * @param limit
	 * @param offset
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "loadStudents", method = RequestMethod.GET)
	@ResponseBody
	public Object loadStudents(int limit, int offset, String name)
	{

		try
		{
			List<StudentInfo> students = new ArrayList<StudentInfo>();

			if (StringUtils.isBlank(name))
			{

				students = this.authService.loadStudents();
			} else
			{
				String nameRequest = new String(name.getBytes("iso8859-1"), "utf-8");
				students = this.authService.queryStudentByName(nameRequest);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			int totalCount = students.size();
			List<StudentInfo> studentsPaging = new ArrayList<StudentInfo>();
			for (int i = offset; (i < offset + limit) && (i < totalCount); i++)
			{
				studentsPaging.add(students.get(i));
			}
			map.put("total", Integer.valueOf(totalCount));
			map.put("list", studentsPaging);
			return map;
		} catch (Exception e)
		{
		}
		return null;
	}

	/**
	 * 新增学生
	 * 
	 * @param studentInfo
	 * @return
	 */
	@RequestMapping(value = "addStudent", method = RequestMethod.POST)
	@ResponseBody
	public Object addStudent(StudentInfo studentInfo)
	{

		try
		{
			String id = UUID.randomUUID().toString();
			studentInfo.getUser().setId(id);
			studentInfo.getStudent().setId(id);
			studentInfo.getUser().setState(Integer.valueOf(0));
			if (authService.addStudent(studentInfo))
			{
				result = Result.getSuccess("", "");
			} else
			{
				result = Result.getFailure("", "");
			}

		} catch (Exception e)
		{
			result = Result.getFailure("", "");
		}
		return result;
	}

	/**
	 * 校验用户名是否已经存在
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "isUsernameExist", method = RequestMethod.POST)
	@ResponseBody
	public Object isUsernameExist(String username)
	{

		try
		{
			User u = this.authService.queryUserByUsername(username);
			if (u != null)
			{
				result = Result.getSuccess("", "");
				return result;
			}

		} catch (Exception e)
		{
			result = Result.getFailure("", "");
		}
		result = Result.getFailure("", "");
		return result;
	}

	/**
	 * 编辑学生
	 * 
	 * @param studentInfo
	 * @return
	 */
	@RequestMapping(value = "editStudent", method = RequestMethod.POST)
	@ResponseBody
	public Object editStudent(StudentInfo studentInfo)
	{

		try
		{
			if (authService.editStudent(studentInfo))
			{

				result = Result.getSuccess("", "");
			} else
			{
				result = Result.getFailure("", "");
			}
		} catch (Exception e)
		{
			result = Result.getFailure("", "");
		}
		return result;
	}

	/**
	 * 删除学生
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "deleteStudent", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteStudent(String ids)
	{

		try
		{
			if (authService.deleteStudent(ids))
			{
				result = Result.getSuccess("", "");
			} else
			{
				result = Result.getFailure("", "");
			}
		} catch (Exception e)
		{
			result = Result.getSuccess("", "");
		}
		return result;
	}

	/**
	 * 更改状态
	 * 
	 * @param ids
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "changeUserState", method = RequestMethod.POST)
	@ResponseBody
	public Object changeUserState(String ids, String state)
	{

		try
		{
			if (authService.changeUserState(ids, state))
			{
				result = Result.getSuccess("", "");
			} else
			{
				result = Result.getFailure("", "");
			}
		} catch (Exception e)
		{
			result = Result.getFailure("", "");
		}
		return result;
	}

	/**
	 * 获取排课学生
	 * 
	 * @return
	 */
	@RequestMapping("loadStudentsForCourse")
	@ResponseBody
	public Object loadStudentsForCourse()
	{

		List<StudentInfo> students = new ArrayList<StudentInfo>();
		try
		{
			students = this.authService.getStudentsListForCourse();
			result = Result.getSuccess("", JSONArray.fromObject(students));
		} catch (Exception e)
		{
			result = Result.getFailure(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * login
	 * 
	 * @param user
	 * @param session
	 * @param callback
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	@ResponseBody
	public Object login(User user, HttpSession session, String callback)
	{

		try
		{
			Map<String, Object> u = authService.login(user);

			if (u.get("userInfo") != null)
			{
				User userResponse = (User) u.get("userInfo");

				if (userResponse.getState().intValue() == 1)
				{
					session.setAttribute("userId", userResponse.getId());
					session.setAttribute("authType",
							u.get("authType").equals(Constants.AUTH_TYPE_TEACHER) ? "teacher"
									: "student");
					session.setAttribute("userName", userResponse.getUsername());

					authService.changeLastLogTime(userResponse.getId());

					result = Result.getSuccess("", null);
				}

				if (userResponse.getState().intValue() == 0)
				{
					result = Result.getFailure("账号未激活！", null);
				}

			} else
			{
				result = Result.getFailure("账号或密码错误！", null);
			}
		} catch (Exception e)
		{
			result = Result.getFailure("系统异常！", null);
		}
		return result;
	}

	/**
	 * log out
	 * 
	 * @param session
	 */
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	@ResponseBody
	public Object logout(HttpSession session)
	{

		try
		{
			session.removeAttribute("userId");
			session.removeAttribute("userName");
			session.removeAttribute("authType");

			result = Result.getSuccess("", null);
		} catch (Exception e)
		{
			result = Result.getFailure("系统异常！", null);
		}
		return result;
	}

	/**
	 * is login
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "isLogin", method = RequestMethod.GET)
	@ResponseBody
	public Object isLogin(HttpSession session)
	{

		try
		{
			String userId = (String) session.getAttribute("userId");
			if (StringUtils.isNotBlank(userId))
			{
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("userId", userId);
				userMap.put("userName", session.getAttribute("userName"));
				userMap.put("authType", session.getAttribute("authType"));
				result = Result.getSuccess("", JSONObject.fromObject(userMap));
			} else
			{
				result = Result.getFailure("未登录", null);
			}
		} catch (Exception e)
		{
			result = Result.getFailure("系统异常", null);
		}
		return result;
	}

	/**
	 * is password right
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "isPasswordRight", method = RequestMethod.POST)
	@ResponseBody
	public Object isPasswordRight(HttpSession session, String password)
	{

		try
		{
			String userId = (String) session.getAttribute("userId");

			if (authService.isPasswordRight(userId, password))
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
	 * modify password
	 * 
	 * @param session
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	public Object modifyPassword(HttpSession session, String newPassword)
	{

		try
		{
			String userId = (String) session.getAttribute("userId");
			authService.modifyPassword(userId, newPassword);
			result = Result.getSuccess("", null);
		} catch (Exception e)
		{
			result = Result.getFailure("系统异常", null);
		}
		return result;
	}
}