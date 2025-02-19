package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddStudentsActivity extends AppCompatActivity {

    EditText et_student_roll_no,et_student_name,et_student_sem_year;
    Button btn_add_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students);

        setTitle("Add Students In Classroom");

        et_student_roll_no=findViewById(R.id.et_add_student_roll_number);
        et_student_name= findViewById(R.id.et_add_student_name);
        et_student_sem_year=findViewById(R.id.et_add_student_sem_year);
        btn_add_student=findViewById(R.id.btn_add_students);

        btn_add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_student_roll_no.getText().toString().isEmpty())
                {
                    et_student_roll_no.setError("Please Enter Student Roll Number");
                }
                else if (et_student_name.getText().toString().isEmpty())
                {
                    et_student_name.setError("Please Enter Student Name");
                }
                else if(et_student_sem_year.getText().toString().isEmpty())
                {
                    et_student_sem_year.setError("Please Enter Sem/year");
                }
                else
                {
                    addStudent();
                }
            }
        });
    }

    private void addStudent() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        params.put("student_roll_number",et_student_roll_no.getText().toString());
        params.put("student_name",et_student_name.getText().toString());
        params.put("student_sem_year",et_student_sem_year.getText().toString());


        client.post(Urls.urlAddStudentDetails,params,new JsonHttpResponseHandler()
        {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String isSuccess = response.getString("success");
                    if (isSuccess.equals("1"))
                    {
                        Toast.makeText(AddStudentsActivity.this,"Student Added Successfully!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddStudentsActivity.this,SessionActivity.class);
//                        editor.putString("session_number",et_session_number.getText().toString()).commit();
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(AddStudentsActivity.this,"Invalid Data",Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AddStudentsActivity.this,"Server Error"+""+statusCode,Toast.LENGTH_SHORT).show();
            }
        });

    }
}