package com.nsmk.workingfirebase.model;

import android.util.Log;

import com.google.firebase.database.DatabaseException;

import java.util.ArrayList;

public class StudentModel {
    Boolean saved = null;
    ArrayList<StudentModel.Student> students = new ArrayList<>();

    public static class Student{
        String id;
        String name;
        String year;
        String major;

        public Student() {
        }

        public Student( String name, String year, String major) {
            this.name = name;
            this.year = year;
            this.major = major;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }
    }

    //WRITE
    public Boolean save(StudentModel.Student student)
    {
        if(student==null)
        {
            saved=false;
        }else
        {
            try
            {
                FirebaseDB.getFirebaseDB().child("Student").push().setValue(student);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

    //UPDATE
    public Boolean update(String id, StudentModel.Student student)
    {
        if(student==null)
        {
            saved=false;
        }else
        {
            try
            {
                Log.i("UPDATE ID ", id);
                FirebaseDB.getFirebaseDB().child("Student").child(id).setValue(student);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

}
