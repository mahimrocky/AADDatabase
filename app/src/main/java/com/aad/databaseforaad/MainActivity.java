package com.aad.databaseforaad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aad.databaseforaad.with_room.RoomDatabaseActivity;
import com.aad.databaseforaad.without_room.SqliteDatabaseActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button withRoom, withOutRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // with SQLite Database

        withRoom = (Button) findViewById(R.id.button_with_room);

        // With room database library

        withOutRoom = (Button) findViewById(R.id.button_with_out_room);

        withRoom.setOnClickListener(this);
        withOutRoom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_with_room:
                startActivity(new Intent(MainActivity.this,RoomDatabaseActivity.class));
                break;
            case R.id.button_with_out_room:
                startActivity(new Intent(MainActivity.this,SqliteDatabaseActivity.class));
                break;
        }

    }
}
