package com.aad.databaseforaad.without_room;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aad.databaseforaad.without_room.DatabaseSchema.EmployeeEntry;
import com.aad.databaseforaad.without_room.DatabaseSchema.EmployeeEntry2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahim on 3/10/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "employee.db";

    private static final String ADD_NEW_COLUMN = "ALTER TABLE " + EmployeeEntry.TABLE_NAME + " ADD COLUMN " +
            EmployeeEntry.COLUMN_EMPLOYEE_ADDRESS + " TEXT";

    // for updating a extra column

    /*
    *  Query of create a table
    * */

    private static final String CREATE_TABLE = "CREATE TABLE " + EmployeeEntry.TABLE_NAME +
            " (" + EmployeeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EmployeeEntry.COLUMN_EMPLOYEE_NAME + " TEXT," +
            EmployeeEntry.COLUMN_EMPLOYEE_AGE + " INTEGER," +
            EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION + " TEXT)";

    /*
    * Query of creating another table
    * */

    private static final String CREATE_TABLE2 = "CREATE TABLE " + EmployeeEntry2.TABLE_NAME +
            " (" + EmployeeEntry2._ID + " INTEGER PRIMARY KEY," +
            EmployeeEntry2.COLUMN_EMPLOYEE_SALARY + " INTEGER)";

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
        db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(ADD_NEW_COLUMN);
            case 2:
                db.execSQL(CREATE_TABLE2);

        }


       /* db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);*/
    }



    /*
    *  insert information in database
    * */

    public long insertNewItem(String name, int age, String designation, String address) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_NAME, name);
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_AGE, age);
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION, designation);
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_ADDRESS, address);

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
                EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION,
                EmployeeEntry.COLUMN_EMPLOYEE_ADDRESS
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
                EmployeeEntry._ID,
                EmployeeEntry.COLUMN_EMPLOYEE_ADDRESS
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

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String designation = cursor.getString(1);
            int id = cursor.getInt(2);

            employeeModels.add(new EmployeeModel(name, designation, id));
        }

        return employeeModels;

    }

    /*
    *  Delete an entry
    * */

    public int deleteItem(int id) {
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

    public int updateItem(int id, String name, int age, String designation, String address) {
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_NAME, name);
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_AGE, age);
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION, designation);
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_ADDRESS, address);

        // Which row to update, based on the title
        String selection = EmployeeEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        int count = db.update(
                EmployeeEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public class FeedReaderDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "FeedReader.db";

        public FeedReaderDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }


    /*
    *
    * For table 2 information
    * */


    // insert

    public long insertSlary(int id, int salary) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(EmployeeEntry2._ID, id);
        values.put(EmployeeEntry2.COLUMN_EMPLOYEE_SALARY, salary);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(EmployeeEntry2.TABLE_NAME, null, values);
        return newRowId;
    }

    // read

    public Cursor readSalary(int id) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database

        String[] projection = {
                EmployeeEntry2.COLUMN_EMPLOYEE_SALARY,
        };

        String selection = EmployeeEntry2._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                EmployeeEntry2.TABLE_NAME,   // The table to query
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
    *  Read all data from both table
    * */

    public Cursor readAllData(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String READ_DATA = "SELECT " + EmployeeEntry.TABLE_NAME + "." + EmployeeEntry.COLUMN_EMPLOYEE_NAME + "," +
                EmployeeEntry.TABLE_NAME + "." + EmployeeEntry.COLUMN_EMPLOYEE_AGE + "," +
                EmployeeEntry.TABLE_NAME + "." + EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION + "," +
                EmployeeEntry.TABLE_NAME + "." + EmployeeEntry.COLUMN_EMPLOYEE_ADDRESS + "," +
                EmployeeEntry2.TABLE_NAME + "." + EmployeeEntry2.COLUMN_EMPLOYEE_SALARY +
                " FROM " + EmployeeEntry.TABLE_NAME + " INNER JOIN " +
                EmployeeEntry2.TABLE_NAME + " ON " + EmployeeEntry.TABLE_NAME + "." + EmployeeEntry._ID + " = " +
                EmployeeEntry2.TABLE_NAME + "." + EmployeeEntry2._ID + " WHERE " +
                EmployeeEntry.TABLE_NAME + "." + EmployeeEntry2._ID + " = ?";

        Cursor cursor = db.rawQuery(READ_DATA, new String[]{String.valueOf(id)});
        return cursor;
    }

}
