package vn.edu.usth.connect.Resource.First_Year;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.Resource;

public class Course_Resource_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_resource);

        setup_recyclerview();

        setup_function();

    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });

        LinearLayout lecture = findViewById(R.id.lecture);
        lecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.First_Year.Course_Resource_Activity.this, Resource.class);
                i.putExtra("Task", "Lectures");
                startActivity(i);
            }
        });

        LinearLayout labwork = findViewById(R.id.labwork);
        labwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.First_Year.Course_Resource_Activity.this, Resource.class);
                i.putExtra("Task", "Labwork");
                startActivity(i);
            }
        });

        LinearLayout sampleCode = findViewById(R.id.sampleCode);
        sampleCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.First_Year.Course_Resource_Activity.this, Resource.class);
                i.putExtra("Task", "Sample Code");
                startActivity(i);
            }
        });

        LinearLayout document = findViewById(R.id.document);
        document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.First_Year.Course_Resource_Activity.this, Resource.class);
                i.putExtra("Task", "Document");
                startActivity(i);
            }
        });

        LinearLayout midtermExam = findViewById(R.id.midterm_exam);
        midtermExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.First_Year.Course_Resource_Activity.this, Resource.class);
                i.putExtra("Task", "Midterm Exam");
                startActivity(i);
            }
        });

        LinearLayout finalExam = findViewById(R.id.final_exam);
        finalExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.First_Year.Course_Resource_Activity.this, Resource.class);
                i.putExtra("Task", "Final Exam");
                startActivity(i);
            }
        });

        LinearLayout retakeExam = findViewById(R.id.retake_exam);
        retakeExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.First_Year.Course_Resource_Activity.this, Resource.class);
                i.putExtra("Task", "Retake Exam");
                startActivity(i);
            }
        });

    }

    private void setup_recyclerview(){
        TextView course_name = findViewById(R.id.course_name);

        String name = getIntent().getStringExtra("Course Name");

        course_name.setText(name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}