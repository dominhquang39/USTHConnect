package vn.edu.usth.connect.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Favorite_RecyclerView.Favorite_course_Adapter;
import vn.edu.usth.connect.Schedule.Favorite_RecyclerView.Favorite_course_Item;

public class Favorite_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite_, container, false);

        setup_recyclerview_function(v);

        setup_function(v);

        return v;
    }

    private void setup_function(View v){

    }

    private void setup_recyclerview_function(View v){
        RecyclerView recyclerView = v.findViewById(R.id.favorite_course_recyclerview);

        List<Favorite_course_Item> items = new ArrayList<Favorite_course_Item>();

        items.add(new Favorite_course_Item("Web Application Development", "Msc. Kieu Quoc Viet & Msc. Huynh Vinh Nam"));
        items.add(new Favorite_course_Item("Object-oriented system analysis and design", "Dr. Do Trung Dung"));
        items.add(new Favorite_course_Item("Mobile Application Development", "Dr. Tran Giang Son & Msc. Kieu Quoc Viet"));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new Favorite_course_Adapter(requireContext(), items));
    }

}