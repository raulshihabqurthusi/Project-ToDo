package com.example.project_todo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_todo.adapter.Adapters;
import com.example.project_todo.database.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList id, title, desc;
    private Database database;
    private Adapters rvAdapters;
    private RecyclerView rvView;
    private FloatingActionButton fab;

    // decorations view
    private ImageView decor1;
    private TextView decor2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvView = findViewById(R.id.rvView);
        database = new Database(MainActivity.this);
        id = new ArrayList();
        title = new ArrayList();
        desc = new ArrayList();
        decor1 = (ImageView) findViewById(R.id.imageView);
        decor2 = (TextView) findViewById(R.id.acc2);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });

        getDataArray();
        rvAdapters = new Adapters(MainActivity.this, this, id, title, desc);
        rvView.setAdapter(rvAdapters);
        rvView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void getDataArray() {
        Cursor cursor = database.readData();
        System.out.println(cursor.getCount());
        if (cursor.getCount() > 0) {
            decor1.setVisibility(View.GONE);
            decor2.setVisibility(View.GONE);
        }
        while (cursor.moveToNext()) {
            id.add(cursor.getString(0));
            title.add(cursor.getString(1));
            desc.add(cursor.getString(2));
        }
    }

}