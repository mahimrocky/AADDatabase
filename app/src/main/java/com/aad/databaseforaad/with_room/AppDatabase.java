package com.aad.databaseforaad.with_room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Tariqul on 3/12/2018.
 */


@Database(entities = {Employee.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static volatile AppDatabase sInstance;

    public abstract EmployeeDao employeeDao();

    public static AppDatabase onInstance(Context context){

        if (sInstance==null){
            sInstance =  Room.databaseBuilder(context,
                    AppDatabase.class, "AAD_Database").build();
        }
        return sInstance;
    }


}
