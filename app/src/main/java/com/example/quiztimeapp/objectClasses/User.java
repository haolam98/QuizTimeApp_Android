package com.example.quiztimeapp.objectClasses;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private String name;
    private String email;
    private String password;
    private String phoneNum;
    private String dateCreated;

    public User(String name, String email, String password,String phoneNum) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.dateCreated = formatter.format(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}

