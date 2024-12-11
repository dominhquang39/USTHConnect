package vn.edu.usth.connect.Schedule.Course.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Course.List_Class_in_Course_Activity;

public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder>{

    Context context;
    List<CourseItem> items;

    public CourseAdapter(Context context, List<CourseItem> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseViewHolder(LayoutInflater.from(context).inflate(R.layout.course_fav_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
       CourseItem item = items.get(position);

        holder.heading.setText(item.getHeading());
        holder.subhead.setText(item.getSubhead());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, List_Class_in_Course_Activity.class);
            i.putExtra("Course Name", item.getHeading());
            i.putExtra("Course Lecturer", item.getSubhead());
            context.startActivity(i);
        });
    }

    public void setFilter(List<CourseItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
