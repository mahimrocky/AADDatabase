package com.aad.databaseforaad.with_room;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.aad.databaseforaad.R;
import com.aad.databaseforaad.with_room.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeAddActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_name)
    EditText name;

    @BindView(R.id.edit_text_age)
    EditText age;

    @BindView(R.id.edit_text_designation)
    EditText designation;


    boolean isCreate = true; // check for update or create

    private int id;

    private Employee employeeModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        id = bundle.getInt("id", 0);
        isCreate = bundle.getBoolean("isCreate", true);


        readSingleData();


    }

    /*
    *  read single data
    * */

    private void readSingleData() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                employeeModel = DatabaseHelper.employeeDataService(EmployeeAddActivity.this).getSingleData(id);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (employeeModel != null) {
                            name.setText(employeeModel.getEmployeeName());
                            age.setText(String.valueOf(employeeModel.getEmployeeAge()));
                            designation.setText(employeeModel.getEmployeeDesignation());
                        }
                    }
                });
            }
        });


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

            // calling method in database helper
            final Employee employee = new Employee();
            employee.setEmployeeName(name.getText().toString());
            employee.setEmployeeAge(Integer.parseInt(age.getText().toString()));
            employee.setEmployeeDesignation(designation.getText().toString());


            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    final long[] result = DatabaseHelper.employeeDataService(EmployeeAddActivity.this).insertData(employee);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result[0] > 0) {
                                Toast.makeText(EmployeeAddActivity.this, "Insert successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EmployeeAddActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

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

            final Employee employee = new Employee();
            employee.setId(id);
            employee.setEmployeeName(name.getText().toString());
            employee.setEmployeeAge(Integer.parseInt(age.getText().toString()));
            employee.setEmployeeDesignation(designation.getText().toString());


            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    final int result = DatabaseHelper.employeeDataService(EmployeeAddActivity.this).updateData(employee);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result > 0) {
                                Toast.makeText(EmployeeAddActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EmployeeAddActivity.this, "An error occurred for updating", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });


        }
    }

    /*/
    *
    *  Delete an item
    * */

    private void delete() {

        final Employee employee = new Employee();
        employee.setId(id);
        employee.setEmployeeName(name.getText().toString());
        employee.setEmployeeAge(Integer.parseInt(age.getText().toString()));
        employee.setEmployeeDesignation(designation.getText().toString());


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final int result = DatabaseHelper.employeeDataService(EmployeeAddActivity.this).deleteData(employee);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result > 0) {
                            Toast.makeText(EmployeeAddActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EmployeeAddActivity.this, "An error occurred deleting", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
