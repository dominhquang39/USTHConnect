package vn.edu.usth.connect.Schedule;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import vn.edu.usth.connect.R;

public class Timetable_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timetable_, container, false);

        schedule_tablayout(v);

        return v;
    }

    private void schedule_tablayout(View v){
        TabLayout tabLayout = v.findViewById(R.id.schedule_tablayout);
        ViewPager2 viewPager2 = v.findViewById(R.id.schedule_viewPager2);

        Schedule_adapter adapter = new Schedule_adapter(getChildFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);
        viewPager2.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, viewPager2, (tab,position) -> {
            switch (position) {
                case 0:
                    tab.setText("Month");
                    break;
                case 1:
                    tab.setText("Week");
                    break;
                case 2:
                    tab.setText("Day");
                    break;
            }
        }).attach();
    }
}