package com.nsmk.workingfirebase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.nsmk.workingfirebase.R;
import com.nsmk.workingfirebase.model.StudentModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentAdapter extends FirebaseRecyclerAdapter<StudentModel.Student, StudentAdapter.StudentViewHolder>{

    private StudentAdapter.ItemClickListener itemClickListener;

    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvtitle)
        TextView tvTitle;

        StudentModel.Student student;

        public StudentViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        public void bindView(StudentModel.Student student){

            this.student = student;
            tvTitle.setText(student.getName());
        }

        @Override
        public void onClick(View v) {

            if (itemClickListener != null) itemClickListener.onClickItem(this.student);
        }
    }

    public StudentAdapter(FirebaseRecyclerOptions<StudentModel.Student> options, StudentAdapter.ItemClickListener itemClickListener) {
        super(options);

        this.itemClickListener = itemClickListener;
    }

    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list_row, parent, false);
        return new StudentAdapter.StudentViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(StudentAdapter.StudentViewHolder holder, int position, StudentModel.Student model) {
        holder.bindView(model);
    }
    public interface ItemClickListener {
        void onClickItem(StudentModel.Student student);
    }
}
