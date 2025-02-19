package com.example.sampleproject;

public class POJOGetAllSessionInfo {

    String id,session_number,session_name,session_date;

    public POJOGetAllSessionInfo(String id,String session_number,String session_name,String session_date)
    {
        this.id=id;
        this.session_number=session_number;
        this.session_name=session_name;
        this.session_date=session_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSession_number() {
        return session_number;
    }

    public void setSession_number(String session_number) {
        this.session_number = session_number;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getSession_date() {
        return session_date;
    }

    public void setSession_date(String session_date) {
        this.session_date = session_date;
    }
}
