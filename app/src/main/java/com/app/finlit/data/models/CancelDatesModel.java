package com.app.finlit.data.models;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelDatesModel implements Serializable {

    public Date createdAt;
    public location location;

    @Getter
    @Setter
    public class location{
        public String address;
        public double[] coordinates;

    }



}
