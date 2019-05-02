package com.app.finlit.data.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionModel implements Serializable {
        public String id;
        public String text;
        public boolean isCorrect;
        public boolean isSelected;

}
