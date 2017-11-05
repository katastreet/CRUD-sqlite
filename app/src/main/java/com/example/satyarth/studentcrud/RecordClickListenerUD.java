package com.example.satyarth.studentcrud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.controller.StudentTableController;
import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.model.StudentModel;

/**
 * Created by satyarth on 05/11/17.
 * view update and delete implementation
 */

public class RecordClickListenerUD implements View.OnLongClickListener {
    Context context;
    String id;

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
                            viewRecord(Integer.parseInt(id));
                        }
                        else if(item == 1){
                            editRecord(Integer.parseInt(id));
                        }
                        else if (item == 2){
                            deleteRecord(Integer.parseInt(id));
                        }

                    }
                }).show();
        return false;
    }

    public void viewRecord(int studentId){
        final StudentTableController studentTableController = new StudentTableController(context);
        StudentModel objectStudent = studentTableController.readSingleRecord(studentId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_display_form, null, false);

        final TextView Name = (TextView) formElementsView.findViewById(R.id.editTextName);
        final TextView Email = (TextView) formElementsView.findViewById(R.id.editTextEmail);
        final TextView Age = (TextView) formElementsView.findViewById(R.id.editTextAge);
        final TextView Address = (TextView) formElementsView.findViewById(R.id.editTextAddress);

        Name.setText("Name: " + objectStudent.name);
        Email.setText("Email: "+ objectStudent.email);
        Age.setText("Age: " + Integer.toString(objectStudent.age));
        Address.setText("Address: " + objectStudent.address);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Student Record Id:" + objectStudent.id)
                .setPositiveButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }

                        }).show();

    }
    public void editRecord(int studentId){
        final StudentTableController studentTableController = new StudentTableController(context);
        final StudentModel objectStudent = studentTableController.readSingleRecord(studentId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_input_form, null, false);

        final EditText editTextName = (EditText) formElementsView.findViewById(R.id.editTextName);
        final EditText editTextEmail = (EditText) formElementsView.findViewById(R.id.editTextEmail);
        final EditText editTextAge = (EditText) formElementsView.findViewById(R.id.editTextAge);
        final EditText editTextAddress = (EditText) formElementsView.findViewById(R.id.editTextAddress);

        editTextName.setText(objectStudent.name);
        editTextEmail.setText(objectStudent.email);
        editTextAge.setText(Integer.toString(objectStudent.age));
        editTextAddress.setText(objectStudent.address);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record Id:" + objectStudent.id)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String name = editTextName.getText().toString();
                                String email = editTextEmail.getText().toString();
                                String age = editTextAge.getText().toString();
                                String address = editTextAddress.getText().toString();

                                StudentModel studentObject = new StudentModel();
                                studentObject.id = objectStudent.id;
                                studentObject.name = name;
                                studentObject.email = email;
                                studentObject.age = (int) (!age.isEmpty() ? Integer.parseInt(age) : 0);
                                studentObject.address = address;

                                Toast.makeText(context, name + " " + email + " " + age + " " + address, Toast.LENGTH_SHORT).show();

                                boolean updateSuccessful = studentTableController.update(studentObject);

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

    public void deleteRecord(int studentId){
        boolean deleteSuccessful = new StudentTableController(context).delete(Integer.parseInt(id));

        if (deleteSuccessful){
            Toast.makeText(context, "Student record was deleted.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Unable to delete student record.", Toast.LENGTH_SHORT).show();
        }

        ((MainActivity) context).countRecords();
        ((MainActivity) context).readRecords();

    }
}
