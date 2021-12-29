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
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql4.freemysqlhosting.net:3306/sql4461783",
                    "sql4461783", "57Jn33Hwd4");


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
