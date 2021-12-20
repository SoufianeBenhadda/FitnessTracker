package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

import com.example.fitnesstracker.model.ChatConversation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConversationDao extends AsyncTask<String, Void, List<ChatConversation>> {


    @Override
    protected List<ChatConversation> doInBackground(String... params) {

        List<ChatConversation> convs=new ArrayList<ChatConversation>();
        try {



            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://10.12.131.24:3306/fitness", "user1", "1234");
           // Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11458541", "sql11458541", "KV5M53tUtZ");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11460118", "sql11460118", "nlikDMLg5c");


            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chatconversation");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ChatConversation conv=new ChatConversation();
                conv.setId(rs.getInt("id"));
                conv.setClient(rs.getString("client"));
                convs.add(conv);
            }


            connection.close();

        }
        catch(Exception e)
        {
            //error = e.toString();
        }

        return convs;
    }
}
