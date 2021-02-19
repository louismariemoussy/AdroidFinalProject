package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    myDbAdapter helper;
    private int id_selected;
    private String newName;
    private String newPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        EditText mModifyName = findViewById(R.id.Modify_Name);
        EditText mModifyPhone = findViewById(R.id.Modify_Phone);
        TextView mPhoneView = findViewById(R.id.phoneView);
        Button mButton = findViewById(R.id.ModifyOk);
        //EditText mFilledModifyName = findViewById(R.id.FilledModifyName);











        helper = new myDbAdapter(this);

        String data = helper.getData();

        //long id = helper.insertData("LM","0672706509");
        //if(id<=0)
        //{
        //    Message.message(getApplicationContext(),"Insertion Unsuccessful");
        //} else
        //{
        //    Message.message(getApplicationContext(),"Insertion Successful");
        //}


        //Drop down
        String name = helper.getName();
        //id=1;



        //Message.message(this,name);
        Spinner choose_user = (Spinner)findViewById(R.id.choose_user);
        choose_user.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.simple_list_item_1_custom,
                (name.replaceAll("\\s+$", "")).split(" "));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_user.setAdapter(adapter);

        String phone = helper.getPhoneByName(choose_user.getItemAtPosition((id_selected)).toString());
        mPhoneView.setText(phone);




        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 newName = mModifyName.getText().toString();
                 newPhone = mModifyPhone.getText().toString();

                 //helper.updateName("LM",newName);
                 if (newName.length() !=0 || newPhone.length() !=0 ){
                     helper.updateUser(id_selected, newName, newPhone);
                     Toast.makeText(ModifyActivity.this, "ID: " + id_selected, Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(ModifyActivity.this, login.class));
                 }

            }
        });





    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        TextView mPhoneView = findViewById(R.id.phoneView);

        //Toast.makeText(ModifyActivity.this, "You Clicked " + parent.getItemAtPosition(pos) + pos, Toast.LENGTH_SHORT).show();
         id = pos+1;
        id_selected = (int)id;
        String name = parent.getItemAtPosition(pos).toString().trim().replace("\n", "").replace("\r", "");
        //Toast.makeText(ModifyActivity.this, name, Toast.LENGTH_SHORT).show();
        String phone = helper.getPhoneByName(name);
        //Toast.makeText(ModifyActivity.this, phone, Toast.LENGTH_SHORT).show();
        mPhoneView.setText(phone);

    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        id_selected=1;

    }


}