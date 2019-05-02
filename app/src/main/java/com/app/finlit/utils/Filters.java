package com.app.finlit.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MANISH-PC on 8/23/2018.
 */

public class Filters {

    private static Filters instance;
    private Map<String, String> queries = new HashMap<>();

    private Filters() {
    }

    public static  void init(){
        if (instance == null)
            instance = new Filters();
    }

    public static Filters getInstance(){
        return instance;
    }

    public void addFilter(String key, String value){
        queries.put(key, value);
    }

    public Map<String, String> getFilter(){
        return queries;
    }

    public String getByKey(String key){
        return queries.get(key);
    }

    public void reset(){
        queries.clear();
    }

    public void deleteByKey(String key){
        queries.remove(key);
    }



}
