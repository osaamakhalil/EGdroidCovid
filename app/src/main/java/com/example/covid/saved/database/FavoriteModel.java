package com.example.covid.saved.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_Countries")
public class FavoriteModel {


    @PrimaryKey
    @NonNull
    private String name;
    private String iconCode;

    public FavoriteModel(@NonNull String name, String iconCode) {
        this.name = name;
        this.iconCode = iconCode;
    }
 public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }
}
