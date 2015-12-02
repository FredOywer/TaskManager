package ke.fred.taskmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Fredrick on 04/06/2015.
 */
public class CustomAdapter extends ArrayAdapter<Holder>{
    int item_id;
    ArrayList<Holder> tasks;
    Context context;

    public CustomAdapter(Context context, int vg, ArrayList<Holder> tasks) {
        super(context, vg, tasks);
        this.context = context;
        item_id = vg;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = convertView;
        Holder item = getItem(position);

        if (v == null) {
            v = inflater.inflate(item_id, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView)v.findViewById(R.id.tvTitle);
            holder.date = (TextView)v.findViewById(R.id.tvDate);
            holder.ivPriority = (ImageView)v.findViewById(R.id.ivPriority);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.title.setText(item.getTitle());
        holder.date.setText(item.getDate());

        if ((item.getPriority()).equals("High") ){
            int prHigh = Color.parseColor("#F44336");
            holder.ivPriority.setColorFilter(prHigh);
        } else {
            int prLow = Color.parseColor("#4CAF50");
            holder.ivPriority.setColorFilter(prLow);
        }
        return v;
    }

    private static class ViewHolder {
        TextView title;
        TextView date;
        ImageView ivPriority;
    }
}
