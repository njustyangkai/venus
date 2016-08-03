package com.venus.service;

import com.venus.dao.AuthDao;
import com.venus.pojo.StudentInfo;
import com.venus.pojo.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{

	@Resource
	private AuthDao authDao;

	/**
	 * 获取学生信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<StudentInfo> loadStudents() throws Exception
	{

		return this.authDao.getStudentsList();
	}

	/**
	 * 增加学生信息
	 * 
	 * @param studentInfo
	 * @return
	 * @throws Exception
	 */
	public boolean addStudent(StudentInfo studentInfo) throws Exception
	{

		boolean result = (this.authDao.addUser(studentInfo.getUser())) && (this.authDao.addStudent(studentInfo.getStudent())) && (this.authDao.addAuth(studentInfo.getUser()));
		if (!result)
		{
			this.authDao.deleteUser(studentInfo.getUser().getId());
			this.authDao.deleteStudent(studentInfo.getStudent().getId());
			this.authDao.deleteAuth(studentInfo.getUser().getId());
		}
		return result;
	}

	/**
	 * 根据登录名查找
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User queryUserByUsername(String username) throws Exception
	{

		return this.authDao.queryUserByUsername(username);
	}

	/**
	 * 根据姓名查找
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<StudentInfo> queryStudentByName(String name) throws Exception
	{

		return this.authDao.queryStudentByName(name);
	}

	/**
	 * 编辑学生
	 * 
	 * @param studentInfo
	 * @return
	 * @throws Exception
	 */
	public boolean editStudent(StudentInfo studentInfo) throws Exception
	{

		return this.authDao.editStudent(studentInfo.getStudent());
	}

	/**
	 * 删除学生
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean deleteStudent(String ids) throws Exception
	{

		return this.authDao.batchDeleteStudent(ids);
	}

	/**
	 * 更改账号状态
	 * 
	 * @param ids
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public boolean changeUserState(String ids, String state) throws Exception
	{

		return this.authDao.changeUserState(ids, state);
	}

	/**
	 * 获取排课学生
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<StudentInfo> getStudentsListForCourse() throws Exception
	{

		return this.authDao.getStudentsListForCourse();
	}

	/**
	 * 登录
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> login(User user) throws Exception
	{

		Map<String, Object> logInfo = new HashMap<String, Object>();

		User u = authDao.login(user);

		logInfo.put("userInfo", u);

		if (u != null)
		{
			logInfo.put("authType", authDao.getAuthType(u.getId()));
		}

		return logInfo;
	}

	/**
	 * 更改最后登录时间
	 * 
	 * @param userId
	 */
	public void changeLastLogTime(String userId)
	{

		try
		{
			this.authDao.changeLastLogTime(userId);
		} catch (Exception localException)
		{
		}
	}
	
	/**
	 * is password right
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean isPasswordRight(String userId, String password) throws Exception{
		return authDao.isPasswordRight(userId, password);
	}
	
	/**
	 * modify password
	 * 
	 * @param userId
	 * @param password
	 * @throws Exception
	 */
	public void modifyPassword(String userId, String password) throws Exception{
		authDao.modifyPassword(userId, password);
	}
}