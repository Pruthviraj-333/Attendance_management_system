package com.example.sampleproject;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GetAllClassroomDetailsAdapter extends BaseAdapter {
    ArrayList<POJOGetAllClassroomDetails> pojoGetAllClassroomDetails;
    Activity activity;
    CardView cv_classroom;

    public GetAllClassroomDetailsAdapter(ArrayList<POJOGetAllClassroomDetails> pojoGetAllClassroomDetails, Activity activity){
        this.pojoGetAllClassroomDetails = pojoGetAllClassroomDetails;
        this.activity = activity;
    }



    @Override
    public int getCount() {
        return pojoGetAllClassroomDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoGetAllClassroomDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final GetAllClassroomDetailsAdapter.ViewHolder holder;
        LayoutInflater inflater =(LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(view == null)
        {
            holder =new GetAllClassroomDetailsAdapter.ViewHolder();
            view=inflater.inflate(R.layout.lv_classrooms,null);
            holder.tv_class_name= view.findViewById(R.id.tv_class_name);
            holder.tv_section = view.findViewById(R.id.tv_section);
            holder.tv_room =view.findViewById(R.id.tv_teacher_name);
            holder.tv_subject =view.findViewById(R.id.tv_subject_name);
            holder.cv_classroom=view.findViewById(R.id.cv_lv_classrooms);





            view.setTag(holder);
        }
        else
        {
            holder = (GetAllClassroomDetailsAdapter.ViewHolder) view.getTag();
        }

        final POJOGetAllClassroomDetails obj = pojoGetAllClassroomDetails.get(position);
        holder.tv_class_name.setText(obj.getclass_name());
        holder.tv_section.setText(obj.getsection());
        holder.tv_room.setText(obj.getroom());
        holder.tv_subject.setText(obj.getsubject());


        holder.cv_classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ClassroomActivity.class);
                intent.putExtra("id",obj.getId());
                intent.putExtra("class_name",obj.getclass_name());
                Toast.makeText(activity, ""+obj.getId(), Toast.LENGTH_SHORT).show();
                activity.startActivity(intent);
            }
        });



        return view;
    }



    public class ViewHolder {
        TextView tv_class_name,tv_section,tv_room,tv_subject;
        CardView cv_classroom;


    }

}


