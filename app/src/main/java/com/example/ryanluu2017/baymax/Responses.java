package com.example.ryanluu2017.baymax;

public class Responses {

    private String itemId;
    private String sentiment;
    private String responseText;
    private String timestamp;

    public Responses(String itemId, String itemSentiment, String responseText,String timestamp){

        this.itemId=itemId;
        this.sentiment=itemSentiment;
        this.responseText=responseText;
        this.timestamp=timestamp;

    }

    public Responses(){


    }

    public String getItemId(){
        return this.itemId;
    }

    public String getSentiment(){
        return this.sentiment;
    }

    public String getResponseText(){
        return this.responseText;
    }

    public String getTimestamp(){
        return this.getTimestamp();
    }

    public void setResponseText(String responseText){
        this.responseText=responseText;
    }

    public void setItemId(String itemId){
        this.itemId=itemId;
    }

    public void setSentiment(String sentiment){
        this.sentiment=sentiment;
    }

    public void setTimestamp(String timestamp){
        this.timestamp=timestamp;
    }
}
