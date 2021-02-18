package com.example.finalproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class login extends ListActivity {

    myDbAdapter helper;


    protected ArrayAdapter<String> myAdaptater;
    private ArrayList<String> UserList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new myDbAdapter(this);


        //long id = helper.insertData("LM","0672706509");
        //if(id<=0)
        //{
        // /   Message.message(getApplicationContext(),"Insertion Unsuccessful");
        //} else
        //{
        //    Message.message(getApplicationContext(),"Insertion Successful");
        //}

        //Option btn
        Button monBouton = findViewById(R.id.option);
        PopupMenu monMenuPopup = new PopupMenu(this, monBouton);
        MenuInflater convertisseur = monMenuPopup.getMenuInflater();
        convertisseur.inflate(R.menu.pop_up2, monMenuPopup.getMenu());
        monBouton.setOnClickListener(new View.OnClickListener() {
                                         public void onClick(View v) {
                                             monMenuPopup.show();
                                             //monMenuPopup.getMenuInflater().inflate(R.menu.pop_up2, monMenuPopup.getMenu());
                                             monMenuPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                                                         @Override
                                                                                         public boolean onMenuItemClick(MenuItem menuItem) {
                                                                                             // Toast message on menu item clicked
                                                                                             //Toast.makeText(login.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                                                                                             switch (menuItem.getTitle().toString() ){
                                                                                                 case "Modify profil":
                                                                                                     startActivity(new Intent(login.this, ModifyActivity.class));
                                                                                                     break;
                                                                                                 case "Add profil":
                                                                                                     startActivity(new Intent(login.this, AddUserActivity.class));
                                                                                                     break;
                                                                                                 case "Delete profil":
                                                                                                     startActivity(new Intent(login.this, DeleteUserActivity.class));
                                                                                                     break;

                                                                                             }
                                                                                             return true;
                                                                                         }
                                                                                     }
                                             );
                                         }
                                     });








        String name = helper.getName();
        String[] AllName = (name.replaceAll("\\s+$", "")).split(" ");




        UserList = new ArrayList<String>();
        for(int i=0; i<AllName.length; i++)
            UserList.add(AllName[i]);

        this.myAdaptater = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, UserList);
        super.setListAdapter(this.myAdaptater);


    }


    // gestion du simple clic:
    public void onListItemClick(ListView liste, View vue, int position, long id) {
        int user_id = (int)id;
        startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("user_list_id", user_id));
    }







}
