package com.app.finlit.utils;

import android.content.Context;
import android.content.Intent;

public class Utils {

    public static int getTotalPages(int total, int perPage){
        return (total + perPage - 1) / perPage;
    }

    public static String capSentences( final String text ) {

        return text.substring( 0, 1 ).toUpperCase() + text.substring( 1 ).toLowerCase();
    }

    public static void sharePost(String title, String description, Context context){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(Intent.EXTRA_TEXT,title +"  :  "+description);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
