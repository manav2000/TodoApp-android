package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import DataHandler.DatabaseHandler;
import Model.Task;

public class TaskList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Task> tasklist;
    private ImageButton delBtn;
    private Button delAllBtn;
    private TextView noTasks;

    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        noTasks = (TextView) findViewById(R.id.noTasks);

        final DatabaseHandler db = new DatabaseHandler(this);

        //setup recycler view
        recyclerView =(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<Task> tasklist = db.getAllTasks();

        adapter = new MyAdapter(this, tasklist);
        recyclerView.setAdapter(adapter);

        delAllBtn = (Button) findViewById(R.id.delAllBtn);
        delAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tasklist.size() != 0) {
                    alertDialog = new AlertDialog.Builder(
                            new ContextThemeWrapper(TaskList.this, R.style.AlertDialogCustom));

                    alertDialog.setTitle(getResources().getString(R.string.delAllTitle));
                    alertDialog.setMessage(getResources().getString(R.string.delAllMsg));
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton(getResources().getString(R.string.alertPos),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.delAllTasks();
                                    tasklist.removeAll(tasklist);
                                    //notifies the adapter that the tasks are deleted and to change
                                    //itself accordingly
                                    adapter.notifyDataSetChanged();
                                    noTasks.setVisibility(View.VISIBLE);
                                    Toast.makeText(TaskList.this,
                                            "You have successfully deleted all the tasks",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                    alertDialog.setNegativeButton(getResources().getString(R.string.alertNeg),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog dialog = alertDialog.create();
                    dialog.show();

                } else {
                    Toast.makeText(TaskList.this,
                            "You have no tasks to be deleted.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (tasklist.size() == 0) {
            noTasks.setVisibility(View.VISIBLE);
        } else {
            noTasks.setVisibility(View.INVISIBLE);
        }

    }
}