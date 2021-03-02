package com.example.finalproject;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
import java.text.SimpleDateFormat;
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
    String sql_PeopleList;

    Date startDate = new Date();
    Date endDate = new Date();
    Boolean sameDay=true;
    String LIST_family_selected[];

     String sql_start_date2;

    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");



    private ArrayList<Integer> AllName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv);

        Calendar calendar = Calendar.getInstance();




        //String date = DateFormat.getDateInstance().format(calendar.getTime());
        String date_init = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        //String time = DateFormat.getDateInstance().format(calendar.getTime());
        String date = format.format(calendar.getTime());
        //Initialize value of startDate
        Log.d("Init start date", date_init + ", " + date);
        startDate.setYear(date.split("/")[0]);
        startDate.setDay(date.split("/")[2]);
        startDate.setMonth(date.split("/")[1]);

        //Initialize value of endDate
        endDate.setYear(date.split("/")[0]);
        endDate.setDay(date.split("/")[2]);
        endDate.setMonth(date.split("/")[1]);

        //get the current date to init sql start and end date
        /*String date_sql[]= DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime()).split("/");//dd/mm/yyyy
        if(date_sql[1].length() == 1){//if month = 1 for january then add 0 to have 01
            date_sql[1] = "0"+date_sql[1];
        }
        if(date_sql[0].length() == 1){//if day = 1 then add 0 to have 01
            date_sql[0] = "0"+date_sql[0];
        }*/


        sql_end_date = endDate.toSQLformat();//yyyy-DD-MM
        sql_start_date = startDate.toSQLformat();//yyyy-DD-MM
        //sql_end_date = endDate.getYear()+"-"+endDate.getDay()+"-"+endDate.getMonth();//yyyy-DD-MM
        //sql_start_date = startDate.getYear()+"-"+startDate.getDay()+"-"+startDate.getMonth();//yyyy-DD-MM







        //https://www.youtube.com/watch?v=Le47R9H3qow

        Bundle extras = getIntent().getExtras();
        int user_id_db = extras.getInt("user_id");
        String user_name = extras.getString("user_name");

        //sql_PeopleList =helper.getNameById(user_id_db);
        Log.d("RDV activity", "User name: "+user_name);
        sql_PeopleList = user_name;

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

         //INIT TIME
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        startDate.setHour(Integer.toString(mHour));
        startDate.setMinute(Integer.toString(mMinute));
        calendar.add(Calendar.HOUR, 1);
        int mHourPlusOne = calendar.get(Calendar.HOUR_OF_DAY);
        endDate.setHour(Integer.toString(mHourPlusOne));
        endDate.setMinute(Integer.toString(mMinute));
        String current_time = "" + mHour+":"+mMinute;
        String current_time_plus_one = "" + mHourPlusOne+":"+mMinute;



        start_date_view.setText(DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));
        end_date_view.setText(DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));
        start_time_view.setText(current_time);
        end_time_view.setText(current_time_plus_one);

        //Transform current time to SQLite DATETIME format HH:mm:ss
        String StringHour = Integer.toString(mHour);
        String StringMinute = Integer.toString(mMinute);
        if( StringHour.length() == 1){//if hour = 1  then add 0 to have 01
            StringHour = "0"+StringHour;
        }
        if(StringMinute.length() == 1){//if minute = 1 then add 0 to have 01
            StringMinute = "0"+StringMinute; }
        sql_start_time = StringHour + ":" + StringMinute+":00";
        //Transform current time to SQLite DATETIME format HH:mm:ss
        String StringHourPlusOne = Integer.toString(mHourPlusOne);
        if( StringHourPlusOne.length() == 1){//if lenght = 1  then add 0 to have 01
            StringHourPlusOne = "0"+StringHourPlusOne;
        }
        sql_end_time = StringHourPlusOne + ":" + StringMinute+":00";





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


        //Toast user id
        //Toast.makeText(RdvActivity.this, "User id "+ user_id_db, Toast.LENGTH_SHORT).show();


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







                    //get id of the new item in RDV TABLE
                    //get if a family event or get all the people
                    //insert in LINK TABLE   createLINK(int rdvID, String userID[])
                    if(fam == true){
                        //get all the  name
                        String name = helper.getName();
                        ArrayList<String> UserList = new ArrayList<>();
                        ArrayList allNameFam = new ArrayList();
                        String allNameString[]=(name.replace('\n', ' ').trim()).split(" ");

                        for(int k = 0; k<allNameString.length;k++)
                            allNameFam.add(allNameString[k]);
                        allNameFam.removeAll(Arrays.asList(null,""));//remove blank
                        //for(int k = 0; k<allNameFam.size();k++)
                        //Toast.makeText(RdvActivity.this, "  allName: "+allNameFam.get(k).toString(), Toast.LENGTH_SHORT).show();



                    //UserList = new ArrayList<String>();
                        //for(int i=0; i<all.length; i++)
                        //    UserList.add(all[i]);
                        //get all the ids
                        sql_PeopleList = "";
                        ArrayList allID = new ArrayList();
                        for(int i=0; i<allNameFam.size(); i++){
                            allID.add(helper.getIdByName(allNameFam.get(i).toString()));
                            if (sql_PeopleList==""){
                                sql_PeopleList = allNameFam.get(i).toString();
                            }else{
                                sql_PeopleList = sql_PeopleList + ", "+allNameFam.get(i).toString();
                            }

                        }

                        allID.removeAll(Arrays.asList(null,""));//remove blank


                        Log.d("TEST", "Test conflit: " + helper.conflit(user_id_db,start,end) );

                        //insert into RDV TABLE
                        long rdvID = helper.insertRDV(Title,start,end,fam,creator,description,sql_PeopleList);//String title, String start_date, String end_date, boolean family,  int creator, String description


                        helper.createLINK((int)rdvID,allID);
                        //Toast.makeText(RdvActivity.this, "rdv id: " + rdvID + "  allID: "+allID.toString(), Toast.LENGTH_LONG).show();

                        Toast.makeText(RdvActivity.this, "RDV created ", Toast.LENGTH_SHORT).show();
                        finish();





                    }else{
                        //get the  selected name
                        ArrayList people_name =new ArrayList();
                        for(int i = 0; i<AllName.size();i++) {
                            people_name.add(nameList[AllName.get(i)]);
                        }
                        //get all the ids
                        ArrayList allID = new ArrayList();
                        ArrayList<String> UserList = new ArrayList<>();

                        //LIST of selected name:
                        //LIST_family_selected

                        allID.add(user_id_db);//add user id
                        for(int i=0; i<people_name.size(); i++)
                            allID.add(helper.getIdByName(LIST_family_selected[i]));

                        allID.removeAll(Arrays.asList(null,""));//remove blank


                        Log.d("TEST", "Test conflit: " + helper.conflit(user_id_db,start,end) );

                        ArrayList conflit = helper.conflit(user_id_db,start,end);
                        if(conflit==null || conflit.size()==0) {//no other event at the same stime
                            //insert into RDV TABLE
                            long rdvID = helper.insertRDV(Title,start,end,fam,creator,description,sql_PeopleList);//String title, String start_date, String end_date, boolean family,  int creator, String description


                            helper.createLINK((int)rdvID,allID);
                            //Toast.makeText(RdvActivity.this, "rdv id: " + rdvID + "  allID: "+allID.toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(RdvActivity.this, "RDV created ", Toast.LENGTH_SHORT).show();

                            finish();

                        } else {

                            Toast.makeText(RdvActivity.this, "You already have an event at this time", Toast.LENGTH_SHORT).show();


                        }





                    }


                }


            }
        });

//

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
                                startDate.setHour(Integer.toString(sHour));
                                startDate.setMinute(Integer.toString(sMinute));
                                start_time_view.setText(startDate.getHour() + ":" + startDate.getMinute());

                                sql_start_time = startDate.transformTime();//Transform selected time to SQLite DATETIME format HH:mm:ss

                                if (timeConflict(endDate,startDate,sameDay)){
                                    sql_end_time = sql_start_time;
                                    end_time_view.setText(endDate.getHour() + ":" + endDate.getMinute());
                                    Log.d("Update date","Yes");
                                }else{
                                    Log.d("Update date","No");
                                }

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
                                endDate.setHour(Integer.toString(sHour));
                                endDate.setMinute(Integer.toString(sMinute));
                                end_time_view.setText(endDate.getHour() + ":" + endDate.getMinute());

                                sql_end_time = endDate.transformTime(); //Transform selected time to SQLite DATETIME format HH:mm:ss

                                if (timeConflict(endDate,startDate,sameDay)){
                                    sql_start_time = sql_end_time;
                                    start_time_view.setText(endDate.getHour() + ":" + endDate.getMinute());
                                    Log.d("Update date","Yes");
                                }else{
                                    Log.d("Update date","No");
                                }

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
                         LIST_family_selected = family_selected.getText().toString().split(",");
                         sql_PeopleList = sql_PeopleList + ", "+ item;

                        //Toast.makeText(RdvActivity.this, "Fam selected: "+LIST_family_selected[0], Toast.LENGTH_SHORT).show();
                        //Toast.makeText(RdvActivity.this, "Fam selected: "+user, Toast.LENGTH_SHORT).show();
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

        if(a==1){//date de début
            start_date_view.setText(selectedDate);

            //Transform selected date to SQLite DATETIME format yyyy-MM-DD
            /*String date[]= DateFormat.getDateInstance(DateFormat.SHORT).format(mCalender.getTime()).split("/");//dd/mm/yyyy
            if(date[1].length() == 1){//if month = 1 for january then add 0 to have 01
                date[1] = "0"+date[1];
            }
            if(date[0].length() == 1){//if day = 1 then add 0 to have 01
                date[0] = "0"+date[0];
            }*/

            //String yDate[] = DateFormat.getDateInstance(DateFormat.FULL).format(mCalender.getTime()).split(", ");;
            String date = format.format(mCalender.getTime());
            startDate.setYear(date.split("/")[0]);
            startDate.setDay(date.split("/")[2]);
            startDate.setMonth(date.split("/")[1]);


            sql_start_date = startDate.toSQLformat();
            //sql_start_date = date[2]+"-"+date[1]+"-"+date[0];;//yyyy-DD-MM
            Log.d("Selected start date", sql_start_date);

            if(dateConflict(endDate,startDate)){
                sql_end_date=sql_start_date;
                end_date_view.setText(selectedDate);
                Log.d("Update date","Yes");
            }else{
                Log.d("Update date","No");
            }

        }else{//date de fin
            end_date_view.setText(selectedDate);

            //Transform selected date to SQLite DATETIME format yyyy-MM-DD
            /*String date[]= DateFormat.getDateInstance(DateFormat.SHORT).format(mCalender.getTime()).split("/");//dd/mm/yyyy
            if(date[1].length() == 1){//if month = 1 for january then add 0 to have 01
                date[1] = "0"+date[1];
            }
            if(date[0].length() == 1){//if day = 1 then add 0 to have 01
                date[0] = "0"+date[0];
            }*/

            //To get the year in format YYYY
            String yDate[] = DateFormat.getDateInstance(DateFormat.FULL).format(mCalender.getTime()).split(", ");;
            Log.d("End date full format", yDate.toString());
            String date = format.format(mCalender.getTime());
            Log.d("End date custom format", date.toString());
            endDate.setYear(date.split("/")[0]);
            endDate.setDay(date.split("/")[2]);
            endDate.setMonth(date.split("/")[1]);


            sql_end_date = endDate.toSQLformat();
            //sql_end_date = date[2]+"-"+date[1]+"-"+date[0];//yyyy-DD-MM
            //Toast.makeText(RdvActivity.this, "SQL end dare "+sql_end_date, Toast.LENGTH_SHORT).show();
            Log.d("Selected end date", sql_end_date);
            if(dateConflict(endDate,startDate)){
                sql_start_date=sql_end_date;
                start_date_view.setText(selectedDate);
                Log.d("Update date","Yes");
            }else{
                Log.d("Update date","No");
            }

        }




    }


    public Boolean dateConflict(Date endDate, Date startDate){//Check if the endDate is < to the startDate for the date
        Log.d("Start date ", startDate.getYear() + ", " + startDate.getMonth() + ", " + startDate.getDay());
        Log.d("End date ", endDate.getYear() + ", " + endDate.getMonth() + ", " + endDate.getDay());
        int endYear = Integer.parseInt(endDate.getYear());
        int endMonth = Integer.parseInt(endDate.getMonth());
        int endDay = Integer.parseInt(endDate.getDay());

        int startYear = Integer.parseInt(startDate.getYear());
        int startMonth = Integer.parseInt(startDate.getMonth());
        int startDay = Integer.parseInt(startDate.getDay());

        if(endYear<startYear){
            return true;
        }else if (endYear==startYear){
            if (endMonth<startMonth){
                return true;
            }else if (endMonth==startMonth){
                if(endDay<startDay){
                    return true;
                }else if(endDay==startDay){
                    sameDay=true;
                    return false;
                }
            }
        }

        return false;
    }

    private Boolean timeConflict(Date endDate, Date startDate, Boolean sameDay){//Check if the endDate is < to the startDate for the time
        Log.d("Endate", endDate.getHour() + "h " + endDate.getMinute() + "min");
        Log.d("Startdate", startDate.getHour() + "h " + startDate.getMinute() + "min");
        Log.d("Sameday", sameDay.toString());

        int endHour = Integer.parseInt(endDate.getHour());
        int endMin = Integer.parseInt(endDate.getMinute());

        int startHour = Integer.parseInt(startDate.getHour());
        int startMin = Integer.parseInt(startDate.getMinute());

        if(sameDay){
            if (endHour<startHour){
                return true;
            }else if (endHour==startHour){
                if (endMin<startMin){
                    return true;
                }else if (endMin==startMin){
                    return false;
                }
            }
        }
        return false;
    }

}