package com.aad.databaseforaad.without_room;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.aad.databaseforaad.R;

public class EmployeeAddActivity extends AppCompatActivity {

    EditText name, age, designation, address, salary;

    boolean isCreate = true; // check for update or create

    private int id;

    private DatabaseHelper databaseHelper;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add);

        name = (EditText) findViewById(R.id.edit_text_name);
        age = (EditText) findViewById(R.id.edit_text_age);
        designation = (EditText) findViewById(R.id.edit_text_designation);
        address = (EditText) findViewById(R.id.edit_text_address);
        salary = (EditText) findViewById(R.id.edit_text_salary);

        databaseHelper = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();

        id = bundle.getInt("id", 0);
        isCreate = bundle.getBoolean("isCreate", true);


        readSingleData();


    }

    /*
    *  read single data
    * */

    private void readSingleData() {

       /* Cursor cursor = databaseHelper.readSingleItem(id);

        if (cursor != null && cursor.moveToNext()) {
            name.setText(cursor.getString(0));
            age.setText(String.valueOf(cursor.getInt(1)));
            designation.setText(cursor.getString(2));
            address.setText(cursor.getString(3));
            cursor.close();
        }
        Cursor cursor2 = databaseHelper.readSalary(id);
        if (cursor2 != null && cursor2.moveToNext()) {
            salary.setText(String.valueOf(cursor2.getInt(0)));
        }*/


        Cursor cursor = databaseHelper.readAllData(id);

        if (cursor != null && cursor.moveToNext()) {
            name.setText(cursor.getString(0));
            age.setText(String.valueOf(cursor.getInt(1)));
            designation.setText(cursor.getString(2));
            address.setText(cursor.getString(3));
            salary.setText(String.valueOf(cursor.getInt(4)));
            cursor.close();
        }
    }


    /*
    *  For inserting data in database we have to call insert method
    *  in database helper
    *
    * */

    private void insertData() {

        if (TextUtils.isEmpty(name.getText())) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(age.getText())) {
            Toast.makeText(this, "Age cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(designation.getText())) {
            Toast.makeText(this, "Designation cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            int salaryAmount = Integer.parseInt(salary.getText().toString());

            // calling method in database helper

            long result = databaseHelper.insertNewItem(name.getText().toString(),
                    Integer.parseInt(age.getText().toString()),
                    designation.getText().toString(), address.getText().toString());

            if (result > 0) {
                long result2 = databaseHelper.insertSlary((int) result, salaryAmount);
                if (result2 > 0) {
                    Toast.makeText(this, "Insert successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

            } else {
                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    *  updating data
    * */

    private void updateData() {

        if (TextUtils.isEmpty(name.getText())) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(age.getText())) {
            Toast.makeText(this, "Age cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(designation.getText())) {
            Toast.makeText(this, "Designation cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            // calling method in database helper

            int result = databaseHelper.updateItem(id, name.getText().toString(),
                    Integer.parseInt(age.getText().toString()),
                    designation.getText().toString(), address.getText().toString());

            if (result > 0) {
                Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*/
    *
    *  Delete an item
    * */

    private void delete() {

        int result = databaseHelper.deleteItem(id);

        if (result > 0) {
            Toast.makeText(this, "Delete successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_ok:
                if (isCreate) {
                    insertData();
                } else {
                    updateData();
                }

                break;
            case R.id.action_delete:
                if (!isCreate) {
                    delete();
                } else {
                    Toast.makeText(this, "You are now creating an item", Toast.LENGTH_SHORT).show();

                }
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }
}
