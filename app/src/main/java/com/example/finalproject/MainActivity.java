package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    myDbAdapter helper;

    //Recycler view
    private RecyclerView recyclerView;
    private rdvAdapter adapter;
    private List<rdvData> rdvList;
    ArrayList<String> TitleList = new ArrayList<String>();
    ArrayList<String> sDateList = new ArrayList<String>();
    ArrayList<String> eDateList = new ArrayList<String>();
    ArrayList<String> ObjList = new ArrayList<String>();
    ArrayList<String> DesList = new ArrayList<String>();
    ArrayList<String> PeopleList = new ArrayList<String>();
    int user_id_db;
    int user_id_list;
    String clicked_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new myDbAdapter(this);
        String name = helper.getName();
        //get user id from the login activity click
        user_id_list = getIntent().getIntExtra("user_list_id", 0);//ID for the spinner









        //long id = helper.insertData("LM","0672706509");
        //if(id<=0)
        //{
        //    Message.message(getApplicationContext(),"Insertion Unsuccessful");
        //} else
        //{
        //    Message.message(getApplicationContext(),"Insertion Successful");
        //}

        //Drop down
        Spinner listeDeroulante = (Spinner) findViewById(R.id.liste_deroulante);
        listeDeroulante.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                (name.replaceAll("\\s+$", "")).split(" "));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeDeroulante.setAdapter(adapter);

        //Set the value on the user id from login activity
        listeDeroulante.setSelection(user_id_list);

        //Message.message(getApplicationContext(),listeDeroulante.getItemAtPosition(user_id_list).toString().trim());
         user_id_db = Integer.parseInt(helper.getIdByName(listeDeroulante.getItemAtPosition(user_id_list).toString().trim()));
        //Message.message(getApplicationContext(), "user id db" + helper.getIdByName(listeDeroulante.getItemAtPosition(user_id_list).toString().trim()));

        java.util.Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        clicked_date = df.format(c);




        //btn +
        FloatingActionButton monBouton = findViewById(R.id.btnplus);
        PopupMenu monMenuPopup = new PopupMenu(this, monBouton);

        MenuInflater convertisseur = monMenuPopup.getMenuInflater();
        convertisseur.inflate(R.menu.pop_up, monMenuPopup.getMenu());
        monBouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("user_id", user_id_db);
                extras.putString("user_name", listeDeroulante.getItemAtPosition(user_id_list).toString().trim());

                Intent i = new Intent(getApplicationContext(), RdvActivity.class).putExtras(extras);
                startActivity(i);



                //monMenuPopup.show();
                //monMenuPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                //                                            @Override
                //                                            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                //Toast.makeText(login.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();


                //switch (menuItem.getTitle().toString() ){
                //    case "RDV":
                //        //startActivity(new Intent(MainActivity.this, ModifyActivity.class));
                //        Intent i = new Intent(getApplicationContext(),RdvActivity.class);
                //        startActivity(i);
                //        break;
                //    case "Event":
                //        //startActivity(new Intent(login.this, AddUserActivity.class));
                //        break;
                //}
                //return true;
                //}
                //}
                //);
            }


        });


        //-----------------------------------------------CALENDAR VIEW

        CalendarView mCalendarView = findViewById(R.id.calendarView);



        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            //GET CLECKED DATE on the calendar view
            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int date) {
                month = month +1;//I dunnon why but months start at 0
                Toast.makeText(getApplicationContext(), date + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                String StringDay = Integer.toString(date);
                String StringMonth = Integer.toString(month);

                if( StringDay.length() == 1){//if lenght = 1  then add 0 to have 01
                    StringDay = "0"+StringDay;
                }
                if(StringMonth.length() == 1){//if min = 1 then add 0 to have 01
                    StringMonth = "0"+StringMonth; }

                clicked_date = Integer.toString(year) + "-"+StringMonth+"-"+StringDay;
                deployRecyclerView( user_id_db, TitleList, sDateList,  eDateList,  ObjList,  DesList, PeopleList);

            }




        });



        deployRecyclerView( user_id_db, TitleList, sDateList,  eDateList,  ObjList,  DesList, PeopleList);










        //String rdv = helper.getAllRDV();

        //Toast.makeText(MainActivity.this, "" +rdv, Toast.LENGTH_LONG).show();

        String links = helper.getAllLink();
        Log.d("LINK", "onCreate: "+links);
        //Toast.makeText(MainActivity.this, "" + links, Toast.LENGTH_SHORT).show();


    }

    public void deployRecyclerView( int user_id_db,ArrayList<String> TitleList, ArrayList<String> sDateList, ArrayList<String> eDateList, ArrayList<String> ObjList, ArrayList<String> DesList, ArrayList<String> PeopleList){

        /*
         * Recycler view
         * */
        recyclerView = (RecyclerView) findViewById(R.id.RDVrecyclerView);
        rdvList = new ArrayList<rdvData>();
        //adapter = new rdvAdapter(this, rdvList);

        // Initializing list view with the custom adapter
        ArrayList<rdvData> itemList = new ArrayList<rdvData>();

        rdvAdapter rdvAdapter = new rdvAdapter(R.layout.rdv_card, itemList);
        recyclerView = (RecyclerView) findViewById(R.id.RDVrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rdvAdapter);

        //get all rdv of the user
        List<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
        listOfLists = helper.getRDVwithUserIdAndDate(user_id_db,clicked_date);

        TitleList = listOfLists.get(0);
        sDateList = listOfLists.get(1);
        eDateList = listOfLists.get(2);
        ObjList = listOfLists.get(3);
        DesList = listOfLists.get(4);
        PeopleList = listOfLists.get(5);




        String[] sDate;
        String[] eDate;

        // Populating list items
        for (int i = 0; i < TitleList.size(); i++) {
            sDate = sDateList.get(i).split(" ");
            eDate = eDateList.get(i).split( " ");
            //Log.d("yo", "ppclc: " +PeopleList.get(i));
            itemList.add(new rdvData(TitleList.get(i), PeopleList.get(i), sDate[0], sDate[1], eDate[0], eDate[1],ObjList.get(i),DesList.get(i)));
        }

    }

    @Override
    public void onResume() {

        //when i'm back the recycler view is updated
        super.onResume();
        //java.util.Date c = Calendar.getInstance().getTime();
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        //clicked_date = df.format(c);
        deployRecyclerView(user_id_db, TitleList, sDateList, eDateList, ObjList, DesList,PeopleList);


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String name = parent.getItemAtPosition(pos).toString().trim().replace("\n", "").replace("\r", "");
        String user_id_db_spinner = helper.getIdByName(name);
        user_id_db = Integer.parseInt(user_id_db_spinner);
        deployRecyclerView(user_id_db, TitleList, sDateList, eDateList, ObjList, DesList,PeopleList);
        user_id_list = (int)id;





    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback


    }
}










