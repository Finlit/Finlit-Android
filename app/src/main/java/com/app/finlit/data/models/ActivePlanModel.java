package com.app.finlit.data.models;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MANISH-PC on 10/30/2018.
 */

@Getter
@Setter
public class ActivePlanModel implements Serializable {

    public String id;
    public Date startDate;
    public Date expiryDate;

    public SubscriptionModel plan;
}
