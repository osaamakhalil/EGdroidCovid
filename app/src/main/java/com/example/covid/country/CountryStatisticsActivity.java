package com.example.covid.country;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.bumptech.glide.Glide;
import com.example.covid.R;
import com.example.covid.api.NetworkUtil;
import com.example.covid.api.Networking;
import com.example.covid.api.OnStatisticsListener;
import com.example.covid.country.countryModel.Country;
import com.example.covid.country.countryModel.DataPickerFragment;
import com.example.covid.saved.database.FavoriteModel;
import com.example.covid.saved.database.SavedRepo;
import com.example.covid.world.StatisticModel.Response;
import com.example.covid.world.StatisticModel.Statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryStatisticsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.tv_country_name)
    TextView tvCountryName;
    @BindView(R.id.country_icon)
    ImageView countryIcon;
    @BindView(R.id.active_num_tv)
    TextView activeNumTv;
    @BindView(R.id.critical_num_tv)
    TextView criticalNumTv;
    @BindView(R.id.recovered_num_tv)
    TextView recoveredNumTv;
    @BindView(R.id.total_num_tv)
    TextView totalNumTv;
    @BindView(R.id.new_death_num_tvt)
    TextView newDeathNumTv;
    @BindView(R.id.total_death_num_tv)
    TextView totalDeathNumTv;
    @BindView(R.id.tv_new_num)
    TextView tvNewNum;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.favorite_image)
    ImageView favoriteImage;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.my_layout)
    LinearLayout myLayout;
    @BindView(R.id.no_internet)
    TextView noInternet;
    @BindView(R.id.btn_date)
    ImageView btnDate;

    private SavedRepo savedRepo;
    private FavoriteModel favoriteModel;
    private Country country = new Country();
    private Boolean isFavorite = false;
    private String countryName;
    private String iconCode;
    private String myDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_statistics);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());
        savedRepo = new SavedRepo(this);

        getIntents();

        statisticsRequest();
        //db
        country.setName(countryName);
        country.setCode(iconCode);
        getNameCountry(countryName);
        //fill favorite btn
        favoriteImage.setOnClickListener(view -> {
            if (isFavorite) {
                deleteCountry();
            } else {
                saveNewCountry();
            }
        });
        btnDate.setOnClickListener(view -> {
            DialogFragment datePicker = new DataPickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

    }

    //get extra intent (countryName,IconCode)
    private void getIntents() {
        //set icon with name
        Intent intent = getIntent();
        countryName = intent.getStringExtra("countryName");
        tvCountryName.setText(countryName);
        //set icons
        iconCode = intent.getStringExtra("IconCode");
        Glide
                .with(getApplicationContext())
                .load("https://www.countryflags.io/" + iconCode + "/flat/64.png")
                .into(countryIcon);
    }

    private void statisticsRequest() {
        if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            //get request by afn from api package
            Networking.getStatistics(new OnStatisticsListener() {
                @Override
                public void onSuccess(Statistics response) {
                    progress.setVisibility(View.GONE);
                    myLayout.setVisibility(View.VISIBLE);
                    List<Response> countryStatistics = response.getResponse();
                    for (int i = 0; i < countryStatistics.size(); i++) {
                        if (countryStatistics.get(i).getCountry().equals(countryName)) {
                            Response e = countryStatistics.get(i);
                            getCountryStatistics(e);
                        }
                    }
                }

                @Override
                public void onFailed(String message) {
                    Toast.makeText(CountryStatisticsActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            progress.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }


    }

    //get country statistics today
    private void getCountryStatistics(Response response) {
        //error handel*
        if (response.getCases().getNew() != null) {
            tvNewNum.setText(response.getCases().getNew());
        } else {
            tvNewNum.setText("0");
        }
        activeNumTv.setText(response.getCases().getActive().toString());
        criticalNumTv.setText(response.getCases().getCritical().toString());
        recoveredNumTv.setText(response.getCases().getRecovered().toString());
        totalNumTv.setText(response.getCases().getTotal().toString());
        //error handel*
        if (response.getDeaths().getNew() != null) {
            newDeathNumTv.setText(response.getDeaths().getNew().toString());
        } else {
            newDeathNumTv.setText("0");
        }
        totalDeathNumTv.setText(response.getDeaths().getTotal().toString());
        dateTv.setText(response.getDay());
    }


    private void saveNewCountry() {
        savedRepo.insertCountryTask(new FavoriteModel(country.getName(), country.getCode()));
        Toast.makeText(this, "Country inserted", Toast.LENGTH_SHORT).show();

    }

    private void deleteCountry() {
        savedRepo.deleteCountry(new FavoriteModel(country.getName(), country.getCode()));
        Toast.makeText(this, "Country deleted", Toast.LENGTH_SHORT).show();

    }

    private void getNameCountry(String name) {
        savedRepo.getName(name).observe(this, new Observer<List<FavoriteModel>>() {
            @Override
            public void onChanged(List<FavoriteModel> favoriteModels) {
                if (favoriteModels == null || favoriteModels.size() == 0) {
                    favoriteImage.setImageResource(R.drawable.ic_signs);
                    isFavorite = false;
                } else {
                    favoriteImage.setImageResource(R.drawable.ic_interface);
                    isFavorite = true;
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String day = dayOfMonth + "";
        String months = month + "";
        if (month < 10) {
            months = "0" + month;
        }
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        }
        myDate = year + "-" + months + "-" + day;

        dateRequest(myDate);
    }

    private void dateRequest(String date) {
        if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            AndroidNetworking.get("https://covid-193.p.rapidapi.com/history?day=" + date + "&country=" + countryName)
                    .addHeaders("x-rapidapi-host", "covid-193.p.rapidapi.com")
                    .addHeaders("x-rapidapi-key", "0afccce25amsh2c82f5da3a90addp1f3307jsn00d72e68da4c")
                    .build().getAsObject(Statistics.class, new ParsedRequestListener<Statistics>() {
                @Override
                public void onResponse(Statistics response) {
                    progress.setVisibility(View.GONE);
                    myLayout.setVisibility(View.VISIBLE);
                    List<Response> historyList = response.getResponse();
                    for (int i = 0; i < historyList.size(); i++) {
                        Response s = historyList.get(i);
                        getCountryStatistics(s);
                    }
                }

                @Override
                public void onError(ANError anError) {
                    Toast.makeText(CountryStatisticsActivity.this, anError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
