package com.app.finlit.data.models;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationModel implements Serializable {

    public String id;
    public String description;
    public Date createdAt;
    public String imgUrl;
    public UserModel user;
}
