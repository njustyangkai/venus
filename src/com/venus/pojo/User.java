package com.venus.pojo;

import java.sql.Timestamp;

public class User
{
  private String id;
  private String username;
  private String password;
  private Timestamp createTime;
  private Timestamp lastLogTime;
  private Integer state;

  public Integer getState()
  {
    return this.state;
  }
  public void setState(Integer state) {
    this.state = state;
  }
  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getUsername() {
    return this.username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return this.password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public Timestamp getCreateTime() {
    return this.createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  public Timestamp getLastLogTime() {
    return this.lastLogTime;
  }
  public void setLastLogTime(Timestamp lastLogTime) {
    this.lastLogTime = lastLogTime;
  }
}