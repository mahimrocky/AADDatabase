package com.aad.databaseforaad.with_room;

import java.util.List;

/**
 * Created by w3E17 on 3/12/2018.
 */

public class EmployeeDataService {

    private static EmployeeDao employeeDao;
    private  static EmployeeDataService sInstance;

    public static EmployeeDataService onInstance(EmployeeDao dao){
        employeeDao = dao;
        if(sInstance==null){
            sInstance = new EmployeeDataService();
        }

        return sInstance;
    }

    public List<Employee> getAllData(){
        return employeeDao.getAllData();
    }

    public Employee getSingleData(int id){
        return employeeDao.getSingleData(id);
    }

    public long[] insertData(Employee...employees){
        return employeeDao.insertAll(employees);
    }

    public int updateData(Employee employees){
        return employeeDao.updateData(employees);
    }

    public int deleteData(Employee employees){
        return employeeDao.deleteData(employees);
    }
}
