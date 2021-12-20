package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

import com.example.fitnesstracker.model.ChatMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class getClientMessagesDao extends AsyncTask<String, Void, List<ChatMessage>> {


    @Override
    protected List<ChatMessage> doInBackground(String... params) {

        List<ChatMessage> messages=new ArrayList<ChatMessage>();
        String username=params[0];
        try {



            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://10.12.131.24:3306/fitness", "user1", "1234");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11458541", "sql11458541", "KV5M53tUtZ");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11460118", "sql11460118", "nlikDMLg5c");

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chatmessage,chatconversation where " +
                    "client=? and chatmessage.conversation_id=chatconversation.id");
            statement.setString(1,username);

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
