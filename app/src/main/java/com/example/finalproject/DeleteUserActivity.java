package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    myDbAdapter helper;
    private int id_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        Button mButton = findViewById(R.id.DeleteOk);

        helper = new myDbAdapter(this);

        String name = helper.getName();

        Spinner choose_user = (Spinner)findViewById(R.id.choose_user);
        choose_user.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                (name.replaceAll("\\s+$", "")).split(" "));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_user.setAdapter(adapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                    Toast.makeText(DeleteUserActivity.this, choose_user.getItemAtPosition(id_selected-1).toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteUserActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Delete");
                builder.setMessage("Goodbye "+choose_user.getItemAtPosition(id_selected-1)+" ?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Delete user

                                helper.deleteByName(choose_user.getItemAtPosition(id_selected-1).toString().trim());
                                startActivity(new Intent(DeleteUserActivity.this, login.class));
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


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
        //String phone = helper.getPhonebyID((int)id);
        //mPhoneView.setText(phone);
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        id_selected=1;

    }
}