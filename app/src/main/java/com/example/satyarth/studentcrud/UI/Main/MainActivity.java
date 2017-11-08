package com.example.satyarth.studentcrud.MainActivity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.satyarth.studentcrud.R;
import com.example.satyarth.studentcrud.Common.RecordClickListenerUD;
import com.example.satyarth.studentcrud.Common.StudentCreateListener;
import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.model.Student;
import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.services.repository.DaoServiceStudent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static android.R.attr.editable;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.add) FloatingActionButton addButton;
    @BindView(R.id.searchBar) EditText searchBar;
    @BindView(R.id.linearRecords) LinearLayout linearLayoutRecords;
    @BindView(R.id.textViewRecordCount) TextView textViewRecordCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        countRecords();
        readRecords();
    }


    @OnTextChanged(value = R.id.searchBar, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable editable) {
        List<Student> students = new DaoServiceStudent().searchByName(searchBar.getText().toString());
        updateListView(linearLayoutRecords, students);

        int noOfRecords = students.size();

        textViewRecordCount.setText(noOfRecords + " records found.");
    }

    @OnClick(R.id.add)
    public void add(View view){
        StudentCreateListener studentCreateListener = new StudentCreateListener();
        studentCreateListener.onClick(view);
    }




    public void countRecords() {
        int noOfRecords = new DaoServiceStudent().getCount();
        textViewRecordCount.setText(noOfRecords + " records found.");
    }

    public void readRecords() {

        List<Student> students = new DaoServiceStudent().read();
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




