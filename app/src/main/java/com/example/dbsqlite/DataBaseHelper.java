package com.example.dbsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final  String DB_NAME = "mySqliteDataBase";
    public static final  String TAble_NAME = "users";
    public static final  String COLUMN_ID = "id";
    public static final  String COLUMN_USER_NAME = "username";
    public static final  String DB_VERSION= "1";

    public  DataBaseHelper(Context context){
        super(context,DB_NAME,null, Integer.parseInt(DB_VERSION));
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+TAble_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+COLUMN_USER_NAME+" VARCHAR)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = " DROP TABLE IF EXISTS " + TAble_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }

    public  boolean addUser( String name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME,name);
//        sqLiteDatabase.insert(TAble_NAME,null,contentValues);
        return sqLiteDatabase.insert(TAble_NAME,null,contentValues) !=-1;


    }

    public Cursor getUsers(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
         String sql = " SELECT * FROM "+ TAble_NAME + ";";
         return sqLiteDatabase.rawQuery(sql,null);
    }
}
