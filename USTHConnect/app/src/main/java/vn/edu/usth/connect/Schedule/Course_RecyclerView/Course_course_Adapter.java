package vn.edu.usth.connect.Schedule.Course_RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Course.First_Course_Activity;
import vn.edu.usth.connect.Schedule.Course.Program_Activity;


public class Course_course_Adapter extends RecyclerView.Adapter<Course_course_ViewHolder>{

    Context context;
    List<Course_course_Item> items;

    public Course_course_Adapter(Context context, List<Course_course_Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Course_course_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Course_course_ViewHolder(LayoutInflater.from(context).inflate(R.layout.one_text_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Course_course_ViewHolder holder, int position) {
        Course_course_Item item = items.get(position);

        holder.heading.setText(item.getHeading());

        holder.itemView.setOnClickListener(v -> {
            if(item.getHeading().equals("First Year - Sciences (three-year program)")){
                Intent i = new Intent(context, First_Course_Activity.class);
                i.putExtra("Program Name", item.getHeading());
                context.startActivity(i);
            }else{
                Intent i = new Intent(context, Program_Activity.class);
                i.putExtra("Program Name", item.getHeading());
                context.startActivity(i);
            }
        });
    }

    public void setFilter(List<Course_course_Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
