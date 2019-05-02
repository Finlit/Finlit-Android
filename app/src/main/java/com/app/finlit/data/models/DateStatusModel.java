package com.app.finlit.data.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateStatusModel  implements Serializable {

    public String status;
    public Date createdAt;
    public String name;
    public String imgUrl;
    public String userid;
    public String id;
    public int ageGroup;
    public Date updatedAt;
    public String date;

    public LocationdateModel location;
    public UserLocationdateModel userlocation;

    @Getter
    @Setter
    List<String> question;                //for arraylist if nothinf is inside


}
