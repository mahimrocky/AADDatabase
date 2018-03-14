package com.aad.databaseforaad.with_room;

import android.content.Context;

/**
 * Created by w3E17 on 3/12/2018.
 */

public class DatabaseHelper {

    public static EmployeeDataService employeeDataService(Context context){

        AppDatabase appDatabase = AppDatabase.onInstance(context);

        return EmployeeDataService.onInstance(appDatabase.employeeDao());
    }
}
