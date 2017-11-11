package com.example.satyarth.studentcrud.UI.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.satyarth.studentcrud.UI.Main.MainActivity;
import com.example.satyarth.studentcrud.R;
import com.example.satyarth.studentcrud.model.Student;
import com.example.satyarth.studentcrud.services.repository.DaoServiceStudent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by satyarth on 05/11/17.
 * view update and delete implementation
 */

public class RecordClickListenerUD implements View.OnLongClickListener {
    Context context;
    String id;

    // binding the data display form
    @Nullable @BindView(R.id.textViewName) TextView Name;
    @Nullable @BindView(R.id.textViewEmail) TextView Email;
    @Nullable @BindView(R.id.textViewAge) TextView Age;

    @Nullable @BindView(R.id.textViewAddress) TextView Address;

    //binding data input form
    @Nullable @BindView(R.id.editTextName) EditText editTextName;
    @Nullable @BindView(R.id.editTextEmail) EditText editTextEmail;
    @Nullable @BindView(R.id.editTextAge) EditText editTextAge;
    @Nullable @BindView(R.id.editTextAddress) EditText editTextAddress;



    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();






        final CharSequence[] items = { "View", "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Student Id:" + id)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        //view

                        if(item == 0){
                            viewRecord(Long.parseLong(id));
                        }
                        else if(item == 1){
                            editRecord(Long.parseLong(id));
                        }
                        else if (item == 2){
                            deleteRecord(Long.parseLong(id));
                        }

                    }
                }).show();
        return false;
    }

    public void viewRecord(long studentId){
        Student objectStudent = new DaoServiceStudent().readSingleRecord(studentId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_display_form, null, false);

        //this can be used with any thing or objects
        ButterKnife.bind(this, formElementsView);


        Name.setText("Name: " + objectStudent.getName());
        Email.setText("Email: "+ objectStudent.getEmail());
        Age.setText("Age: " + Integer.toString(objectStudent.getAge()));
        Address.setText("Address: " + objectStudent.getAddress());

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Student Record Id:" + objectStudent.getId())
                .setPositiveButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }

                        }).show();

    }
    public void editRecord(long studentId){
        final Student objectStudent = new DaoServiceStudent().readSingleRecord(studentId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_input_form, null, false);

        ButterKnife.bind(this, formElementsView);


        editTextName.setText(objectStudent.getName());
        editTextEmail.setText(objectStudent.getEmail());
        editTextAge.setText(Integer.toString(objectStudent.getAge()));
        editTextAddress.setText(objectStudent.getAddress());

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record Id:" + objectStudent.getId())
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String name = editTextName.getText().toString();
                                String email = editTextEmail.getText().toString();
                                String age = editTextAge.getText().toString();
                                String address = editTextAddress.getText().toString();

                                Student studentObject = new Student();
                                studentObject.setId(objectStudent.getId());
                                studentObject.setName(name);
                                studentObject.setEmail(email);
                                studentObject.setAge((int) (!age.isEmpty() ? Integer.parseInt(age) : 0));
                                studentObject.setAddress(address);

                                Toast.makeText(context, name + " " + email + " " + age + " " + address, Toast.LENGTH_SHORT).show();

                                boolean updateSuccessful = new DaoServiceStudent().update(studentObject);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Student record was updated.", Toast.LENGTH_SHORT).show();
                                    ((MainActivity) context).readRecords();
                                    ((MainActivity)context).countRecords();
                                }else{
                                    Toast.makeText(context, "Unable to update student record.", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }).show();



    }

    public void deleteRecord(long studentId){
        boolean deleteSuccessful = new DaoServiceStudent().delete(studentId);
        if (deleteSuccessful){
            Toast.makeText(context, "Student record was deleted.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Unable to delete student record.", Toast.LENGTH_SHORT).show();
        }

        ((MainActivity) context).countRecords();
        ((MainActivity) context).readRecords();

    }
}
