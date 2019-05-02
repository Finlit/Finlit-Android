package com.app.finlit.data.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel implements Serializable {

    public String id;
    public String imgUrl;
    public String name;
    public String aboutUs;
    public String gender;
    public String username;
    public String password;
    public String newPassword;
    public int ageGroup;
    public List<String> question;
    public String profileType;
    public String chatId;
    public String userId;

    public Date createdAt;
    //public String status;

    public boolean isFavourite;
    public boolean isBlocked;
    public boolean isPaidQuiz;

    public LocationModel location;
    public List<InterestModel> interest;

    public String token;

    public String deviceId;
    public String deviceType;
    public String facebookId;
    public String email;
}
