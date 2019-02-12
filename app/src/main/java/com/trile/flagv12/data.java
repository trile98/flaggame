package com.trile.flagv12;


import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class data extends SQLiteOpenHelper {

    SQLiteDatabase Mydb;
    private static String DB_NAME="flag.db";
    public String DB_PATH="data/data/com.trile.flagv12/";
    Context context;



    //constructor
    public data (Context context)
    {
        super(context,DB_NAME,null,1);
        this.context=context;
        boolean chckexist = chckdb();


        if (chckexist)
            opendb();
        else
        {
            System.out.println("doesn't exist");
            createdb();
        }

    }

    void createdb()
    {
        this.getWritableDatabase();

        try {
            copydb();
        }

        catch (IOException e) {e.printStackTrace();}
    }

    private void copydb() throws IOException
    {
        AssetManager dirPATH = context.getAssets();
        InputStream myinput = dirPATH.open(DB_NAME);
        String outFileName = DB_PATH+DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);


        byte[] buffer = new byte[1024];
        int length;

        while ((length = myinput.read(buffer))>0)
        {
            myOutput.write(buffer,0,length);
        }

        myOutput.flush();
        myOutput.close();
        myinput.close();
    }

    boolean chckdb()
    {
        boolean chk = false;

        try{
            String Mypath = DB_PATH+DB_NAME;
            File dbfile = new File(Mypath);
            chk=dbfile.exists();}

        catch (SQLiteException e){
            System.out.println("doesn't exist");}

        return chk;
    }

    void opendb()
    {
        String Mypath=DB_PATH+DB_NAME;
        Mydb=SQLiteDatabase.openDatabase(Mypath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public SQLiteDatabase getdb() {return Mydb;}

    public data(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
