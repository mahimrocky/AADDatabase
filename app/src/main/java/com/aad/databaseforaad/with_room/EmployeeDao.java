package com.aad.databaseforaad.with_room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Tariqul on 3/12/2018.
 */


@Dao
public interface EmployeeDao {

    // inserting data

    @Insert(onConflict = REPLACE)
    long[] insertAll(Employee...employees);

    // get all data: Multiple data

    @Query("SELECT * FROM Employee")
    List<Employee> getAllData();

    // get single data

    @Query("SELECT * FROM Employee WHERE id LIKE :id")
    Employee getSingleData(int id);

    // update data

   /* @Query("UPDATE Employee SET employeeName = :name, employeeAge = :age, employeeDesignation = :designation WHERE id = :id")
    long updateUser(String name, int age, String designation, int id);*/

    // delete data

    @Delete
    int deleteData(Employee...employees);

    @Update
    int updateData(Employee...employees);

}
