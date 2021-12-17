package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fitnesstracker.dao.ClientDao;
import com.example.fitnesstracker.dao.ConversationDao;
import com.example.fitnesstracker.model.ChatConversation;
import com.example.fitnesstracker.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Conversations extends AppCompatActivity {

    ListView conversations;
    List<ChatConversation> conversation_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        SharedPreferences sp=getApplicationContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String username=sp.getString("session_username","");

        conversations=findViewById(R.id.conversations);

        try {
            conversation_users=new ConversationDao().execute().get();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        conversations.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return conversation_users.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view1 = getLayoutInflater().inflate(R.layout.users_list,null);

                User client=null;
                try {
                    client=new ClientDao().execute(conversation_users.get(position).getClient()).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TextView sender = view1.findViewById(R.id.c_user);
                //sender.setText(conversation_users.get(position).getClient());
                sender.setText(client.getFirstName()+" "+client.getLastName());
                ImageView picture = view1.findViewById(R.id.imageView2);
                /*if(!client.getPicture().equals(null) || !client.getPicture().equals(""))
                    Picasso.with(getApplicationContext()).load(client.getPicture())
                        .into(picture);
                else{*/
                if(client.getGender().equals("female"))
                    Picasso.with(getApplicationContext()).load("https://i.postimg.cc/XJ8HZ7Ly/petite-fille.png")
                            .into(picture);
                else{
                    Picasso.with(getApplicationContext()).load("https://i.postimg.cc/9Q7LdB12/profil.png")
                            .into(picture);
                }
                //}
                return view1;
            }
        });

        conversations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getApplicationContext(),PrivateConversation.class);
                intent.putExtra("client",conversation_users.get(position).getClient());
                intent.putExtra("conversation",conversation_users.get(position).getId());
                startActivity(intent);

            }
        });

    }
}