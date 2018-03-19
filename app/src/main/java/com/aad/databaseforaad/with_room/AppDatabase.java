package com.aad.databaseforaad.with_room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Tariqul on 3/12/2018.
 */


@Database(entities = {Employee.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public static volatile AppDatabase sInstance;

    public abstract EmployeeDao employeeDao();

    private static final String MIGRATION_SQL = "ALTER TABLE  Employee ADD COLUMN address TEXT";

    static final Migration MIGRATION1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(MIGRATION_SQL);
        }
    };

    public static AppDatabase onInstance(Context context){

        if (sInstance==null){
            sInstance =  Room.databaseBuilder(context,
                    AppDatabase.class, "AAD_Database")
                    .addMigrations(MIGRATION1_2)
                    .build();
        }
        return sInstance;
    }


}
