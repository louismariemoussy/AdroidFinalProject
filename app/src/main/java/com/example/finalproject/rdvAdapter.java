package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class rdvAdapter extends RecyclerView.Adapter<rdvAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<rdvData> itemList;
    // Constructor of the class
    public rdvAdapter(int layoutId, ArrayList<rdvData> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {


        holder.item.setText(itemList.get(listPosition).getTitle());
        //Log.d("OBJ" , "onBindViewHolder: "+itemList.get(listPosition).getRdv_onj());
        if(Integer.parseInt(itemList.get(listPosition).getRdv_onj().trim()) == 1){
            holder.rdv_people.setText("Family");
        }else{
            holder.rdv_people.setText(itemList.get(listPosition).getRdv_people());
        }

        holder.rdv_start_date.setText(itemList.get(listPosition).getRdv_start_date());
        holder.rdv_start_time.setText(itemList.get(listPosition).getRdv_start_time());
        holder.rdv_end_date.setText(itemList.get(listPosition).getRdv_end_date());
        holder.rdv_end_time.setText(itemList.get(listPosition).getRdv_end_time());
        holder.rdv_note.setText(itemList.get(listPosition).getRdv_note());




    }

    // Static inner class to initialize the views of rows
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView item;
        TextView rdv_people;
        TextView rdv_start_date;
        TextView rdv_start_time;
        TextView rdv_end_date;
        TextView rdv_end_time;
        String rdv_obj;
        TextView rdv_note;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            item = (TextView) itemView.findViewById(R.id.rdv_title);
            rdv_people
                    = (TextView)itemView
                    .findViewById(R.id.rdv_people);
            rdv_start_date
                    = (TextView)itemView
                    .findViewById(R.id.rdv_start_date);
            rdv_start_time
                    = (TextView)itemView
                    .findViewById(R.id.rdv_start_time);
            rdv_end_date
                    = (TextView)itemView
                    .findViewById(R.id.rdv_end_date);
            rdv_end_time
                    = (TextView)itemView
                    .findViewById(R.id.rdv_end_time);
            rdv_note
                    = (TextView)itemView
                    .findViewById(R.id.rdv_note);


        }
        @Override
        public void onClick(View view) {

            Log.d("onclick", "onClick " + getLayoutPosition() + " " + item.getText());
            Log.d("onclick", "onClick visibility: "+rdv_note.getVisibility());
            if(rdv_note.getText().length() != 0){
                if(rdv_note.getVisibility() == View.VISIBLE ){
                    rdv_note.setVisibility(View.GONE);
                }else{
                    //rdv_note.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);//add line under text
                    rdv_note.setVisibility(View.VISIBLE);

                }
            }



        }
    }
}
