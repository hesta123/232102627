package com.example.tablayout;

import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import com.example.tablayout.MyFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private MyFragmentPagerAdapter _myFragmentPagerAdapter;
    private TabLayout _tabLayout1;
    private ViewPager _viewPager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (View v, WindowInsetsCompat insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.POST_NOTIFICATIONS }, 1);
            }
        }

        _tabLayout1 = findViewById(R.id.tabLayout1);
        _tabLayout1.addTab(_tabLayout1.newTab().setText("E-Commerce"));
        _tabLayout1.addTab(_tabLayout1.newTab().setText("Berita"));
        _tabLayout1.addTab(_tabLayout1.newTab().setText("Kampus"));
        _tabLayout1.setTabGravity(TabLayout.GRAVITY_FILL);

        _viewPager1 = findViewById(R.id.viewPager1);

        _myFragmentPagerAdapter = new MyFragmentPagerAdapter(this, getSupportFragmentManager(), _tabLayout1.getTabCount());
        _viewPager1.setAdapter(_myFragmentPagerAdapter);
        _viewPager1.setOffscreenPageLimit(3);

        _viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_tabLayout1));

        _tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                _viewPager1.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
            }
        });
    }
}