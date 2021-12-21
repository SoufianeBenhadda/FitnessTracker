package com.example.fitnesstracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnesstracker.model.Excercise;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter  extends BaseAdapter implements Filterable {

    private List<Excercise> listData;
    private List<Excercise> listDataFiltred;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext,  List<Excercise> listData) {

        this.listData = listData;
        this.listDataFiltred= listData;
        this.context = aContext;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listDataFiltred.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataFiltred.get(position);
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

        Excercise exo = this.listDataFiltred.get(position);
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
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = listData.size();
                    filterResults.values = listData;

                }else{
                    List<Excercise> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(Excercise itemsModel:listData){
                        if(itemsModel.getTitle().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);
                            Log.d("MESSAAGE writen",searchStr );

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                listDataFiltred = (List<Excercise>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }

    static class ViewHolder {
        ImageView exoIcon;
        TextView exoTitle;
        TextView exoDesc;
    }

}