package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

import com.example.fitnesstracker.model.ChatMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GetMessagesDao extends AsyncTask<Integer, Void, List<ChatMessage>> {


    @Override
    protected List<ChatMessage> doInBackground(Integer... params) {

        List<ChatMessage> messages=new ArrayList<ChatMessage>();
        Integer conversation_id=params[0];
        try {



            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql4.freemysqlhosting.net:3306/sql4461783",
                    "sql4461783", "57Jn33Hwd4");

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chatmessage where conversation_id=?");
            statement.setInt(1,conversation_id);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ChatMessage msg=new ChatMessage();
                msg.setId(rs.getInt("id"));
                msg.setText(rs.getString("text"));
                msg.setConversation_id(rs.getInt("conversation_id"));
                msg.setFlag(rs.getInt("flag"));
                messages.add(msg);
            }


            connection.close();

        }
        catch(Exception e)
        {
            //error = e.toString();
        }

        return messages;
    }
}