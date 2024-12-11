package vn.edu.usth.connect.Schedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.connect.Schedule.TabLayout_Fragment.Day_Fragment;
import vn.edu.usth.connect.Schedule.TabLayout_Fragment.Month_Fragment;
import vn.edu.usth.connect.Schedule.TabLayout_Fragment.Week_Fragment;

public class Schedule_adapter  extends FragmentStateAdapter {
    public Schedule_adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Month_Fragment();
            case 1:
                return new Week_Fragment();
            case 2:
                return new Day_Fragment();
            default:
                return new Month_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

