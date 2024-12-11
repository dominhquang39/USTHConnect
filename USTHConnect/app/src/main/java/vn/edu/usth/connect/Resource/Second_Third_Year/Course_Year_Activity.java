package vn.edu.usth.connect.Resource.Second_Third_Year;

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
import vn.edu.usth.connect.Resource.Second_Third_Year.RecyclerView.Sty_Adapter;
import vn.edu.usth.connect.Resource.Second_Third_Year.RecyclerView.Sty_Item;

public class Course_Year_Activity extends AppCompatActivity {
    
    private List<Sty_Item> items;
    private Sty_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_year);

        setup_recyclerview_function();

        setup_recyclerview();

        setup_function();
    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setup_recyclerview(){
        TextView course_name = findViewById(R.id.year_course);

        String name = getIntent().getStringExtra("Year");

        course_name.setText(name);
    }

    private void setup_recyclerview_function(){
        RecyclerView recyclerView = findViewById(R.id.year_course_recyclerview);

        items = new ArrayList<Sty_Item>();

        items.add(new Sty_Item("Advanced Programming with Python", "Dr. Tran Giang Son"));
        items.add(new Sty_Item("Algorithms and Data Structures", "Dr. Doan Nhat Quang"));
        items.add(new Sty_Item("Signal and System", "Dr. Tran Duc Tan"));
        items.add(new Sty_Item("Numerical Methods", "Dr. "));
        items.add(new Sty_Item("Fundamental Database", "Dr. "));
        items.add(new Sty_Item("Probability and Statistics", "Dr. "));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Sty_Adapter(this, items);
        recyclerView.setAdapter(adapter);

        // SearchView
        SearchView searchView = findViewById(R.id.year_course_searchView);
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
        List<Sty_Item> filteredItems = new ArrayList<>();

        for (Sty_Item item : items) {
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