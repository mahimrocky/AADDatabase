package com.aad.databaseforaad.without_room;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aad.databaseforaad.without_room.DatabaseSchema.EmployeeEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahim on 3/10/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "employee.db";

    /*
    *  Query of create a table
    * */

    private static final String CREATE_TABLE = "CREATE TABLE " + EmployeeEntry.TABLE_NAME +
            " (" + EmployeeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EmployeeEntry.COLUMN_EMPLOYEE_NAME + " TEXT," +
            EmployeeEntry.COLUMN_EMPLOYEE_AGE + " INTEGER," +
            EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION + " TEXT)";

    /*
    * Drop the table if exist
    * */

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EmployeeEntry.TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /*
    *  insert information in database
    * */

    public long insertNewItem(String name, int age, String designation) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_NAME, name);
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_AGE, age);
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION, designation);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(EmployeeEntry.TABLE_NAME, null, values);
        return newRowId;
    }


    /*
    *  Read single item from database
    *
    * */

    public Cursor readSingleItem(int id) {

        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database

        String[] projection = {
                EmployeeEntry.COLUMN_EMPLOYEE_NAME,
                EmployeeEntry.COLUMN_EMPLOYEE_AGE,
                EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION
        };

        String selection = EmployeeEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                EmployeeEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        return cursor;

    }

    /*
    *  Read multiple item from database
    *
    * */

    public List<EmployeeModel> readMultipleItem() {

        SQLiteDatabase db = getReadableDatabase();

        List<EmployeeModel> employeeModels = new ArrayList<>();

        // Define a projection that specifies which columns from the database

        String[] projection = {
                EmployeeEntry.COLUMN_EMPLOYEE_NAME,
                EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION,
                EmployeeEntry._ID
        };



        Cursor cursor = db.query(
                EmployeeEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            String designation = cursor.getString(1);
            int id = cursor.getInt(2);

            employeeModels.add(new EmployeeModel(name,designation,id));
        }

        return employeeModels;

    }

    /*
    *  Delete an entry
    * */

    public int deleteItem(int id){
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = EmployeeEntry._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        int deletedRows = db.delete(EmployeeEntry.TABLE_NAME, selection, selectionArgs);

        return deletedRows;
    }

    /*
    *  Update an entry
    * */

    public int updateItem(int id,String name,int age,String designation){
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_NAME, name);
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_AGE, age);
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION, designation);

        // Which row to update, based on the title
        String selection = EmployeeEntry._ID+ " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(
                EmployeeEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }
}
