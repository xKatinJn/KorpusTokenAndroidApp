package com.example.korpustokenandroidappnongit.apijsontranslator;

public class User {
    public String email;
    public String login;
    public String tg_nickname;
    public String courses;
    public String birthday;
    public String education;
    public String work_exp;
    public String sex;
    public String name;
    public String surname;

    public User (String email, String login, String tg_nickname, String courses, String birthday,
                 String education, String work_exp, String sex, String name, String surname){
        this.email = email;
        this.login = login;
        this.tg_nickname = tg_nickname;
        this.courses = courses;
        this.birthday = birthday;
        this.education = education;
        this.work_exp = work_exp;
        this.sex = sex;
        this.name = name;
        this.surname = surname;
    }
}