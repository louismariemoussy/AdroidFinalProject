package com.example.finalproject;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RdvActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button start_date;
    TextView start_date_view;
    TextView end_date_view;
    TimePickerDialog picker;
    myDbAdapter helper;

    a variable = new a();

    String sql_start_date;
    String sql_end_date;
    String sql_start_time;
    String sql_end_time;
    String sql_start;
    String sql_end;



    private ArrayList<Integer> AllName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv);

        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        //String time = DateFormat.getDateInstance().format(calendar.getTime());

        //https://www.youtube.com/watch?v=Le47R9H3qow

        Bundle extras = getIntent().getExtras();
        int user_id_db = extras.getInt("user_id");
        String user_name = extras.getString("user_name");

        //int user_id_db = getIntent().getIntExtra("user_id", 0);//get the user id in the db



        Button start_date = findViewById(R.id.btnSetDate);
        TextView start_date_view = findViewById(R.id.txtStartDate);
        TextView start_time_view = findViewById(R.id.txtStartTime);
        TextView end_date_view = findViewById(R.id.txtEndDate);
        TextView end_time_view = findViewById(R.id.txtEndTime);
        Switch family_switch = findViewById(R.id.Familywitch);
        TextView family_check =findViewById(R.id.FamilyCheck);
        TextView family_selected = findViewById(R.id.FamilySelected);
        EditText title = findViewById(R.id.title_rdv);
        Button add_btn = findViewById(R.id.addBtn);
        EditText descr = findViewById(R.id.description);

        //to get name in the db
        helper = new myDbAdapter(this);
        String name = helper.getName();

         String[] nameList = (name.replaceAll("\\s+$", "")).split(" ");
         List<String> l  = Arrays.asList(nameList);
         AllName = new ArrayList<>();
         boolean[] checkedMembers = new boolean[nameList.length];

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        calendar.add(Calendar.HOUR, 1);
        int mHourPlusOne = calendar.get(Calendar.HOUR_OF_DAY);
        String current_time = "" + mHour+":"+mMinute;
        String current_time_plus_one = "" + mHourPlusOne+":"+mMinute;



        start_date_view.setText(date);
        end_date_view.setText(date);
        start_time_view.setText(current_time);
        end_time_view.setText(current_time_plus_one);

        //https://www.youtube.com/watch?v=eX-TdY6bLdg

        //Create popup
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        //close button
        Button mCloseBTN = findViewById(R.id.CloseBTN);
        mCloseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // ADD IN THE DB
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Si pas de titre -> toast
                if(title.getText().length() == 0 ){
                    Toast.makeText(RdvActivity.this, "Require a title", Toast.LENGTH_SHORT).show();
                }else {
                    //Add to the database

                    //get user id
                    //get all the data
                    sql_start = sql_start_date + " " + sql_start_time;
                    sql_end = sql_end_date + " " + sql_end_time;

                    String Title = title.getText().toString();
                    String start = sql_start;
                    String end = sql_end;
                    boolean fam = family_switch.isChecked();
                    int creator = user_id_db;
                    String description = descr.getText().toString();


                    //insert into RDV TABLE
                    long rdvID = helper.insertRDV(Title,start,end,fam,creator,description);//String title, String start_date, String end_date, boolean family,  int creator, String description
                    //get id of the new item in RDV TABLE
                    //get if a family event or get all the people
                    //insert in LINK TABLE   createLINK(int rdvID, String userID[])
                    if(fam == true){
                        //get all the name
                        String name = helper.getName();
                        ArrayList<String> UserList = new ArrayList<>();
                        String[] all = (name.replaceAll("\\s+$", "")).split(" ");
                        UserList = new ArrayList<String>();
                        for(int i=0; i<all.length; i++)
                            UserList.add(all[i]);
                        //get all the ids
                        ArrayList allID = new ArrayList();
                        for(int i=0; i<UserList.size(); i++)
                            allID.add(helper.getIdByName(UserList.get(i)));

                            helper.createLINK((int)rdvID,allID);




//NEED TO ADD THE AUTHOR IN THE LIST TOO
                    }else{
                        //get the name selected
                        ArrayList people_name =new ArrayList();
                        for(int i = 0; i<AllName.size();i++) {
                            people_name.add(nameList[AllName.get(i)]);
                        }
                        //get all the ids
                        ArrayList allID = new ArrayList();
                        ArrayList<String> UserList = new ArrayList<>();
                        for(int i=0; i<UserList.size(); i++)
                            allID.add(helper.getIdByName(UserList.get(i)));

                        helper.createLINK((int)rdvID,allID);


                    }
                }


            }
        });


        //Open start date selector
        start_date_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                variable.setVar(1);
                com.example.finalproject.DatePicker mDatePicker;
                mDatePicker = new com.example.finalproject.DatePicker();
                mDatePicker.show(getSupportFragmentManager(), "DATE PICK");

                //start_date_view.setText(return_date);



            }
        });

        //Open end date selector
        end_date_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setVar(-1);
                com.example.finalproject.DatePicker mDatePickerEnd;
                mDatePickerEnd = new com.example.finalproject.DatePicker();
                mDatePickerEnd.show(getSupportFragmentManager(), "DATE PICK");
                //end_date_view.setText(return_date);





            }
        });

        //Select start time
        start_time_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(RdvActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                start_time_view.setText(sHour + ":" + sMinute);
                                sql_start_time = sHour + ":" + sMinute;
                            }
                        }, hour, minutes, true);
                picker.show();
            }

    });

        //Select end time
        end_time_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(RdvActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                end_time_view.setText(sHour + ":" + sMinute);
                                sql_end_time = sHour + ":" + sMinute;
                            }
                        }, hour, minutes, true);
                picker.show();
            }

        });


        //when switch is checked
        family_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // do something when check is selected
                    //hide people selection
                    family_check.setVisibility(View.INVISIBLE);
                    family_selected.setVisibility(View.INVISIBLE);
                } else {
                    //do something when unchecked
                    //show people selection
                    family_check.setVisibility(View.VISIBLE);
                    family_selected.setVisibility(View.VISIBLE);
                }
            }

        });

        //cleick on the people btn to select the family member
        //https://www.youtube.com/watch?v=wfADRuyul04
        family_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RdvActivity.this, R.style.alertdialog);
                mBuilder.setTitle("Family member");

                String name2 = name.replace(user_name,"").replaceAll("(?m)^[ \t]*\r?\n", "");
                //Toast.makeText(RdvActivity.this, name2, Toast.LENGTH_SHORT).show();
                String[] nameListNoUser = (name2.replaceAll("\\s+$", "")).split(" ");
                mBuilder.setMultiChoiceItems(nameListNoUser, checkedMembers, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if(isChecked){
                            if(!AllName.contains(which)){
                                AllName.add(which);
                            }else if (AllName.contains(which)){
                                AllName.remove(which);
                            }
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i = 0; i<AllName.size();i++){
                            item = item + nameListNoUser[AllName.get(i)];
                            if(i != AllName.size()-1){
                                item = item + ", ";
                            }
                        }
                        family_selected.setText(item.replace("\n", "").replace("\r", ""));
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        for(int t =0; t<checkedMembers.length;t++){
                            checkedMembers[t]=false;
                            AllName.clear();
                            family_selected.setText("");
                        }
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i =0; i<checkedMembers.length;i++){
                            checkedMembers[i]=false;
                            AllName.clear();
                            family_selected.setText("");
                        }

                                            }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();


            }
        });
    }




    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)

    {


        int a = variable.getVar();


        TextView start_date_view = findViewById(R.id.txtStartDate);
        TextView end_date_view = findViewById(R.id.txtEndDate);
        // Create a Calender instance

        Calendar mCalender = Calendar.getInstance();

        // Set static variables of Calender instance

        mCalender.set(Calendar.YEAR,year);

        mCalender.set(Calendar.MONTH,month);

        mCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        // Get the date in form of string

        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalender.getTime());


        // Set the textview to the selectedDate String

        if(a==1){
            start_date_view.setText(selectedDate);
            sql_start_date = DateFormat.getDateInstance(DateFormat.SHORT).format(mCalender.getTime());//dd/mm/yyyy

        }else{
            end_date_view.setText(selectedDate);
            sql_end_date = DateFormat.getDateInstance(DateFormat.SHORT).format(mCalender.getTime());//dd/mm/yyyy
            //Toast.makeText(RdvActivity.this, "SQL end dare "+sql_end_date, Toast.LENGTH_SHORT).show();


        }



    }




}