package com.app.finlit.data.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SecuritySetupModel implements Serializable {

    public String username;
    public String password;
}
