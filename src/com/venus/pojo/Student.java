package com.venus.pojo;

import java.util.Date;

public class Student
{
  private String id;
  private String name;
  private String email;
  private String grade;
  private Date birthday;
  private String phone;
  private String parentPhone;
  private String sex;

  public String getSex()
  {
    return this.sex;
  }
  public void setSex(String sex) {
    this.sex = sex;
  }
  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getGrade() {
    return this.grade;
  }
  public void setGrade(String grade) {
    this.grade = grade;
  }
  public Date getBirthday() {
    return this.birthday;
  }
  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }
  public String getPhone() {
    return this.phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getParentPhone() {
    return this.parentPhone;
  }
  public void setParentPhone(String parentPhone) {
    this.parentPhone = parentPhone;
  }
}