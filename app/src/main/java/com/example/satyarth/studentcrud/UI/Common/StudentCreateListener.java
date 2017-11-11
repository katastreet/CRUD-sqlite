package com.example.satyarth.studentcrud.UI.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.satyarth.studentcrud.UI.Main.MainActivity;
import com.example.satyarth.studentcrud.R;
import com.example.satyarth.studentcrud.model.Student;
import com.example.satyarth.studentcrud.services.repository.DaoServiceStudent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by satyarth on 05/11/17.
 */

public class StudentCreateListener{
    @BindView(R.id.editTextName) EditText editTextName;
    @BindView(R.id.editTextEmail) EditText editTextEmail;
    @BindView(R.id.editTextAge) EditText editTextAge;
    @BindView(R.id.editTextAddress) EditText editTextAddress;

    public void onClick(View view) {
        final Context context = view.getContext();

        //get a layout XML into corresponding View Objects
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_input_form, null, false);

        ButterKnife.bind(this, formElementsView);

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

                Student studentObject = new Student();
                studentObject.setName(name);
                studentObject.setEmail(email);
                studentObject.setAge((int) (!age.isEmpty() ? Integer.parseInt(age) : 0));
                studentObject.setAddress(address);

                boolean createSuccess = new DaoServiceStudent().create(studentObject);

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
