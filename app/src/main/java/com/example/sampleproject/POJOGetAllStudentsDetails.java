package com.example.sampleproject;

public class POJOGetAllStudentsDetails {
    String id,student_roll_number,student_name,student_sem_year;

    public POJOGetAllStudentsDetails(String id,String student_roll_number,String student_name,String student_sem_year)
    {
        this.id=id;
        this.student_roll_number=student_roll_number;
        this.student_name=student_name;
        this.student_sem_year=student_sem_year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_roll_number() {
        return student_roll_number;
    }

    public void setStudent_roll_number(String student_roll_number) {
        this.student_roll_number = student_roll_number;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_sem_year() {
        return student_sem_year;
    }

    public void setStudent_sem_year(String student_sem_year) {
        this.student_sem_year = student_sem_year;
    }
}
