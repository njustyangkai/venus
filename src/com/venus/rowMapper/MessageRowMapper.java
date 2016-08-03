package com.venus.rowMapper;

import com.venus.pojo.Message;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class MessageRowMapper
  implements RowMapper<Message>
{
  public Message mapRow(ResultSet rs, int arg0)
    throws SQLException
  {
    Message m = new Message();

    m.setId(rs.getString("id"));
    m.setUserId(rs.getString("user_id"));
    m.setUserName(rs.getString("user_name"));
    m.setTime(rs.getTimestamp("time"));
    m.setEmail(rs.getObject("email") == null ? null : rs.getString("email"));
    m.setParentId(rs.getString("parent_id"));
    m.setContent(rs.getString("content"));
    m.setreplyId(rs.getObject("reply_id") == null ? null : rs.getString("reply_id"));
    m.setreplyUserId(rs.getObject("reply_user_id") == null ? null : rs.getString("reply_user_id"));
    m.setreplyUserName(rs.getObject("reply_user_name") == null ? null : rs.getString("reply_user_name"));
    m.setreplyContent(rs.getObject("reply_content") == null ? null : rs.getString("reply_content"));
    m.setreplyTime(rs.getObject("reply_time") == null ? null : rs.getTimestamp("reply_time"));

    return m;
  }
}