package com.aad.databaseforaad.without_room;

import android.provider.BaseColumns;

/**
 * Created by Mahim on 3/10/2018.
 */

public class DatabaseSchema {
    /*
    * To prevent someone from accidentally instantiating the contract class,
    * We have to crate a private constructor
    * */

    private DatabaseSchema(){}

    /*
    *  Below we have to define our table and different fields
    *
    *  for this we have to create an inner class to define elements
    * */

    public static class EmployeeEntry implements BaseColumns{

        public static final String TABLE_NAME = "employee";
        public static final String COLUMN_EMPLOYEE_NAME = "name";
        public static final String COLUMN_EMPLOYEE_AGE = "age";
        public static final String COLUMN_EMPLOYEE_DESIGNATION = "designation";

    }




}
