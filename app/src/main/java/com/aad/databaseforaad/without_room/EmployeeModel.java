package com.aad.databaseforaad.without_room;

/**
 * Created by Mahim on 3/11/2018.
 */

class EmployeeModel {

    private String name;
    private String designation;
    private int id;

    public EmployeeModel(String name, String designation, int id) {
        this.name = name;
        this.designation = designation;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
