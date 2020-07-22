package com.muxad.app.feesystem;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.muxad.app.feesystem.ui.main.SectionsPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Fee Management System");

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.btnRefresh:
            refresh();
            break;
        case R.id.logout:
            Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_SHORT).show();
            break;
    }
        return(super.onOptionsItemSelected(item));
    }

    private void refresh(){
        FeeDetailsFragment feeDetailsFragment = new FeeDetailsFragment();
        AttendanceFragment attendanceFragment = new AttendanceFragment();
        androidx.fragment.app.Fragment fragment = null;

        Fragment FeeDetails = getFragmentManager().findFragmentById(feeDetailsFragment.getId());
        if (FeeDetails != null && FeeDetails.isVisible()) {
            fragment = getSupportFragmentManager().findFragmentById(feeDetailsFragment.getId());
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(fragment);
            ft.attach(fragment);
            ft.commit();
        }

        Fragment AttendanceDetails = getFragmentManager().findFragmentById(attendanceFragment.getId());
        if (FeeDetails != null && AttendanceDetails.isVisible()) {
            fragment = getSupportFragmentManager().findFragmentById(attendanceFragment.getId());
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(fragment);
            ft.attach(fragment);
            ft.commit();
        }

    }


}