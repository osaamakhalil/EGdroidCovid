package com.example.covid.world;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.covid.R;
import com.example.covid.api.NetworkUtil;
import com.example.covid.api.Networking;
import com.example.covid.api.OnStatisticsListener;
import com.example.covid.world.StatisticModel.Response;
import com.example.covid.world.StatisticModel.Statistics;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorldFragment extends Fragment {
    //ui views
    private TextView new_num_tv, active_num_tv, critical_num_tv,
            recovered_num_tv, total_num_tv, new_death_num_tv,
            total_death_num_tv, date_tv, tv_no_internet;
    private ProgressBar progressBar;
    private View my_layout;


    public WorldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_world, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //find ui views
        new_num_tv = view.findViewById(R.id.new_num_tv);
        active_num_tv = view.findViewById(R.id.active_num_tv);
        critical_num_tv = view.findViewById(R.id.critical_num_tv);
        recovered_num_tv = view.findViewById(R.id.recovered_num_tv);
        total_num_tv = view.findViewById(R.id.total_num_tv);
        new_death_num_tv = view.findViewById(R.id.new_death_num_tv);
        total_death_num_tv = view.findViewById(R.id.total_death_num_tv);
        date_tv = view.findViewById(R.id.date_tv);
        progressBar = view.findViewById(R.id.progress);
        tv_no_internet = view.findViewById(R.id.no_internet);
        my_layout = view.findViewById(R.id.my_layout);

        //handel error Network connection
        if (NetworkUtil.isNetworkAvailable(getContext())) {

            //api get request
            AndroidNetworking.initialize(getActivity().getApplicationContext());
            Networking.getStatistics(new OnStatisticsListener() {
                @Override
                public void onSuccess(Statistics response) {
                    progressBar.setVisibility(View.GONE);
                    my_layout.setVisibility(View.VISIBLE);

                    NumberFormat formatter = NumberFormat.getInstance(Locale.ENGLISH);
                    List<Response> statisticsList = response.getResponse();
                    for (int i = 0; i < statisticsList.size(); i++) {
                        if (statisticsList.get(i).getCountry().equals("All")) {
                            Response r = statisticsList.get(i);
                            getWorldStatistic(formatter, r);
                        }
                    }
                }

                @Override
                public void onFailed(String message) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            progressBar.setVisibility(View.GONE);
            tv_no_internet.setVisibility(View.VISIBLE);
        }
    }

    private void getWorldStatistic(NumberFormat formatter, Response response) {
        new_num_tv.setText(response.getCases().getNew());
        String a = response.getCases().getActive().toString();
        String critical = formatter.format(response.getCases().getCritical());
        active_num_tv.setText(a);
        critical_num_tv.setText(critical);
        recovered_num_tv.setText(response.getCases().getRecovered().toString());
        total_num_tv.setText(response.getCases().getTotal().toString());
        new_death_num_tv.setText(response.getDeaths().getNew().toString());
        total_death_num_tv.setText(response.getDeaths().getTotal().toString());
        date_tv.setText(response.getDay());
    }
}
