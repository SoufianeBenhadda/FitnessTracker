package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fitnesstracker.dao.GetMessagesDao;
import com.example.fitnesstracker.dao.SendChatMessageDao;
import com.example.fitnesstracker.model.ChatMessage;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PrivateConversation extends AppCompatActivity {
    ListView chatmessages;
    Button send;
    EditText message;
    List<ChatMessage> chat_messages;
    int conversation_id;
    ChatMessage current_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_conversation);

        chatmessages=findViewById(R.id.chat);
        chatmessages.setDivider(null);
        send=findViewById(R.id.button7);
        message=findViewById(R.id.chat_type);

        conversation_id=getIntent().getExtras().getInt("conversation");

        try {
            chat_messages=new GetMessagesDao().execute(conversation_id).get();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        ChatAdapter adapter=new ChatAdapter();
        chatmessages.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String text=message.getText().toString();
                    //String error;
                    current_message=new SendChatMessageDao().execute(Integer.toString(conversation_id),text,"0").get();
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

    }

    private class ChatAdapter extends BaseAdapter{
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
            //View view1 = getLayoutInflater().inflate(R.layout.msgs_list_sender,null);
            View view1;
            TextView message;
            int flag=chat_messages.get(position).getFlag();
            if(flag==0) {
                view1 = getLayoutInflater().inflate(R.layout.msgs_list_sender,null);
                message = view1.findViewById(R.id.c_message1);
                message.setText(chat_messages.get(position).getText());
            }
            else{
                view1 = getLayoutInflater().inflate(R.layout.msg_list_receiver,null);
                message = view1.findViewById(R.id.c_message2);
                message.setText(chat_messages.get(position).getText());
            }
            return view1;
        }
    }
}