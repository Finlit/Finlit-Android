package com.app.finlit.data.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetCommentsModel {

    public String id;
    public String text;
    public Date createdAt;
    public String blogId;
    public CommentModel user;



}
