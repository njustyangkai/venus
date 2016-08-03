package com.venus.pojo;

import java.sql.Timestamp;

public class CourseEvent
{
  private String eventId;
  private Timestamp startTime;
  private Timestamp endTime;
  private String studentId;
  private String studentName;
  private String teacherId;
  private String teacherName;
  private String remark;
  private String color;
  private int status;

  public String getColor()
  {
    return this.color;
  }

  public void setColor(String color)
  {
    this.color = color;
  }

  public String getEventId()
  {
    return this.eventId;
  }

  public void setEventId(String eventId)
  {
    this.eventId = eventId;
  }

  public Timestamp getStartTime()
  {
    return this.startTime;
  }

  public void setStartTime(Timestamp startTime)
  {
    this.startTime = startTime;
  }

  public Timestamp getEndTime()
  {
    return this.endTime;
  }

  public void setEndTime(Timestamp endTime)
  {
    this.endTime = endTime;
  }

  public String getStudentId()
  {
    return this.studentId;
  }

  public void setStudentId(String studentId)
  {
    this.studentId = studentId;
  }

  public String getStudentName()
  {
    return this.studentName;
  }

  public void setStudentName(String studentName)
  {
    this.studentName = studentName;
  }

  public String getTeacherId()
  {
    return this.teacherId;
  }

  public void setTeacherId(String teacherId)
  {
    this.teacherId = teacherId;
  }

  public String getTeacherName()
  {
    return this.teacherName;
  }

  public void setTeacherName(String teacherName)
  {
    this.teacherName = teacherName;
  }

  public String getRemark()
  {
    return this.remark;
  }

  public void setRemark(String remark)
  {
    this.remark = remark;
  }

  public int getStatus()
  {
    return this.status;
  }

  public void setStatus(int status)
  {
    this.status = status;
  }
}