package com.venus.pojo;

import java.sql.Timestamp;

public class Message
{
  private String id;
  private String userId;
  private String userName;
  private String email;
  private String content;
  private String parentId;
  private Timestamp time;
  private String replyId;
  private String replyUserId;
  private String replyUserName;
  private String replyContent;
  private Timestamp replyTime;

  public String getreplyId()
  {
    return this.replyId;
  }

  public void setreplyId(String replyId)
  {
    this.replyId = replyId;
  }

  public String getreplyUserId()
  {
    return this.replyUserId;
  }

  public void setreplyUserId(String replyUserId)
  {
    this.replyUserId = replyUserId;
  }

  public String getreplyUserName()
  {
    return this.replyUserName;
  }

  public void setreplyUserName(String replyUserName)
  {
    this.replyUserName = replyUserName;
  }

  public String getreplyContent()
  {
    return this.replyContent;
  }

  public void setreplyContent(String replyContent)
  {
    this.replyContent = replyContent;
  }

  public Timestamp getreplyTime()
  {
    return this.replyTime;
  }

  public void setreplyTime(Timestamp replyTime)
  {
    this.replyTime = replyTime;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String getUserName()
  {
    return this.userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getContent()
  {
    return this.content;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  public String getParentId()
  {
    return this.parentId;
  }

  public void setParentId(String parentId)
  {
    this.parentId = parentId;
  }

  public Timestamp getTime()
  {
    return this.time;
  }

  public void setTime(Timestamp time)
  {
    this.time = time;
  }
}