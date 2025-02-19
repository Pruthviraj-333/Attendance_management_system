package com.example.sampleproject;

public class POJOGetAllClassroomDetails {
     String id,class_name,section,room,subject;

     public POJOGetAllClassroomDetails(String id,String class_name,String section,String room,String subject)
     {
         this.id=id;
         this.class_name=class_name;
         this.section=section;
         this.room=room;
         this.subject=subject;
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getclass_name() {
        return class_name;
    }

    public void setclass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getsection() {
        return section;
    }

    public void setsection(String section) {
        this.section = section;
    }

    public String getroom() {
        return room;
    }

    public void setroom(String room) {
        this.room = room;
    }

    public String getsubject() {
        return subject;
    }

    public void setsubject(String subject) {
        this.subject = subject;
    }


}
