package com.example.lab5_milestone1;

public class Note {
    private String date;
    private String username;
    private String title;
    private String content;
    private String src;

    public Note(String date, String username, String title, String content, String src){
        this.date = date;
        this.username = username;
        this.title = title;
        this.content = content;
        this.src = src;
    }

    public String getDate(){return date;}

    public String getUsername(){return username;}

    public String getTitle(){return title;}

    public String getContent(){return content;}

    public String getSrc(){return src;}
}
