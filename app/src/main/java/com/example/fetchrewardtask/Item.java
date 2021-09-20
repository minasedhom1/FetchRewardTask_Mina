package com.example.fetchrewardtask;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    private int id;

    @SerializedName("listId")
    private int list_id;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getList_id() {
        return list_id;
    }

}
