package com.example.finalproject;
// ViewHolder code for RecyclerView

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * The ViewHolder is a java class that stores the reference to the card layout views that have to be dynamically modified during the execution of the program by a list of data obtained either by online databases or added in some other way.
 *
 */

class rdvViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView rdv_people;
    TextView rdv_start_date;
    TextView rdv_start_time;
    TextView rdv_end_date;
    TextView rdv_end_time;
    TextView rdv_note;

    View view;

    rdvViewHolder(View itemView)
    {
        super(itemView);
        title
                = (TextView)itemView
                .findViewById(R.id.rdv_title);
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
        view  = itemView;
    }

}
