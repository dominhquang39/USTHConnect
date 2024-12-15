package vn.edu.usth.connect.Schedule.Course_RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class Course_course_ViewHolder extends RecyclerView.ViewHolder{

    TextView heading;

    public Course_course_ViewHolder(@NonNull View itemView){
        super(itemView);
        heading = itemView.findViewById(R.id.first_text);
    }
}
