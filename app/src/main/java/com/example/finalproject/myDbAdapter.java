package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.CaseMap;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    //-----------------------------------USER-------------------------------------------------------
    public long insertData(String name, String phone)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.PHONE, phone);
        long id = dbb.insert(myDbHelper.TABLE_NAME_USER, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.NAME, myDbHelper.PHONE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME_USER,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String  phone =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            buffer.append(cid+ "   " + name + "   " + phone +" \n");
        }
        return buffer.toString();
    }
    public String getName()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.NAME};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME_USER,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            //int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            //String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            buffer.append( name +" \n");
        }
        return buffer.toString();
    }

    public String getPhonebyID(int id)
    {

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.PHONE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME_USER,columns, myDbHelper.UID+" like "+id, null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            //int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String phone =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            //String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            buffer.append( phone +" \n");
        }
        return buffer.toString();
    }

    public String getPhoneByName(String name)
    {

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.PHONE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME_USER,columns, myDbHelper.NAME+" = '"+name+"';", null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            //int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String phone =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            //String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            buffer.append(phone);
        }
        return buffer.toString();
    }


    public String getIdByName(String name)
    {

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME_USER,columns, myDbHelper.NAME+" = '"+name+"';", null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            //int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            int idu =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));//https://stackoverflow.com/questions/16832401/sqlite-auto-increment-not-working
            String sidu = Integer.toString(idu);
            //String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            buffer.append(idu);
        }
        return buffer.toString();
    }

    public String getNameById(int id)
    {

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.NAME};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME_USER,columns, myDbHelper.UID+" = '"+id+"';", null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            //int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));//https://stackoverflow.com/questions/16832401/sqlite-auto-increment-not-working

            //String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            buffer.append(name);
        }
        return buffer.toString();
    }

    public  int delete(int id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={String.valueOf(id)};
        int count =db.delete(myDbHelper.TABLE_NAME_USER , myDbHelper.UID+" = ?",whereArgs);
        return  count;
    }

    public  int deleteByName(String Name)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={Name};
        int count =db.delete(myDbHelper.TABLE_NAME_USER , myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME_USER,contentValues, myDbHelper.NAME+" = ?",whereArgs );
        return count;
    }

    public int updateUser(int id, String newName, String newPhone)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (newName.length() != 0){
            if (newPhone.length() != 0){//both must be modify
                contentValues.put(myDbHelper.NAME,newName);
                contentValues.put(myDbHelper.PHONE,newPhone);
                String[] whereArgs= {String.valueOf(id)};
                int count =db.update(myDbHelper.TABLE_NAME_USER,contentValues, myDbHelper.UID+" = ?",whereArgs );
                return count;
            } else{//only name have to be modify
                contentValues.put(myDbHelper.NAME,newName);
                //contentValues.put(myDbHelper.PHONE,newPhone);
                String[] whereArgs= {String.valueOf(id)};
                int count =db.update(myDbHelper.TABLE_NAME_USER,contentValues, myDbHelper.UID+" = ?",whereArgs );
                return count;
            }
        }else{//only phone have to be modify
            //contentValues.put(myDbHelper.NAME,newName);
            contentValues.put(myDbHelper.PHONE,newPhone);
            String[] whereArgs= {String.valueOf(id)};
            int count =db.update(myDbHelper.TABLE_NAME_USER,contentValues, myDbHelper.UID+" = ?",whereArgs );
            return count;
        }

        //Log.i("update","user table");//ds la console


    }

    //---------ADD AN EVENT-------------------------------------------------------------------------
    public long insertRDV(String title, String start_date, String end_date, boolean family,  int creator, String description, String people)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValuesRDV = new ContentValues();



        //RDV TABLE DATA
        contentValuesRDV.put(myDbHelper.CREATOR_ID,creator);
        contentValuesRDV.put(myDbHelper.START_DATE,start_date);
        contentValuesRDV.put(myDbHelper.END_DATE,end_date);
        contentValuesRDV.put(myDbHelper.OBJECT,family);
        contentValuesRDV.put(myDbHelper.DESCR,description);
        contentValuesRDV.put(myDbHelper.TITLE,title);
        contentValuesRDV.put(myDbHelper.PEOPLE, people);
        //contentValues.put(myDbHelper., );

        long id = dbb.insert(myDbHelper.TABLE_NAME_RDV, null , contentValuesRDV);

        return id;
    }

    public ArrayList createLINK(int rdvID, ArrayList userID)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ArrayList ids = new ArrayList();

        //contentValues.put(myDbHelper., );
        //LINK TABLE DATA

        for(int t =0; t < userID.size(); t++){
            contentValues.put(myDbHelper.RDV_ID,rdvID);
            contentValues.put(myDbHelper.USER_ID,userID.get(t).toString());
            long id = dbb.insert(myDbHelper.TABLE_NAME_LINK, null , contentValues);
            ids.add(id);
            contentValues.clear();
        }



        return ids;
    }

    public int IsThisNameAlreadyTaken(String name)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int available = 0;



        String[] columns = {myDbHelper.NAME};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME_USER,columns, myDbHelper.NAME+" = '"+name+"';", null,null,null,null);


        Log.d("test add user adapter", "IsThisNameAlreadyTaken: "+ cursor.getCount());

                if (cursor.getCount() == 0){
                        available=1;
                }



            return available;//0= not available and 1 is available, should I use boolean ?


        //Log.i("Available ?",count);//ds la console


    }


    //-----------------------------------RDV--------------------------------------------------------

    public String getAllRDV()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.TITLE, myDbHelper.START_DATE, myDbHelper.RID, myDbHelper.CREATOR_ID, myDbHelper.END_DATE, myDbHelper.OBJECT, myDbHelper.DESCR, myDbHelper.PEOPLE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME_RDV,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        //buffer.append(cursor.getColumnCount());
        while (cursor.moveToNext())
        {

            String title =cursor.getString(cursor.getColumnIndex(myDbHelper.TITLE));
            String  sdate =cursor.getString(cursor.getColumnIndex(myDbHelper.START_DATE));
            String rid =cursor.getString(cursor.getColumnIndex(myDbHelper.RID));
            String cid =cursor.getString(cursor.getColumnIndex(myDbHelper.CREATOR_ID));
            String edate =cursor.getString(cursor.getColumnIndex(myDbHelper.END_DATE));
            String obj =cursor.getString(cursor.getColumnIndex(myDbHelper.OBJECT));
            String des =cursor.getString(cursor.getColumnIndex(myDbHelper.DESCR));
            String people =cursor.getString(cursor.getColumnIndex(myDbHelper.PEOPLE));


            buffer.append( "Title: " + title +  "  RDV id: " + rid + "  Creator: " + cid  +" \n" + "  Start: " + sdate + "  End: " + edate +" \n" + "  Object: " + obj + "  Description: " + des  +" \n"+ "  People: " + people  +" \n");
        }
        return buffer.toString();
    }

    //-----------------------------------LINK-------------------------------------------------------

    public String getAllLink()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.RDV_ID, myDbHelper.USER_ID};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME_LINK,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {

            String rid =cursor.getString(cursor.getColumnIndex(myDbHelper.RDV_ID));
            String  uid =cursor.getString(cursor.getColumnIndex(myDbHelper.USER_ID));
            buffer.append( "RDV id: " + rid + "  User id: " + uid +" \n");
        }
        return buffer.toString();
    }

    public List<ArrayList<String>> getRDVwithUserID(int user_id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        String query = "SELECT "+ myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.TITLE +", "+myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.START_DATE +", " +myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.END_DATE +", " + myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.OBJECT +", " +myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.DESCR +", " +myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.PEOPLE +" FROM "+ myDbHelper.TABLE_NAME_RDV+" INNER JOIN "+myDbHelper.TABLE_NAME_LINK+" ON "+myDbHelper.TABLE_NAME_LINK+"."+myDbHelper.RDV_ID+"="+myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.RID+" WHERE "+myDbHelper.TABLE_NAME_LINK+"."+myDbHelper.USER_ID+"="+user_id;
        Cursor cursor = db.rawQuery( query,null);
        StringBuffer buffer= new StringBuffer();
        List<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
        ArrayList<String> TitleList = new ArrayList<String>();
        ArrayList<String> sDateList = new ArrayList<String>();
        ArrayList<String> eDateList = new ArrayList<String>();
        ArrayList<String> ObjList = new ArrayList<String>();
        ArrayList<String> DesList = new ArrayList<String>();
        ArrayList<String> PeopleList = new ArrayList<String>();
        while (cursor.moveToNext())
        {

            String title =cursor.getString(cursor.getColumnIndex(myDbHelper.TITLE));
            String sDate =cursor.getString(cursor.getColumnIndex(myDbHelper.START_DATE));
            String eDate =cursor.getString(cursor.getColumnIndex(myDbHelper.END_DATE));
            String Obj =cursor.getString(cursor.getColumnIndex(myDbHelper.OBJECT));
            String Des =cursor.getString(cursor.getColumnIndex(myDbHelper.DESCR));
            String People =cursor.getString(cursor.getColumnIndex(myDbHelper.PEOPLE));

            TitleList.add(title);
            sDateList.add(sDate);
            eDateList.add(eDate);
            ObjList.add(Obj);
            DesList.add(Des);
            PeopleList.add(People);
        }
        listOfLists.add(TitleList);
        listOfLists.add(sDateList);
        listOfLists.add(eDateList);
        listOfLists.add(ObjList);
        listOfLists.add(DesList);
        listOfLists.add(PeopleList);

        return listOfLists;
    }

    public List<ArrayList<String>> getRDVwithUserIdAndDate(int user_id,String date)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        String query = "SELECT "+ myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.TITLE +", "+myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.START_DATE +", " +myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.END_DATE +", " + myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.OBJECT +", " +myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.DESCR +", " +myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.PEOPLE +" FROM "+ myDbHelper.TABLE_NAME_RDV+" INNER JOIN "+myDbHelper.TABLE_NAME_LINK+" ON "+myDbHelper.TABLE_NAME_LINK+"."+myDbHelper.RDV_ID+"="+myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.RID+" WHERE "+myDbHelper.TABLE_NAME_LINK+"."+myDbHelper.USER_ID+"="+user_id+" AND "+myDbHelper.TABLE_NAME_RDV+"."+myDbHelper.START_DATE+" LIKE \'"+date+"%\'";
        Cursor cursor = db.rawQuery( query,null);
        StringBuffer buffer= new StringBuffer();
        List<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
        ArrayList<String> TitleList = new ArrayList<String>();
        ArrayList<String> sDateList = new ArrayList<String>();
        ArrayList<String> eDateList = new ArrayList<String>();
        ArrayList<String> ObjList = new ArrayList<String>();
        ArrayList<String> DesList = new ArrayList<String>();
        ArrayList<String> PeopleList = new ArrayList<String>();
        while (cursor.moveToNext())
        {

            String title =cursor.getString(cursor.getColumnIndex(myDbHelper.TITLE));
            String sDate =cursor.getString(cursor.getColumnIndex(myDbHelper.START_DATE));
            String eDate =cursor.getString(cursor.getColumnIndex(myDbHelper.END_DATE));
            String Obj =cursor.getString(cursor.getColumnIndex(myDbHelper.OBJECT));
            String Des =cursor.getString(cursor.getColumnIndex(myDbHelper.DESCR));
            String People =cursor.getString(cursor.getColumnIndex(myDbHelper.PEOPLE));

            TitleList.add(title);
            sDateList.add(sDate);
            eDateList.add(eDate);
            ObjList.add(Obj);
            DesList.add(Des);
            PeopleList.add(People);
        }
        listOfLists.add(TitleList);
        listOfLists.add(sDateList);
        listOfLists.add(eDateList);
        listOfLists.add(ObjList);
        listOfLists.add(DesList);
        listOfLists.add(PeopleList);

        return listOfLists;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final int DATABASE_Version = 1;    // Database Version

        //USER TABLE
        private static final String TABLE_NAME_USER = "USER";   // Table Name
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String PHONE= "Phone";    // Column III
        private static final String CREATE_TABLE1 = "CREATE TABLE "+TABLE_NAME_USER+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ PHONE+" VARCHAR(225));";
        private static final String DROP_TABLE_USER ="DROP TABLE IF EXISTS "+TABLE_NAME_USER;

        //RDV TABLE
        private static final String TABLE_NAME_RDV = "RDV";   // Table Name
        private static final String RID="_id";     // Column I (Primary Key)
        private static final String CREATOR_ID = "uid";    //Column II
        private static final String TITLE = "title";    //Column III
        private static final String START_DATE= "startdate";    // Column IV
        private static final String END_DATE= "enddate";    // Column V
        private static final String OBJECT= "object";    // Column VI
        private static final String DESCR= "descr";    // Column VII description
        private static final String PEOPLE= "people";    // Column VIII

        private static final String CREATE_TABLE2 = "CREATE TABLE "+TABLE_NAME_RDV+
                " ("+RID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CREATOR_ID+" NUMBER ,"+ TITLE+" VARCHAR(225),"+ START_DATE+" DATETIME,"+ END_DATE+" DATETIME,"+OBJECT+" BOOLEAN,"+DESCR+" VARCHAR(225),"+PEOPLE+" VARCHAR(225));";
        private static final String DROP_TABLE_RDV ="DROP TABLE IF EXISTS "+TABLE_NAME_RDV;

        //LINK TABLE
        private static final String TABLE_NAME_LINK = "LINK";   // Table Name
        private static final String RDV_ID="rid";     // Column I (Primary Key)
        private static final String USER_ID = "uid";    //Column II
        private static final String CREATE_TABLE3 = "CREATE TABLE "+TABLE_NAME_LINK+
                " ("+RDV_ID+" NUMBER, "+USER_ID+" NUMBER);";
        private static final String DROP_TABLE_LINK ="DROP TABLE IF EXISTS "+TABLE_NAME_LINK;



        private Context context;


        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE1);
                db.execSQL(CREATE_TABLE2);
                db.execSQL(CREATE_TABLE3);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE_USER);
                db.execSQL(DROP_TABLE_RDV);
                db.execSQL(DROP_TABLE_LINK);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}
