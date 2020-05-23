package com.example.covid.saved.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = FavoriteModel.class, version = 1, exportSchema = false)
public abstract class SavedCountryDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "saved_Countries";

    private static SavedCountryDatabase instance;

   public static SavedCountryDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    SavedCountryDatabase.class,
                    DATABASE_NAME)
                    .build();
        }
        return instance;
    }
    public abstract SavedCountryDao getSavedDao();
}
