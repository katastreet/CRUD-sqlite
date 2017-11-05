package com.example.satyarth.studentcrud;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.controller.StudentTableController;
import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.model.StudentModel;

import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (FloatingActionButton) findViewById(R.id.add);

        addButton.setOnClickListener(new StudentCreateListener());

        countRecords();
        readRecords();


    }

    public void countRecords() {
        int noOfRecords = new StudentTableController(this).getCount();

        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(noOfRecords + " records found.");
    }

    public void readRecords() {
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearRecords);
        linearLayoutRecords.removeAllViews();
        List<StudentModel> students = new StudentTableController(this).read();

        if (students.size() > 0) {

            for (StudentModel obj : students) {

                int id = obj.id;
                String studentName = obj.name;
                String studentEmail = obj.email;

                String textViewContents = studentName + "\n" + studentEmail;

                TextView textViewStudentItem = new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setBackgroundColor(BLACK);
                textViewStudentItem.setTextColor(WHITE);
                textViewStudentItem.setTextSize(18);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));
                textViewStudentItem.setOnLongClickListener(new RecordClickListenerUD());


                linearLayoutRecords.addView(textViewStudentItem);

                TextView line = new TextView(this);
                line.setBackgroundColor(WHITE);
                line.setTextSize(2);

                linearLayoutRecords.addView(line);


            }

        } else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }

    }
}




