package com.app.finlit.data.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MANISH-PC on 12/6/2018.
 */

@Getter
@Setter
public class InterestModel implements Serializable {
    public String question;
    public String answer;
    public boolean isSelected;
}
