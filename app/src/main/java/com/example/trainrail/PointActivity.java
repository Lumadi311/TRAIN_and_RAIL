package com.example.trainrail;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PointActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    public ViewPager viewPager;
//    public Button buttonEnd;
//    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        getSupportActionBar().setTitle("Select Your Place");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        buttonStart=(Button)findViewById(R.id.btnStart);
//        buttonEnd=(Button)findViewById(R.id.btnEnd);

//        buttonStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), FinishActivity.class));
//            }
//        });

//        final String nameTrain=getIntent().getStringExtra("NAME_TRAIN");
//        final String dateTrain=getIntent().getStringExtra("DATE_TRAIN");
//        final String conditionTrain=getIntent().getStringExtra("CONDITION_TRAIN");


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        buttonEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//            }
//        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StartPoint(), "BOARDING"+"\n"+"Add Location");
        adapter.addFragment(new EndPoint(), "DROPPING"+"\n"+"Add Location");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}