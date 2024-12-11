package vn.edu.usth.connect.Resource.Second_Third_Year;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.connect.R;

public class Year_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_year);

        setup_recyclerview();

        setup_function();
    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });

        LinearLayout second_year = findViewById(R.id.second_year);
        second_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.Second_Third_Year.Year_Activity.this, vn.edu.usth.connect.Resource.Second_Third_Year.Course_Year_Activity.class);
                i.putExtra("Year", "Second Year");
                startActivity(i);
            }
        });

        LinearLayout third_year = findViewById(R.id.third_year);
        third_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.Second_Third_Year.Year_Activity.this, vn.edu.usth.connect.Resource.Second_Third_Year.Course_Year_Activity.class);
                i.putExtra("Year", "Third Year");
                startActivity(i);
            }
        });
    }

    private void setup_recyclerview(){
        TextView course_name = findViewById(R.id.course_name);

        String name = getIntent().getStringExtra("Program Name");

        course_name.setText(name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}