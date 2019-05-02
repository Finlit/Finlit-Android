package com.app.finlit.data.repositories;

import android.support.annotation.NonNull;

import com.app.finlit.data.models.ChatModel;
import com.app.finlit.utils.App;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRepositories {
    private DatabaseReference database;

    public ChatRepositories() {

        database = App.getInstance().getDatabase();
    }

    public interface OnCompleteListener{

        void onSuccessCreateChat();

        void onSuccessChat(List<ChatModel> list);

        void onFailed(String error);
    }

    public void createChat(ChatModel model, final OnCompleteListener onCompleteListener){
        String key = database.child("messages").push().getKey();
        Map<String, Object> values = model.toMap();
        values.put("msgId", key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/messages/" + model.id + "/" + key, values);
        database.updateChildren(childUpdates).addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onCompleteListener.onSuccessCreateChat();
            }
        });
    }

    public void getChats(String id,final OnCompleteListener onCompleteListener){
        Query query = database.child("messages").child(id).orderByChild("timeStamp");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ChatModel> list = new ArrayList<>();
                for (DataSnapshot valuableSnapshot: dataSnapshot.getChildren()) {
                    ChatModel chatModel = valuableSnapshot.getValue(ChatModel.class);
                    list.add(chatModel);
                }

                Collections.sort(list, new Comparator<ChatModel>() {
                    @Override
                    public int compare(ChatModel chatModel, ChatModel t1) {
                        Calendar firstCalendar = Calendar.getInstance();
                        firstCalendar.setTimeInMillis(chatModel.timeStamp);
                        Calendar secondCalendar = Calendar.getInstance();
                        secondCalendar.setTimeInMillis(t1.timeStamp);
                        return firstCalendar.compareTo(secondCalendar);
                    }
                });
                onCompleteListener.onSuccessChat(list);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onCompleteListener.onFailed(databaseError.toException().toString());
            }
        });
    }
}
