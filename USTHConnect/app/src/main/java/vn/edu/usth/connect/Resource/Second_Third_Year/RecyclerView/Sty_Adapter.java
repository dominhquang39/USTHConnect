package vn.edu.usth.connect.Resource.Second_Third_Year.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.Second_Third_Year.Year_Course_Resource_Activity;

public class Sty_Adapter extends RecyclerView.Adapter<Sty_ViewHolder>{

    Context context;
    List<Sty_Item> items;

    public Sty_Adapter(Context context, List<Sty_Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Sty_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Sty_ViewHolder(LayoutInflater.from(context).inflate(R.layout.double_text_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Sty_ViewHolder holder, int position) {
        Sty_Item item = items.get(position);

        holder.heading.setText(item.getHeading());
        holder.subhead.setText(item.getSubhead());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, Year_Course_Resource_Activity.class);
            i.putExtra("Course Name", item.getHeading());
            context.startActivity(i);
        });
    }

    public void setFilter(List<Sty_Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
