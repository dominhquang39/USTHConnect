package vn.edu.usth.connect.Schedule.Course;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Course.RecyclerView.CourseAdapter;
import vn.edu.usth.connect.Schedule.Course.RecyclerView.CourseItem;

public class Third_Course_Activity extends AppCompatActivity {

    private List<CourseItem> items;
    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_third_course);

        setup_recyclerview();

        setup_recyclerview_function();

        setup_function();

    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setup_recyclerview(){
        TextView program_name = findViewById(R.id.program_name);

        String name = getIntent().getStringExtra("Program Name");

        program_name.setText(name);
    }

    private void setup_recyclerview_function(){
        RecyclerView recyclerView = findViewById(R.id.third_year_recyclerview);

        items = new ArrayList<CourseItem>();

        items.add(new CourseItem("Web Application Development", "Msc. Kieu Quoc Viet & Msc. Huynh Vinh Nam"));
        items.add(new CourseItem("Object-oriented system analysis and design", "Dr. Do Trung Dung"));
        items.add(new CourseItem("Mobile Application Development", "Dr. Tran Giang Son & Msc. Kieu Quoc Viet"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseAdapter(this, items);
        recyclerView.setAdapter(adapter);

        // SearchView
        SearchView searchView = findViewById(R.id.third_year_course_searchView);
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
        List<CourseItem> filteredItems = new ArrayList<>();

        for (CourseItem item : items) {
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