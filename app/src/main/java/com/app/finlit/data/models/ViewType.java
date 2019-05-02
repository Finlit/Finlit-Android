package com.app.finlit.data.models;

public enum ViewType {

    ITEM(1),
    LOADING(2);

    private int value;

    ViewType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static ViewType parseByValue(int value) {
        ViewType[] prioritiesArray = ViewType.values();
        ViewType result = null;
        for (ViewType type : prioritiesArray) {
            if (type.getValue() == value) {
                result = type;
                break;
            }
        }
        return result;
    }
}
