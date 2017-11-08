package com.example.satyarth.studentcrud;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.model.Student;
import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.services.repository.DaoServiceStudent;

import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addButton;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addButton = (FloatingActionButton) findViewById(R.id.add);
        searchBar = (EditText) findViewById(R.id.searchBar);

        addButton.setOnClickListener(new StudentCreateListener());

        countRecords();
        readRecords();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                List<Student> students = new DaoServiceStudent().searchByName(searchBar.getText().toString());
                LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearRecords);
                updateListView(linearLayoutRecords, students);

                int noOfRecords = students.size();

                TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
                textViewRecordCount.setText(noOfRecords + " records found.");

            }
        });


    }

    public void countRecords() {
        int noOfRecords = new DaoServiceStudent().getCount();

        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(noOfRecords + " records found.");
    }

    public void readRecords() {

        List<Student> students = new DaoServiceStudent().read();

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearRecords);

        updateListView(linearLayoutRecords, students);

    }

    public void updateListView(LinearLayout linearLayoutRecords, List<Student> students){
        linearLayoutRecords.removeAllViews();
        if (students.size() > 0) {

            for (Student obj : students) {

                long id = obj.getId();
                String studentName = obj.getName();
                String studentEmail = obj.getEmail();

                String textViewContents = studentName + "\n" + studentEmail;

                TextView textViewStudentItem = new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setBackgroundColor(BLACK);
                textViewStudentItem.setTextColor(WHITE);
                textViewStudentItem.setTextSize(18);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Long.toString(id));
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




