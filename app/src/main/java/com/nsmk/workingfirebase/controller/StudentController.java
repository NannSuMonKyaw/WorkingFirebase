package com.nsmk.workingfirebase.controller;

import com.nsmk.workingfirebase.model.StudentModel;

public class StudentController {

    StudentModel modelStudent = new StudentModel();

    //WRITE
    public Boolean save(StudentModel.Student student)
    {
        return modelStudent.save(student);
    }

    //UPDATE
    public Boolean update(String id, StudentModel.Student student)
    {
        return modelStudent.update(id, student);
    }
}
