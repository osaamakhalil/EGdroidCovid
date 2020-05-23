package com.example.covid.saved.database.asynctask;

import android.os.AsyncTask;

import com.example.covid.saved.database.FavoriteModel;
import com.example.covid.saved.database.SavedCountryDao;

public class DeleteAsyncTask extends AsyncTask<FavoriteModel,Void,Void> {
    private SavedCountryDao savedCountryDao;

    public DeleteAsyncTask(SavedCountryDao savedCountryDao) {
        this.savedCountryDao = savedCountryDao;
    }

    @Override
    protected Void doInBackground(FavoriteModel... favoriteModels) {
        savedCountryDao.deleteCountry(favoriteModels);
        return null;
    }
}
