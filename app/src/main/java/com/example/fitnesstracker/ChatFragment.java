package com.example.fitnesstracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesstracker.dao.SendChatMessageDao;
import com.example.fitnesstracker.dao.getClientMessagesDao;
import com.example.fitnesstracker.model.ChatMessage;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChatFragment extends Fragment {

    ListView chatmessages;
    Button send;
    EditText message;
    List<ChatMessage> chat_messages;
    int conversation_id;
    ChatMessage current_message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat, container, false);
        chatmessages=view.findViewById(R.id.cl_chat);
        chatmessages.setDivider(null);
        send=view.findViewById(R.id.button8);
        message=view.findViewById(R.id.cl_type);

        //conversation_id=getIntent().getExtras().getInt("conversation");
        SharedPreferences sp=getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        String username=sp.getString("session_username","");

        try {
            chat_messages=new getClientMessagesDao().execute(username).get();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        ChatAdapter adapter=new ChatAdapter();
        chatmessages.setAdapter(adapter);

        chatmessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*Intent intent=new Intent(getApplicationContext(),PrivateConversation.class);
                intent.putExtra("client",chat_messages.get(position).getText());
                startActivity(intent);*/

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String text=message.getText().toString();
                    //String error;
                    current_message=new SendChatMessageDao().execute(Integer.toString(chat_messages.get(0).getConversation_id()),text,"1").get();
                    chat_messages.add(current_message);
                    adapter.notifyDataSetChanged();
                    message.setText("");
                    //Log.d("error",error);
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;

}
    private class ChatAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return chat_messages.size();
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
            View view1 = getLayoutInflater().inflate(R.layout.msgs_list,null);
            TextView sender = view1.findViewById(R.id.c_message);
            sender.setText(chat_messages.get(position).getText());
            int flag=chat_messages.get(position).getFlag();
            if(flag==1) {
                sender.setTextColor(Color.parseColor("#ff0000"));
                sender.setGravity(Gravity.RIGHT);
            }
            else
                sender.setTextColor(Color.parseColor("#00ff00"));
            //title.setText(messages.get(i).getTitle());
            return view1;
        }
    }


}
