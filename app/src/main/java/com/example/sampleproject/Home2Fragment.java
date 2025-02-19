package com.example.sampleproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Home2Fragment extends Fragment {

    TextView tv_class_name;
    TextView tv_section;
    TextView tv_teacher_name;
    TextView tv_subject_name;
    
    String id;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home2, container, false);
 /*
        tv_class_name = view.findViewById(R.id.tv_classroom_class_name);
        tv_section = view.findViewById(R.id.tv_classroom_section);
        tv_teacher_name = view.findViewById(R.id.tv_classroom_teacher_name);
        tv_subject_name = view.findViewById(R.id.tv_classroom_subject_name);
 */

        getMyClassroomInfo();
        
        return view;
        
        
        
        
    }

    private void getMyClassroomInfo() {
    }
}