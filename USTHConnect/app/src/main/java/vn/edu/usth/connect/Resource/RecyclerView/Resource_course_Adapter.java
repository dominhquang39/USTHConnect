package vn.edu.usth.connect.Resource.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.First_Year.Semester_Activity;
import vn.edu.usth.connect.Resource.Second_Third_Year.Year_Activity;

public class Resource_course_Adapter extends RecyclerView.Adapter<Resource_course_ViewHolder>{

    Context context;
    List<Resource_course_Item> items;

    public Resource_course_Adapter(Context context, List<Resource_course_Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Resource_course_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Resource_course_ViewHolder(LayoutInflater.from(context).inflate(R.layout.one_text_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Resource_course_ViewHolder holder, int position) {
        Resource_course_Item item = items.get(position);

        holder.heading.setText(item.getHeading());

        holder.itemView.setOnClickListener(v -> {
            if(item.getHeading().equals("First Year - Sciences (three-year program)")){
                Intent i = new Intent(context, Semester_Activity.class);
                context.startActivity(i);
            }else{
                Intent i = new Intent(context, Year_Activity.class);
                i.putExtra("Program Name", item.getHeading());
                context.startActivity(i);
            }
        });
    }

    public void setFilter(List<Resource_course_Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
