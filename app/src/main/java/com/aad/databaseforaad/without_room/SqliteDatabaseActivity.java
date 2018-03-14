package com.aad.databaseforaad.without_room;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aad.databaseforaad.R;

import java.util.List;

public class SqliteDatabaseActivity extends AppCompatActivity implements EmployeeAdapter.OnItemClick {

    RecyclerView recyclerView;

    FloatingActionButton create;

    // this is our database helper
    private DatabaseHelper databaseHelper;

    // this is our adapter

    private EmployeeAdapter adapter;

    //list of data

    private List<EmployeeModel> employeeModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_database);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        create = (FloatingActionButton) findViewById(R.id.button_create);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SqliteDatabaseActivity.this, EmployeeAddActivity.class);
                intent.putExtra("id", 0);
                intent.putExtra("isCreate", true);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        databaseHelper = new DatabaseHelper(this);
        new PullData().execute();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }


    @Override
    public void onGetItem(int id) {
        Intent intent = new Intent(SqliteDatabaseActivity.this, EmployeeAddActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("isCreate", false);
        startActivity(intent);
    }

    public class PullData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            employeeModels = databaseHelper.readMultipleItem();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter = new EmployeeAdapter(SqliteDatabaseActivity.this,
                    employeeModels, SqliteDatabaseActivity.this);
            recyclerView.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }
    }
}
