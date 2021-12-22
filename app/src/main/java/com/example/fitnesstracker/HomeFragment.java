package com.example.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
    private EditText etSearch;
    private CustomListAdapter adapter1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        etSearch = (EditText) view.findViewById(R.id.etSearch);
        List<Excercise> image_details = getListData();
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        adapter1=new CustomListAdapter(getActivity(), image_details);
        listView.setAdapter(adapter1);

        Intent i = getActivity().getIntent();
        user = (User)i.getSerializableExtra("user");
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter1.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Excercise exo = (Excercise) o;
                Toast.makeText(getActivity(), "Selected :" + " " + exo, Toast.LENGTH_LONG).show();

                FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
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
