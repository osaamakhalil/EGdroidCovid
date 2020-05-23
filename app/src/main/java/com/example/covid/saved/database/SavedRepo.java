package com.example.covid.saved.database;

import android.content.Context;

import com.example.covid.saved.database.asynctask.DeleteAsyncTask;
import com.example.covid.saved.database.asynctask.InsertAsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SavedRepo {

    private SavedCountryDatabase savedCountryDatabase;


    public SavedRepo(Context context) {
    savedCountryDatabase = SavedCountryDatabase.getInstance(context);
    }
    public void insertCountryTask(FavoriteModel country){
        new InsertAsyncTask(savedCountryDatabase.getSavedDao()).execute(country);

    }
    public void deleteCountry(FavoriteModel country){
        new DeleteAsyncTask(savedCountryDatabase.getSavedDao()).execute(country);

    }
    public LiveData<List<FavoriteModel>> getAllCountry(){
        return savedCountryDatabase.getSavedDao().getAllCountries();
    }
    public LiveData<List<FavoriteModel>> getName(String name){
        return  savedCountryDatabase.getSavedDao().getName(name);
    }

}
