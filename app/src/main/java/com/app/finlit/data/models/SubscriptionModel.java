package com.app.finlit.data.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MANISH-PC on 10/29/2018.
 */

@Getter
@Setter
public class SubscriptionModel implements Serializable {
    public String id;
    public String name;
    public float price;
    public String per;

    public String planId;
}
