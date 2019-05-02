package com.app.finlit.data.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogModel implements Serializable {

    public String id;
    public String title;
    public String description;
    public String imgUrl;
    public String link;
    public int  likeCount;
    public boolean isLike;
    public int  commentCount;

    public user user;
    @Getter
    @Setter

    public class user implements Serializable{
         public String imgUrl;
         public String name;

    }


    }

//    public String saved;
//    public Likemodel like;




