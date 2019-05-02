package com.app.finlit.data.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MANISH-PC on 10/18/2018.
 */

@Getter
@Setter
public class ParticipantsModel implements Serializable {
    public String userId;
    public UserModel user;
    public int unreadCount;
    public boolean isBlocked;
}
