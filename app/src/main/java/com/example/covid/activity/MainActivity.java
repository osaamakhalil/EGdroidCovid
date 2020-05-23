package com.example.covid.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid.R;
import com.example.covid.adapter.PagerAdapter;
import com.example.covid.country.CountriesFragment;
import com.example.covid.saved.SavedFragment;
import com.example.covid.world.WorldFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.search_view)
    ImageView searchView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_cancel)
    ImageView imgCancel;
    @BindView(R.id.title_name)
    TextView titleName;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private List<Pair<String, Fragment>> fragments = new ArrayList<>();
    private CountriesFragment countriesFragment = new CountriesFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //fragment list
        fragments.add(new Pair<>("World", new WorldFragment()));
        fragments.add(new Pair<>("Country", countriesFragment));
        fragments.add(new Pair<>("Saved", new SavedFragment()));

        //set the view pager
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 0, fragments);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        //set Titles
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        //control in top views
        searchView.setOnClickListener(view -> {
            etSearch.setVisibility(View.VISIBLE);
            imgCancel.setVisibility(View.VISIBLE);
            titleName.setVisibility(View.INVISIBLE);


        });
        imgCancel.setOnClickListener(view -> {
            etSearch.setVisibility(View.INVISIBLE);
            imgCancel.setVisibility(View.INVISIBLE);
            titleName.setVisibility(View.VISIBLE);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                countriesFragment.search(editable.toString().trim());
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    searchView.setVisibility(View.VISIBLE);
                } else {
                    searchView.setVisibility(View.GONE);
                    etSearch.setVisibility(View.GONE);
                    imgCancel.setVisibility(View.GONE);
                    titleName.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
