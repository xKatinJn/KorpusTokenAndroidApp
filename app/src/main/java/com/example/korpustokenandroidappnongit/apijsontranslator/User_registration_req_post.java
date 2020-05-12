package com.example.korpustokenandroidappnongit.apijsontranslator;

import java.util.List;

public class User_registration_req_post {
    public String email;
    public String login;
    public String password;
    public String tg_nickname;
    public String courses;
    public String birthday;
    public String sex;
    public String education;
    public String work_exp;
    public String name;
    public String surname;
    public List<String> team;

    public User_registration_req_post(String email, String login, String password, String tg_nickname,
                                      String courses, String birthday, String sex, String education,
                                      String work_exp, String name, String surname, List<String> team) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.tg_nickname = tg_nickname;
        this.courses = courses;
        this.birthday = birthday;
        this.sex = sex;
        this.education = education;
        this.work_exp = work_exp;
        this.name = name;
        this.surname = surname;
        this.team = team;
    }
}
