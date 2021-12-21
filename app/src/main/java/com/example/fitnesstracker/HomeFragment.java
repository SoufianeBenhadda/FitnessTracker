package com.example.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitnesstracker.dao.HomeDao;
import com.example.fitnesstracker.model.Excercise;
import com.example.fitnesstracker.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {
    private List<Excercise> exos;
    private User user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        List<Excercise> image_details = getListData();
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new CustomListAdapter(getActivity(), image_details));

        Intent i = getActivity().getIntent();
        user = (User)i.getSerializableExtra("user");

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Excercise exo = (Excercise) o;
                Toast.makeText(getActivity(), "Selected :" + " " + exo, Toast.LENGTH_LONG).show();
               /* Intent i = new Intent(getActivity(), Exerciseinfo.class);
                i.putExtra("Exo",exo);
                i.putExtra("user",user);
                startActivity(i);*/
                //container.clearDisappearingChildren();

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    Exerciseinfo exoinfo = new Exerciseinfo();

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("user", user);
                    bundle.putSerializable("exo", exo);
                    exoinfo.setArguments(bundle);
                    ft.replace(R.id.fragment_container, exoinfo);
                    ft.addToBackStack(null);
                    ft.commit();

            }
        });
        return view;
    }
    private List<Excercise> getListData() {
        try {
            exos=new HomeDao().execute().get();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(exos==null){
            Toast toast=Toast.makeText(getActivity(),"No data",Toast.LENGTH_LONG);
            toast.show();
            Log.d("myerror","Error");
        }
        else{

            Toast toast=Toast.makeText(getActivity(),"yes data",Toast.LENGTH_LONG);
            toast.show();
            Log.d("logged","Logged");

        }
        return exos;


    }




}
