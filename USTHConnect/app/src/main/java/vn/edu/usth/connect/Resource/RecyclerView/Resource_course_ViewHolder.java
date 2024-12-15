package vn.edu.usth.connect.Resource.RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class Resource_course_ViewHolder extends RecyclerView.ViewHolder{

    TextView heading;

    public Resource_course_ViewHolder(@NonNull View itemView){
        super(itemView);
        heading = itemView.findViewById(R.id.first_text);
    }

}
