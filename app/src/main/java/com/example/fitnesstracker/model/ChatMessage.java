package com.example.fitnesstracker.model;

public class ChatMessage {

    private int id;
    private String text;
    private int conversation_id;
    private int flag;

    public ChatMessage() {
    }

    public ChatMessage(int id, String text,int conversation_id,int flag) {
        this.id = id;
        this.text = text;
        this.conversation_id=conversation_id;
        this.flag=flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
