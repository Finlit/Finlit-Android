package com.app.finlit.utils.helpers;


import com.app.finlit.data.repositories.ChatRepositories;

public class DatabaseHelper {

    private static DatabaseHelper instance;

    private ChatRepositories chatRepository;

    private DatabaseHelper() {
    }

    public static void init() {
        if (null == instance) {
            instance = new DatabaseHelper();
        }
    }

    public static DatabaseHelper getInstance() {
        return instance;
    }

    public ChatRepositories getChatRepository(){
        if (chatRepository==null){
            chatRepository = new ChatRepositories();
        }
        return chatRepository;
    }
}