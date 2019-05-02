package com.app.finlit.data.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourModel implements Serializable {
    public String url;
    public boolean isSelected;
}

