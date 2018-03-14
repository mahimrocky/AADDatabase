package com.aad.databaseforaad.with_room;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aad.databaseforaad.R;
import com.aad.databaseforaad.without_room.*;
import com.aad.databaseforaad.with_room.EmployeeAddActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomDatabaseActivity extends AppCompatActivity implements EmployeeAdapter.OnItemClick {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.button_create)
    FloatingActionButton create;

    private EmployeeAdapter adapter;

    //list of data
    private List<Employee> employeeModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_database);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomDatabaseActivity.this, EmployeeAddActivity.class);
                intent.putExtra("id", 0);
                intent.putExtra("isCreate", true);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onResume() {
        new PullData().execute();
        super.onResume();
    }


    @Override
    public void onGetItem(int id) {

        Intent intent = new Intent(RoomDatabaseActivity.this, EmployeeAddActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("isCreate", false);
        startActivity(intent);
    }

    public class PullData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            employeeModels = DatabaseHelper.employeeDataService(RoomDatabaseActivity.this).getAllData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter = new EmployeeAdapter(RoomDatabaseActivity.this,
                    employeeModels, RoomDatabaseActivity.this);

            recyclerView.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }
    }
}
