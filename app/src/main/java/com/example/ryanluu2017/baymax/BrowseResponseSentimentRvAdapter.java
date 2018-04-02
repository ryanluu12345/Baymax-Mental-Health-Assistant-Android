package com.example.ryanluu2017.baymax;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BrowseResponseSentimentRvAdapter extends RecyclerView.Adapter<BrowseResponseSentimentRvAdapter.UserViewHolder> {

    //Initializes responses
    ArrayList<Responses> responses;


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        //Initializes UI variables
        private CardView mResponseCv;
        private TextView mResponseTv;

        UserViewHolder(View itemView) {

            super(itemView);
            mResponseCv = (CardView) itemView.findViewById(R.id.response_cv);
            mResponseTv=(TextView) itemView.findViewById(R.id.response_tv);

        }

    }

    //Constructor
    public BrowseResponseSentimentRvAdapter(ArrayList<Responses> responses) {

        this.responses=responses;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.response_card,viewGroup,false); UserViewHolder mUvh=new UserViewHolder(v);
        return mUvh;

    }

    public void onBindViewHolder(UserViewHolder holder, int pos) {

        //Sets the response
        holder.mResponseTv.setText(responses.get(pos).getResponseText()); //Make the array list of objects in android for response
        holder.mResponseTv.setTag(responses.get(pos).getItemId()); //Make the array list of objects in android for uid7

        //Acquires color based on sentiment
        String sentColor=getSentimentColor(responses.get(pos).getSentiment());

        //Sets background based on sentiment
        holder.mResponseCv.setCardBackgroundColor(Color.parseColor(sentColor));

        //Sets onClickListener to each holder element
        //TODO Reset later and an on click method
        //holder.mUserCv.setOnClickListener(new SimpleBrowseOnClickListener());

    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    /* Helper functions determine the color change based on sentiment*/
    private String getSentimentColor(String sentiment){

        switch(sentiment){

            case "Strongly Positive":
                Log.i("color","dgreen");
                return "#006400";

            case "Positive":
                Log.i("color","green");
                return "#32CD32";

            case "Mildly Positive":
                Log.i("color","lgreen");
                return "#90EE90";

            case "Strongly Negative":
                Log.i("color","dred");
                return "#8B0000";

            case "Negative":
                Log.i("color","red");
                return "#FF0000";

            case "Mildly Negative":
                Log.i("color","lred");
                return "#ff7f7f";
        }

        return "000000";

    }



}
