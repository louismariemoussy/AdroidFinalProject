package com.example.finalproject;
/*
* The Data class is a custom java class that acts as a structure for holding the information for every item of the RecyclerView.
* */

import java.io.Serializable;

class rdvData implements Serializable {
    private String title;
    private String rdv_people;
    private String rdv_start_date;
    private String rdv_start_time;
    private String rdv_end_date;
    private String rdv_end_time;
    private String rdv_onj;
    private String rdv_note;
    private String owner_name;

    public String getowner_name() {
        return owner_name;
    }

    public void setowner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public rdvData(String title,
                   String rdv_people,
                   String rdv_start_date,
                   String rdv_start_time,
                   String rdv_end_date,
                   String rdv_end_time,
                   String rdv_onj,
                   String rdv_note,
                   String owner_name)
    {
        this.title=title;
        this.rdv_people=rdv_people;
        this.rdv_start_date=rdv_start_date;
        this.rdv_start_time=rdv_start_time;
        this.rdv_end_date=rdv_end_date;
        this.rdv_end_time=rdv_end_time;
        this.rdv_onj=rdv_onj;
        this.rdv_note=rdv_note;
        this.owner_name = owner_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }

    public String getRdv_people() {
        return rdv_people;
    }

    public void setRdv_people(String rdv_people) {
        this.rdv_people = rdv_people;
    }

    public String getRdv_start_date() {
        return rdv_start_date;
    }

    public void setRdv_start_date(String rdv_start_date) {
        this.rdv_start_date = rdv_start_date;
    }

    public String getRdv_start_time() {
        return rdv_start_time;
    }

    public void setRdv_start_time(String rdv_start_time) {
        this.rdv_start_time = rdv_start_time;
    }

    public String getRdv_end_date() {
        return rdv_end_date;
    }

    public void setRdv_end_date(String rdv_end_date) {
        this.rdv_end_date = rdv_end_date;
    }

    public String getRdv_end_time() {
        return rdv_end_time;
    }

    public void setRdv_end_time(String rdv_end_time) {
        this.rdv_end_time = rdv_end_time;
    }

    public String getRdv_onj() {
        return rdv_onj;
    }

    public void setRdv_onj(String rdv_onj) {
        this.rdv_onj = rdv_onj;
    }

    public String getRdv_note() {
        return rdv_note;
    }

    public void setRdv_note(String rdv_note) {
        this.rdv_note = rdv_note;
    }
}
