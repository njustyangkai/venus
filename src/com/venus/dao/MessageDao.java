package com.venus.dao;

import com.venus.pojo.Message;
import com.venus.rowMapper.MessageRowMapper;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDao
{

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean saveMessage(Message message)
    throws Exception
  {
    String sql = "insert into v_message(id,user_id,user_name,email,content,parent_id,time) values(?,?,?,?,?,?,now())";
    return this.jdbcTemplate.update(sql, new Object[] { message.getId(), message.getUserId(), message.getUserName(), 
      message.getEmail(), message.getContent(), message.getParentId() }) > 0;
  }

  public List<Message> getMessage(Map<String, String> queryCondition)
    throws Exception
  {
    if (String.valueOf(0).equals(queryCondition.get("authType")))
    {
      String whereCondition = "and 1=1";
      String sql = "select m1.*,m2.id as reply_id,m2.user_id as reply_user_id,m2.user_name as reply_user_name,m2.content as reply_content,m2.time as reply_time from v_message m1 LEFT JOIN v_message m2 on m2.parent_id = m1.id where m1.parent_id=0 " + 
        whereCondition + " order by m1.time desc";
      return this.jdbcTemplate.query(sql, new MessageRowMapper());
    }

    if (String.valueOf(1).equals(queryCondition.get("authType")))
    {
      String whereCondition = "and m1.user_id = ?";
      String sql = "select m1.*,m2.id as reply_id,m2.user_id as reply_user_id,m2.user_name as reply_user_name,m2.content as reply_content,m2.time as reply_time from v_message m1 LEFT JOIN v_message m2 on m2.parent_id = m1.id where m1.parent_id=0 " + 
        whereCondition + " order by m1.time desc";
      return this.jdbcTemplate.query(sql, new MessageRowMapper(), new Object[] { queryCondition.get("userId") });
    }

    return null;
  }
}