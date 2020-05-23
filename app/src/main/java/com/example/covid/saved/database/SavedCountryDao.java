package com.example.covid.saved.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SavedCountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertCountry(FavoriteModel... country);

    @Delete
    int deleteCountry(FavoriteModel... country);

    @Query("SELECT * FROM saved_Countries")
    LiveData<List<FavoriteModel>> getAllCountries();

    @Query("SELECT * FROM saved_Countries WHERE name LIKE :name ")
    LiveData<List<FavoriteModel>>  getName(String name);

}
