package com.app.finlit.data.shared;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jaspreet on 20/08/17.
 */
@Getter
@Setter
public class CommonResponse {

    public boolean isSuccess;
    public String message;
    public String error;

}
