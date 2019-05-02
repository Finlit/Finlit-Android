package com.app.finlit.data.models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatModel {
    public String id;
    public String imgUrl;
    public String name;
    public String message;
    public String myId;
    public String opponentId;
    public long timeStamp;
    public String chatId;

    //server side
    public String chatType;
    public String text;
    public String lastMessage;
    public List<ParticipantsModel> participants;
    public boolean isBlocked;


    //mapping for firebase
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("chatId", id);
        result.put("imgUrl", imgUrl);
        result.put("opponentId", opponentId);
        result.put("myId", myId);
        result.put("message", message);
        result.put("timeStamp", System.currentTimeMillis());
        return result;
    }
}
