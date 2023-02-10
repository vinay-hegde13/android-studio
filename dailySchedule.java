package com.shashank.platform.classroomappui;

import java.sql.Time;
import java.util.Date;

public class dailySchedule {
    private String courseName;
    private String classN0;
    private String date;
    private String from_to;
    private String professor_name;

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setClassN0(String classN0) {
        this.classN0 = classN0;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFrom_to(String from_to) {
        this.from_to = from_to;
    }

    public void setProfessor_name(String professor_name) {
        this.professor_name = professor_name;
    }

    public dailySchedule(String courseName, String classN0, String  date, String from_to, String professor_name) {
        this.courseName = courseName;
        this.classN0 = classN0;
        this.date = date;
       this.from_to = from_to;
       this.professor_name = professor_name;
    }
    public dailySchedule(){

    }

    public String getDate() {
        return date;
    }

    public String getClassN0() {
        return classN0;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getFrom_to() {
        return from_to;
    }
    public String getProfessor_name(){
        return professor_name;
    }


}
