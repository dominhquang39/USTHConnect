package vn.edu.usth.connect.Resource;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.MainActivity;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.RecyclerView.Resource_course_Adapter;
import vn.edu.usth.connect.Resource.RecyclerView.Resource_course_Item;

public class Resource_Activity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private List<Resource_course_Item> items;
    private Resource_course_Adapter adapter;

    private ImageView avatar_profile_image;
    private Handler handler = new Handler();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_resource);

        ImageButton mImageView = findViewById(R.id.menu_button);

        mDrawerLayout = findViewById(R.id.resource_page);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        
        navigator_drawer_function();

        setup_recyclerview_function();

        setup_function();

        update_picture();
    }

    private void setup_recyclerview_function() {
        RecyclerView recyclerView = findViewById(R.id.resource_recyclerview);

        items = new ArrayList<Resource_course_Item>();

        items.add(new Resource_course_Item("First Year - Sciences (three-year program)"));
        items.add(new Resource_course_Item("Information and Communication Technology"));
        items.add(new Resource_course_Item("Pharmacological Medical and Agronomical Biotechnology"));
        items.add(new Resource_course_Item("Advanced Materials Science and Nanotechnology"));
        items.add(new Resource_course_Item("Applied Engineering and Technology (AET)"));
        items.add(new Resource_course_Item("Applied Environmental Sciences"));
        items.add(new Resource_course_Item("Space and Applications"));
        items.add(new Resource_course_Item("Medical Science and Technology (MST)"));
        items.add(new Resource_course_Item("Food Science and Technology (FST)"));
        items.add(new Resource_course_Item("Aeronautical Maintenance and Engineering"));
        items.add(new Resource_course_Item("Chemistry"));
        items.add(new Resource_course_Item("Applied Mathematics"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Resource_course_Adapter(this, items);
        recyclerView.setAdapter(adapter);

        // SearchView
        SearchView searchView = findViewById(R.id.resource_searchView);
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

    private void navigator_drawer_function(){
        LinearLayout to_home_activity = findViewById(R.id.to_home_page);
        to_home_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Resource_Activity.this, vn.edu.usth.connect.MainActivity.class);
                startActivity(i);
            }
        });

        LinearLayout to_schedule_activity = findViewById(R.id.to_schedule_page);
        to_schedule_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Resource_Activity.this, vn.edu.usth.connect.Schedule.Schedule_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_campus_activity = findViewById(R.id.to_map_page);
        to_campus_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Resource_Activity.this, vn.edu.usth.connect.Campus.Campus_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_resource_activity = findViewById(R.id.to_resource_page);
        to_resource_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Resource_Activity.this, vn.edu.usth.connect.Resource.Resource_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_study_activity = findViewById(R.id.to_study_page);
        to_study_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Resource_Activity.this, vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.class);
                startActivity(i);
            }
        });
    }

    private void setup_function(){

    }

    private void update_picture(){
        avatar_profile_image = findViewById(R.id.avatar_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("ProfileImage", MODE_PRIVATE);
        String url = sharedPreferences.getString("Image_URL", null);
        if (url != null) {
            new UpdateImage(url).start();
        }
    }

    class UpdateImage extends Thread {
        private String url;
        private Bitmap bitmap;

        public UpdateImage(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            handler.post(() -> {
                if (bitmap != null) {
                    avatar_profile_image.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(Resource_Activity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void filterList(String text) {
        List<Resource_course_Item> filteredItems = new ArrayList<>();

        for (Resource_course_Item item : items) {
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
}