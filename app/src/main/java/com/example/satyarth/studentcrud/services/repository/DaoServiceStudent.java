package com.example.satyarth.studentcrud.services.repository;

import com.example.satyarth.studentcrud.CRUDapp;
import com.example.satyarth.studentcrud.model.DaoSession;
import com.example.satyarth.studentcrud.model.Student;
import com.example.satyarth.studentcrud.model.StudentDao;

import java.util.List;

/**
 * Created by satyarth on 08/11/17.
 */

public class DaoServiceStudent {
    private StudentDao studentDao;


    public DaoServiceStudent(){
        DaoSession daoSession = CRUDapp.getInstance().getDaoSession();
        this.studentDao = daoSession.getStudentDao();
    }

    public Boolean create(Student student) {
        System.out.print(student.getId());

        try {
            studentDao.insert(student);
            return true;
        }catch(Exception e)
        {
            System.out.println("exception:"+ e.getMessage());
            return false;
        }
    }
    public Student readSingleRecord(long studentId) {
        return studentDao.load(studentId);
    }


    public int getCount(){
        return (int)studentDao.count();
    }


    public List<Student> read(){
        return studentDao.loadAll();
    }
    public boolean update(Student student){
        try {
            studentDao.update(student);
            return true;
        }catch(Exception e)
        {
            System.out.println("exception:"+ e.getMessage());
            return false;
        }
    }
    public boolean delete(long studentId) {

        try {
            studentDao.deleteByKey(studentId);
            return true;
        }catch(Exception e)
        {
            System.out.println("exception:"+ e.getMessage());
            return false;
        }
    }

    public List<Student> searchByName(String name){
        return studentDao.queryBuilder().where(StudentDao.Properties.Name.like(name + "%")).orderAsc().list();
    }

}
