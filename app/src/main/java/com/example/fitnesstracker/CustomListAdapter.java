package com.example.fitnesstracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnesstracker.model.Excercise;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomListAdapter  extends BaseAdapter {

    private List<Excercise> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext,  List<Excercise> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_card, null);
            holder = new ViewHolder();
            holder.exoIcon = (ImageView) convertView.findViewById(R.id.imageView_Exoicon);
            holder.exoTitle = (TextView) convertView.findViewById(R.id.textView_Title);
            holder.exoDesc = (TextView) convertView.findViewById(R.id.textView_Desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Excercise exo = this.listData.get(position);
        holder.exoTitle.setText(exo.getTitle());
        holder.exoDesc.setText(exo.getDescription());


        Picasso.with(context).load(exo.getImage())
                .into(holder.exoIcon);
        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        ImageView exoIcon;
        TextView exoTitle;
        TextView exoDesc;
    }

}
