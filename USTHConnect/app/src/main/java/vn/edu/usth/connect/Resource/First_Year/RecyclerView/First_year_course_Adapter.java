package vn.edu.usth.connect.Resource.First_Year.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.First_Year.Course_Resource_Activity;

public class First_year_course_Adapter extends RecyclerView.Adapter<First_year_course_ViewHolder> {

    Context context;
    List<First_year_course_Item> items;

    public First_year_course_Adapter(Context context, List<First_year_course_Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public First_year_course_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new First_year_course_ViewHolder(LayoutInflater.from(context).inflate(R.layout.double_text_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull First_year_course_ViewHolder holder, int position) {
        First_year_course_Item item = items.get(position);

        holder.heading.setText(item.getHeading());
        holder.subhead.setText(item.getSubhead());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, Course_Resource_Activity.class);
            i.putExtra("Course Name", item.getHeading());
            context.startActivity(i);
        });
    }

    public void setFilter(List<First_year_course_Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
