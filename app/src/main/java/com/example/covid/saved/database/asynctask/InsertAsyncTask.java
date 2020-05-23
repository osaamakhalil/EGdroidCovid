package com.example.covid.saved.database.asynctask;

import android.os.AsyncTask;

import com.example.covid.saved.database.FavoriteModel;
import com.example.covid.saved.database.SavedCountryDao;

public class InsertAsyncTask extends AsyncTask<FavoriteModel,Void,Void> {

    private SavedCountryDao savedCountryDao;
    public InsertAsyncTask(SavedCountryDao savedCountryDao) {
        this.savedCountryDao=savedCountryDao;
    }

    @Override
    protected Void doInBackground(FavoriteModel... favoriteModels) {
        savedCountryDao.insertCountry(favoriteModels);
        return null;
    }
}
