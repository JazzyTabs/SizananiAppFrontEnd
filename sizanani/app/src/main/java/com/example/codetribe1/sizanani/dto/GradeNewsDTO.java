package com.example.codetribe1.sizanani.dto;

import java.io.Serializable;

/**
 * Created by CodeTRibe1 on 2015-03-29.
 */
public class GradeNewsDTO implements Serializable {

    private Integer studentID;
    private String firstName;
    private String middleName;
    private String lastName;
    private int schoolID;
    private int gradeID;

    public GradeNewsDTO() {
    }

    public GradeNewsDTO(Integer studentID, String firstName, String middleName, String lastName, int schoolID, int gradeID) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.schoolID = schoolID;
        this.gradeID = gradeID;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }
}
