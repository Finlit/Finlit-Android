package com.app.finlit.data.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MANISH-PC on 9/18/2018.
 */

@Getter
@Setter
public class LocationModel implements Serializable {
    public String address;
    public double[] coordinates;
}
