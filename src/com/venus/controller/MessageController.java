package com.venus.controller;

import com.venus.pojo.Message;
import com.venus.service.MessageService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("message")
public class MessageController
{

  @Resource
  private MessageService messageService;

  @RequestMapping("saveMessage")
  @ResponseBody
  public String saveMessage(Message message)
  {
    try
    {
      String id = UUID.randomUUID().toString();
      message.setId(id);
      return this.messageService.saveMessage(message);
    }
    catch (Exception e) {
    }
    return null;
  }

  @RequestMapping("loadMessage")
  @ResponseBody
  public List<Message> loadMessage(String userId, String authType)
  {
    try
    {
      Map<String,String> queryCondition = new HashMap<String,String>();
      queryCondition.put("userId", userId);
      queryCondition.put("authType", authType);
      return this.messageService.getMessage(queryCondition);
    }
    catch (Exception e) {
    }
    return null;
  }
}