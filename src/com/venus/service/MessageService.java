package com.venus.service;

import com.venus.dao.MessageDao;
import com.venus.pojo.Message;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MessageService
{

  @Resource
  private MessageDao messageDao;

  public String saveMessage(Message message)
    throws Exception
  {
    return String.valueOf(this.messageDao.saveMessage(message));
  }

  public List<Message> getMessage(Map<String, String> queryCondition)
    throws Exception
  {
    return this.messageDao.getMessage(queryCondition);
  }
}