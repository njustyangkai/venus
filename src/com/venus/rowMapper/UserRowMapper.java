package com.venus.rowMapper;

import com.venus.pojo.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper
  implements RowMapper<User>
{
  public User mapRow(ResultSet rs, int arg0)
    throws SQLException
  {
    User u = new User();
    u.setId(rs.getString("id"));
    u.setUsername(rs.getString("username"));
    u.setPassword(rs.getString("password"));
    u.setCreateTime(rs.getTimestamp("createtime"));
    u.setLastLogTime(rs.getTimestamp("lastlogtime"));
    u.setState(rs.getObject("state") == null ? null : Integer.valueOf(rs.getInt("state")));
    return u;
  }
}