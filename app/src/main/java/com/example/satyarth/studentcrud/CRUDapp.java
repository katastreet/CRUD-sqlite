package com.example.satyarth.studentcrud;

import android.app.Application;
import android.content.Context;

import com.example.satyarth.studentcrud.model.DaoMaster;
import com.example.satyarth.studentcrud.model.DaoSession;

import org.greenrobot.greendao.database.Database;


/**
 * Created by satyarth on 08/11/17.
 */

public class CRUDapp extends  Application{

    private DaoSession mDaoSession;
    private static CRUDapp instance;
    @Override
    public void onCreate(){
        super.onCreate();
        initializeDaoSession();

        instance = this;
    }

    public static CRUDapp getInstance(){ return instance;}
    public static CRUDapp get(Context context){return (CRUDapp) context.getApplicationContext();}

    public void initializeDaoSession(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "student_db.db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();

    }

    public DaoSession getDaoSession(){
        if(mDaoSession ==  null){
            initializeDaoSession();
        }
        return mDaoSession;
    }



}
