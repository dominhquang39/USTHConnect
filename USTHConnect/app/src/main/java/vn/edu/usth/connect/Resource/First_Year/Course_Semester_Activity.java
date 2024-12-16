package vn.edu.usth.connect.Resource.First_Year;

import android.os.Bundle;

import android.widget.ImageButton;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.First_Year.RecyclerView.First_year_course_Adapter;
import vn.edu.usth.connect.Resource.First_Year.RecyclerView.First_year_course_Item;

public class Course_Semester_Activity extends AppCompatActivity {

    private List<First_year_course_Item> items;
    private First_year_course_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_semester);

        setup_recyclerview();
        
        setup_recyclerview_function();

        setup_function();

    };

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setup_recyclerview(){
        TextView course_name = findViewById(R.id.program_name);
        String name = getIntent().getStringExtra("Semester");
        course_name.setText(name);
    }

    private void setup_recyclerview_function(){
        RecyclerView recyclerView = findViewById(R.id.course_semester_recyclerview);

        items = new ArrayList<First_year_course_Item>();

        items.add(new First_year_course_Item("Introduction to Infomatics", "Dr. "));
        items.add(new First_year_course_Item("Fundamental Physics 1", "Dr. "));
        items.add(new First_year_course_Item("General Chemistry 1", "Dr. "));
        items.add(new First_year_course_Item("Cellular Biology", "Dr. "));
        items.add(new First_year_course_Item("Calculus 1", "Dr. "));
        items.add(new First_year_course_Item("Calculus 2", "Dr. "));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new First_year_course_Adapter(this, items);
        recyclerView.setAdapter(adapter);

        // SearchView
        SearchView searchView = findViewById(R.id.course_semester_searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String text) {
        List<First_year_course_Item> filteredItems = new ArrayList<>();

        for (First_year_course_Item item : items) {
            if (item.getHeading().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.setFilter(filteredItems);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}