package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new myDbAdapter(this);
        String name = helper.getName();
        //get user id from the login activity click
        int user_id_list = getIntent().getIntExtra("user_list_id", 0);//ID for the spinner






        //long id = helper.insertData("LM","0672706509");
        //if(id<=0)
        //{
        //    Message.message(getApplicationContext(),"Insertion Unsuccessful");
        //} else
        //{
        //    Message.message(getApplicationContext(),"Insertion Successful");
        //}

        //Drop down
        Spinner listeDeroulante = (Spinner)findViewById(R.id.liste_deroulante);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                (name.replaceAll("\\s+$", "")).split(" "));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeDeroulante.setAdapter(adapter);

        //Set the value on the user id from login activity
        listeDeroulante.setSelection(user_id_list);

        //Message.message(getApplicationContext(),listeDeroulante.getItemAtPosition(user_id_list).toString().trim());
        int user_id_db = Integer.parseInt(helper.getIdByName(listeDeroulante.getItemAtPosition(user_id_list).toString().trim()));
        Message.message(getApplicationContext(),"user id db"+helper.getIdByName(listeDeroulante.getItemAtPosition(user_id_list).toString().trim()));


        //btn +
        Button monBouton = findViewById(R.id.btnplus);
        PopupMenu monMenuPopup = new PopupMenu(this, monBouton);

        MenuInflater convertisseur = monMenuPopup.getMenuInflater();
        convertisseur.inflate(R.menu.pop_up, monMenuPopup.getMenu());
        monBouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("user_id", user_id_db);
                extras.putString("user_name",listeDeroulante.getItemAtPosition(user_id_list).toString().trim());

                Intent i = new Intent(getApplicationContext(),RdvActivity.class).putExtras(extras);
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
                                                      Toast.makeText(getApplicationContext(), date + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                                                  }
                                              });





        String rdv = helper.getAllRDV();

        //Toast.makeText(MainActivity.this, "" +rdv, Toast.LENGTH_LONG).show();

        String links = helper.getAllLink();

        Toast.makeText(MainActivity.this, "" +links, Toast.LENGTH_SHORT).show();


        }



    }






