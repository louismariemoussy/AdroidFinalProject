package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


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
            int id =cursor.getColumnIndex(myDbHelper.UID);
            //String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            buffer.append( id);
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
    public long insertRDV(String title, String start_date, String end_date, boolean family,  int creator, String description)
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

        for(int t =0; t<userID.size();t++){
            contentValues.put(myDbHelper.RDV_ID,rdvID);
            contentValues.put(myDbHelper.USER_ID,userID.get(t).toString());
            long id = dbb.insert(myDbHelper.TABLE_NAME_LINK, null , contentValues);
            ids.add(id);
            contentValues.clear();
        }



        return ids;
    }


    //-----------------------------------RDV--------------------------------------------------------
    public String getRDVbyID(int id)
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

    public String getAllRDV()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.TITLE, myDbHelper.START_DATE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME_RDV,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {

            String title =cursor.getString(cursor.getColumnIndex(myDbHelper.TITLE));
            String  sdate =cursor.getString(cursor.getColumnIndex(myDbHelper.START_DATE));
            buffer.append( "Title: " + title + "  Start: " + sdate +" \n");
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
        private static final String CREATE_TABLE2 = "CREATE TABLE "+TABLE_NAME_RDV+
                " ("+RID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CREATOR_ID+" NUMBER ,"+ TITLE+" VARCHAR(225),"+ START_DATE+" DATETIME,"+ END_DATE+" DATETIME,"+OBJECT+" BOOLEAN,"+DESCR+" VARCHAR(225));";
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
