package com.example.fitnesstracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fitnesstracker.model.Tracker;

import java.util.ArrayList;

public class RepsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Tracker> repsModelArrayList;

    public RepsAdapter(Context context,ArrayList<Tracker> repsModelArrayList){
        this.context = context;
        this.repsModelArrayList = repsModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return repsModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return repsModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null, true);

            holder.tvWeight = (TextView) convertView.findViewById(R.id.tvWeight);
            holder.tvReps = (TextView) convertView.findViewById(R.id.tvReps);
            holder.tvRepMax = (TextView) convertView.findViewById(R.id.tvRepMax);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvWeight.setText(String.valueOf(repsModelArrayList.get(position).getWeight()));
        holder.tvReps.setText(String.valueOf(repsModelArrayList.get(position).getReps()));
        holder.tvRepMax.setText(String.valueOf(repsModelArrayList.get(position).getRepmax()));
        holder.tvDate.setText(String.valueOf(repsModelArrayList.get(position).getDate()));
        notifyDataSetChanged();
        return convertView;
    }

    private class ViewHolder {

        protected TextView tvWeight, tvReps, tvRepMax, tvDate;

    }
}
