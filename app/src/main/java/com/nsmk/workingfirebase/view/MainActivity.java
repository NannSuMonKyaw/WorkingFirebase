package com.nsmk.workingfirebase.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.nsmk.workingfirebase.R;
import com.nsmk.workingfirebase.adapter.StudentAdapter;
import com.nsmk.workingfirebase.controller.StudentController;
import com.nsmk.workingfirebase.model.FirebaseDB;
import com.nsmk.workingfirebase.model.StudentModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements StudentAdapter.ItemClickListener {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private StudentController studentController;

    private StudentAdapter mFirebaseAdapter;

    public ArrayList<StudentModel.Student> Students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEntry = new Intent(MainActivity.this, EntryActivity.class);
                startActivity(intentEntry);
            }
        });

        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {

        Query query = FirebaseDB.getFirebaseDB().child("Student");
        FirebaseRecyclerOptions<StudentModel.Student> options =
                new FirebaseRecyclerOptions.Builder<StudentModel.Student>()
                        .setQuery(query, new SnapshotParser<StudentModel.Student>() {
                            @NonNull
                            @Override
                            public StudentModel.Student parseSnapshot(@NonNull DataSnapshot snapshot) {

                                StudentModel.Student Student = new StudentModel.Student(
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("year").getValue().toString(),
                                        snapshot.child("major").getValue().toString());
                                Student.setId(snapshot.getKey().toString());
                                return Student;
                            }
                        })
                        .build();

        mFirebaseAdapter = new StudentAdapter(options, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mFirebaseAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_get) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
    }

    @Override
    public void onClickItem(StudentModel.Student Student){

        Log.i("Student ", Student.getId());
        Intent intentDetail = new Intent(MainActivity.this, EntryActivity.class);
        intentDetail.putExtra("id", Student.getId());
        startActivity(intentDetail);
    }
}
