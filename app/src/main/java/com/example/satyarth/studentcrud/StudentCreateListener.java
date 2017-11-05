package com.example.satyarth.studentcrud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.controller.StudentTableController;
import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.model.StudentModel;

/**
 * Created by satyarth on 05/11/17.
 */

public class StudentCreateListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        final Context context = view.getContext();

        //get a layout XML into corresponding View Objects
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_input_form, null, false);

        final EditText editTextName = (EditText) formElementsView.findViewById(R.id.editTextName);
        final EditText editTextEmail = (EditText) formElementsView.findViewById(R.id.editTextEmail);
        final EditText editTextAge = (EditText) formElementsView.findViewById(R.id.editTextAge);
        final EditText editTextAddress = (EditText) formElementsView.findViewById(R.id.editTextAddress);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(formElementsView);

        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String age = editTextAge.getText().toString();
                String address = editTextAddress.getText().toString();

                Toast.makeText(context, name + " " + email + " " + age + " " + address, Toast.LENGTH_SHORT).show();

                StudentModel studentObject = new StudentModel();
                studentObject.name = name;
                studentObject.email = email;
                studentObject.age = (int) Integer.parseInt(age);
                studentObject.address = address;

                boolean createSuccess = new StudentTableController(context).create(studentObject);

                if(createSuccess){
                    Toast.makeText(context, "Student information was saved.", Toast.LENGTH_SHORT).show();
                    ((MainActivity) context).readRecords();
                    ((MainActivity)context).countRecords();
                }else{
                    Toast.makeText(context, "Unable to save student information.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alertDialogBuilder.show();


    }
}
