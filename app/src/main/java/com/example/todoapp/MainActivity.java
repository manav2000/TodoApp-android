package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Components.DatePickerFragment;
import Components.TimePickerFragment;
import DataHandler.DatabaseHandler;
import Model.Task;

public class MainActivity extends AppCompatActivity {

    private EditText enterTask;
    private EditText taskDesc;
    private TextView time;
    private TextView date;
    private RadioGroup priorities;
    private RadioButton radioButton;
    private Button addTask;
    private Button showTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseHandler db = new DatabaseHandler(this);

        intializeLayout();

        priorities.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);

                switch(radioButton.getId()) {
                    case R.id.high:
                    case R.id.low:
                        break;
                }
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String db_task, db_desc, db_date, db_time, db_priority;

                db_task = enterTask.getText().toString();
                db_desc = (taskDesc.getText().toString().equals("")) ? "No Description" :
                        taskDesc.getText().toString();
                db_date = date.getText().toString();
                db_time = time.getText().toString();
                db_priority = (priorities.getCheckedRadioButtonId() == R.id.high)? "high" : "low";

                if ( !db_task.toString().equals("") && !db_date.toString().equals("00/00/0000")
                        && !db_time.toString().equals("00:00")) {
                    db.addTask(new Task(db_task, db_desc, db_date, db_time, db_priority));
                    Toast.makeText(MainActivity.this,
                            "The task was added to the list successfully!!!",
                            Toast.LENGTH_SHORT).show();
                    enterTask.setText("");
                    taskDesc.setText("");
                    date.setText("00/00/0000");
                    time.setText("00:00");
                    if (priorities.getCheckedRadioButtonId() != -1) {
                        radioButton.setChecked(false);
                    }

                } else {
                    Toast.makeText(MainActivity.this,
                            "Make sure u have entered task, date, time and priority",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        showTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskList.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    public void intializeLayout() {
        enterTask = (EditText) findViewById(R.id.editTask);
        taskDesc = (EditText) findViewById(R.id.taskDesc);
        time = (TextView) findViewById(R.id.showTime);
        date = (TextView) findViewById(R.id.showDate);
        priorities = (RadioGroup) findViewById(R.id.priorities);
        addTask = (Button) findViewById(R.id.addTask);
        showTasks = (Button) findViewById(R.id.taskList);
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newTimeFragment = new TimePickerFragment(time);
        newTimeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newDateFragment = new DatePickerFragment(date);
        newDateFragment.show(getSupportFragmentManager(), "datePicker");
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        SharedPreferences sp = getSharedPreferences("TodosPref", MODE_PRIVATE);
//        String task, description, pref_time, pref_date;
//
//        task  = sp.getString("task", "");
//        description = sp.getString("description", "");
//        pref_time  = sp.getString("time", "");
//        pref_date  = sp.getString("date", "");
//
//        enterTask.setText(task);
//        taskDesc.setText(description);
//        time.setText(pref_time);
//        date.setText(pref_date);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        SharedPreferences sharedPreferences = getSharedPreferences("TodosPref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putString("task", String.valueOf(enterTask.getText()));
//        editor.putString("description", String.valueOf(taskDesc.getText()));
//        editor.putString("time", String.valueOf(time.getText()));
//        editor.putString("date", String.valueOf(date.getText()));
//
//        editor.commit();
//    }
}