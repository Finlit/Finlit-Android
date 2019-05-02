package com.app.finlit.data.models;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionsModel implements Serializable {

    public String id;
    public String label;
    public List<OptionModel> options;
}
