package com.example.sampleproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GetAllStudentsDetailsAdapter extends BaseAdapter {

    ArrayList<POJOGetAllStudentsDetails> pojoGetAllStudentsDetails;
    Activity activity;

    public GetAllStudentsDetailsAdapter(ArrayList<POJOGetAllStudentsDetails> pojoGetAllStudentsDetails, Activity activity){
        this.pojoGetAllStudentsDetails = pojoGetAllStudentsDetails;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojoGetAllStudentsDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoGetAllStudentsDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final GetAllStudentsDetailsAdapter.ViewHolder holder;
        LayoutInflater inflater =(LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(view == null)
        {
            holder =new GetAllStudentsDetailsAdapter.ViewHolder();
            view=inflater.inflate(R.layout.lv_get_all_students_details,null);
            holder.tv_student_roll_number= view.findViewById(R.id.tv_student_details_roll_no);
            holder.tv_student_name = view.findViewById(R.id.tv_student_details_student_name);
            holder.tv_student_sem_year =view.findViewById(R.id.tv_student_details_sem_year);



            view.setTag(holder);
        }
        else
        {
            holder = (GetAllStudentsDetailsAdapter.ViewHolder) view.getTag();
        }

        final POJOGetAllStudentsDetails obj = pojoGetAllStudentsDetails.get(position);
        holder.tv_student_roll_number.setText(obj.getStudent_roll_number());
        holder.tv_student_name.setText(obj.getStudent_name());
        holder.tv_student_sem_year.setText(obj.getStudent_sem_year());

        return view;
    }

    public class ViewHolder {
        TextView tv_student_roll_number,tv_student_name,tv_student_sem_year;
    }
}
