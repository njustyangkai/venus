package com.venus.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.venus.pojo.Student;
import com.venus.pojo.StudentInfo;
import com.venus.pojo.User;
import com.venus.rowMapper.StudentListRowMapper;
import com.venus.rowMapper.UserRowMapper;
import com.venus.support.Constants;

@Repository
public class AuthDao
{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * get students list
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<StudentInfo> getStudentsList() throws Exception
	{

		String sql = "select * from v_user u,v_student s where u.id=s.id order by u.createtime";
		return this.jdbcTemplate.query(sql, new StudentListRowMapper());
	}

	/**
	 * add student
	 * 
	 * @param student
	 * @return
	 */
	public boolean addStudent(Student student)
	{

		try
		{
			String sql = "insert into v_student(id,name,email,grade,birthday,phone,parent_phone,sex) values(?,?,?,?,?,?,?,?)";
			return this.jdbcTemplate.update(sql,
					new Object[]
					{ student.getId(), student.getName(), student.getEmail(), student.getGrade(),
							student.getBirthday(), student.getPhone(), student.getParentPhone(),
							student.getSex() }) > 0;
		} catch (Exception e)
		{
		}
		return false;
	}

	/**
	 * add user
	 * 
	 * @param user
	 * @return
	 */
	public boolean addUser(User user)
	{

		try
		{
			String sql = "insert into v_user(id,username,password,createtime,lastlogtime,state) values(?,?,?,now(),now(),?)";
			return this.jdbcTemplate.update(sql, new Object[]
			{ user.getId(), user.getUsername(), user.getPassword(), user.getState() }) > 0;
		} catch (Exception e)
		{
		}
		return false;
	}

	/**
	 * add auth
	 * 
	 * @param user
	 * @return
	 */
	public boolean addAuth(User user)
	{

		try
		{
			String sql = "insert into v_user_auth(user_id, auth_type) values(?, ?)";
			return this.jdbcTemplate.update(sql, user.getId(), Constants.AUTH_TYPE_STUDENT) > 0;
		} catch (Exception e)
		{
		}

		return false;
	}

	/**
	 * delete user
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteUser(String id) throws Exception
	{

		String sql = "delete from v_user where id=?";
		this.jdbcTemplate.update(sql, new Object[]
		{ id });
	}

	/**
	 * delete student
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteStudent(String id) throws Exception
	{

		String sql = "delete from v_student where id=?";
		this.jdbcTemplate.update(sql, new Object[]
		{ id });
	}

	/**
	 * delete auth
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteAuth(String id) throws Exception
	{

		String sql = "delete from v_user_auth where user_id=?";
		this.jdbcTemplate.update(sql, new Object[]
		{ id });
	}

	/**
	 * query user by username
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User queryUserByUsername(String username) throws Exception
	{

		String sql = "select * from v_user where username=?";
		List<User> list = this.jdbcTemplate.query(sql, new UserRowMapper(), new Object[]
		{ username });
		if (CollectionUtils.isNotEmpty(list))
		{
			return list.get(0);
		}
		return null;
	}

	/**
	 * query student by name
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<StudentInfo> queryStudentByName(String name) throws Exception
	{

		String sql = "select * from v_user u,v_student s where u.id=s.id and s.name like ? order by u.createtime";
		return this.jdbcTemplate.query(sql, new StudentListRowMapper(), "%" + name + "%");
	}

	/**
	 * edit student
	 * 
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public boolean editStudent(Student student) throws Exception
	{

		try
		{
			String sql = "update v_student set name=?, email=?, grade=?, birthday=?, phone=?, parent_phone=?,sex=? where id=?";
			return this.jdbcTemplate.update(
					sql,
					new Object[]
					{ student.getName(), student.getEmail(), student.getGrade(),
							student.getBirthday(), student.getPhone(), student.getParentPhone(),
							student.getSex(), student.getId() }) > 0;
		} catch (Exception e)
		{
		}
		return false;
	}

	/**
	 * batch delete student
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean batchDeleteStudent(String ids) throws Exception
	{

		try
		{
			if (StringUtils.isNotEmpty(ids))
			{
				String[] id = ids.split(",");
				int length = id.length;
				String[] userSqls = new String[length];
				String[] studentSqls = new String[length];
				String[] authSqls = new String[length];
				for (int i = 0; i < length; i++)
				{
					String sql1 = "delete from v_user where id='" + id[i] + "'";
					String sql2 = "delete from v_student where id='" + id[i] + "'";
					String sql3 = "delete from v_user_auth where user_id='" + id[i] + "'";
					userSqls[i] = sql1;
					studentSqls[i] = sql2;
					authSqls[i] = sql3;
				}
				this.jdbcTemplate.batchUpdate(userSqls);
				this.jdbcTemplate.batchUpdate(studentSqls);
				this.jdbcTemplate.batchUpdate(authSqls);
			}
			return true;
		} catch (Exception e)
		{
		}
		return false;
	}

	/**
	 * change user state
	 * 
	 * @param ids
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public boolean changeUserState(String ids, String state) throws Exception
	{

		try
		{
			if (StringUtils.isNotEmpty(ids))
			{
				String[] id = ids.split(",");
				int length = id.length;
				String[] userSqls = new String[length];
				for (int i = 0; i < length; i++)
				{
					String sql = "update v_user set state=" + Integer.parseInt(state)
							+ " where id='" + id[i] + "'";
					userSqls[i] = sql;
				}
				this.jdbcTemplate.batchUpdate(userSqls);
			}
			return true;
		} catch (Exception e)
		{
		}
		return false;
	}

	/**
	 * get students list for course
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<StudentInfo> getStudentsListForCourse() throws Exception
	{

		String sql = "select * from v_user u,v_student s where u.id=s.id and u.state=1 order by s.name";
		return this.jdbcTemplate.query(sql, new StudentListRowMapper());
	}

	/**
	 * login
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User login(User user) throws Exception
	{

		String sql = "select * from v_user u where u.username=? and u.password=?";
		List<User> list = this.jdbcTemplate.query(sql, new UserRowMapper(), new Object[]
		{ user.getUsername(), user.getPassword() });
		if (CollectionUtils.isNotEmpty(list))
		{
			return list.get(0);
		}
		return null;
	}

	/**
	 * get auth type
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getAuthType(String userId) throws Exception
	{

		String sql = "select a.auth_type from v_user_auth a where a.user_id=?";
		return String.valueOf(this.jdbcTemplate.queryForObject(sql, Integer.class, new Object[]
		{ userId }));
	}

	/**
	 * change last log time
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void changeLastLogTime(String userId) throws Exception
	{

		String sql = "update v_user set lastlogtime=now() where id=?";
		this.jdbcTemplate.update(sql, new Object[]
		{ userId });
	}

	/**
	 * is password right
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean isPasswordRight(String userId, String password) throws Exception
	{

		String sql = "select * from v_user where id=? and password=?";
		List<User> list = jdbcTemplate.query(sql, new UserRowMapper(), userId, password);
		if (CollectionUtils.isNotEmpty(list))
		{
			return true;
		}
		return false;
	}

	/**
	 * modify password
	 * 
	 * @param userId
	 * @param password
	 * @throws Exception
	 */
	public void modifyPassword(String userId, String password) throws Exception
	{

		String sql = "update v_user set password=? where id=?";
		jdbcTemplate.update(sql, password, userId);
	}
}