package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

import com.example.fitnesstracker.model.ChatMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SendChatMessageDao extends AsyncTask<String, Void, ChatMessage> {


    @Override
    protected ChatMessage doInBackground(String... params) {

        ChatMessage message=new ChatMessage();
        String error="";
        try {

            String conversation_id=params[0];
            String text=params[1];
            String flag=params[2];


            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.12:3306/fitness", "user1", "1234");
           // Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11458541", "sql11458541", "KV5M53tUtZ");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11460118", "sql11460118", "nlikDMLg5c");
            String sql="INSERT INTO chatmessage(text,conversation_id,flag)" +
                    " VALUES(?,?,?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,text);
            statement.setInt(2,Integer.parseInt(conversation_id));
            statement.setInt(3,Integer.parseInt(flag));

            statement.executeUpdate();

            message.setText(text);
            message.setConversation_id(Integer.parseInt(conversation_id));

            connection.close();

        }
        catch(Exception e)
        {
            error = e.toString();
        }

        return message;
    }
}
