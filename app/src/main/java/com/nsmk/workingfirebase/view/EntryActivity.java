package com.nsmk.workingfirebase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nsmk.workingfirebase.R;
import com.nsmk.workingfirebase.controller.StudentController;
import com.nsmk.workingfirebase.model.FirebaseDB;
import com.nsmk.workingfirebase.model.StudentModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EntryActivity extends AppCompatActivity {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etYear)
    EditText etYear;

    @BindView(R.id.etMajor)
    EditText etMajor;

    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    private StudentController studentController;

    private String update_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this, this);

        studentController = new StudentController();

        if(getIntent().hasExtra("id")){
            FirebaseDB.getFirebaseDB().child("Student").child(getIntent().getStringExtra("id")).
                    addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            StudentModel.Student student = dataSnapshot.getValue(StudentModel.Student.class);
                            student.setId(dataSnapshot.getKey().toString());

                            update_id = student.getId();
                            etName.setText(student.getName());
                            etYear.setText(student.getYear());
                            etMajor.setText(student.getMajor());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    @OnClick(R.id.btnSave)
    public void SavetoCloud() {

        if(etName.getText().toString().equals("")){
            etName.setError("Required student Name.");
            return;
        }

        String message = "";
        if(update_id.equals("")) {
            boolean isSave = studentController.save(new StudentModel.Student(etName.getText().toString(), etYear.getText().toString(), etMajor.getText().toString()));
            message = "Save Failed!";
            if (isSave) {
                ClearData();
                message = "Saving Successful !";
            }
        }else{
            boolean isUpdate = studentController.update(update_id, new StudentModel.Student(etName.getText().toString(), etYear.getText().toString(), etMajor.getText().toString()));
            message = "Update Failed!";
            if (isUpdate) {
                ClearData();
                message = "Update Successful !";
            }
        }

        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.btnCancel)
    public void ClearData(){
        etName.setText("");
        etYear.setText("");
        etMajor.setText("");
    }
}
