package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CreateClassroomActivity extends AppCompatActivity {

    EditText et_class_name,et_section,et_room,et_subject;
    Button btn_create;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_classroom);

        setTitle("Create class");

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        et_class_name= findViewById(R.id.et_create_classroom_class_name);
        et_section=findViewById(R.id.et_create_classroom_section);
        et_room=findViewById(R.id.et_create_classroom_room);
        et_subject=findViewById(R.id.et_create_classroom_subject);
        btn_create=findViewById(R.id.btn_create_classroom_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_class_name.getText().toString().isEmpty())
                {
                    et_class_name.setError("Please Enter your Class Name");
                }
                else
                {
                    createClassroom();

                }
            }
        });
    }

    private void createClassroom() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        params.put("class_name",et_class_name.getText().toString());
        params.put("section",et_section.getText().toString());
        params.put("room",et_room.getText().toString());
        params.put("subject",et_subject.getText().toString());

        client.post(Urls.urlCreateClassRoom,params,new JsonHttpResponseHandler()
        {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String isSuccess = response.getString("success");
                    if (isSuccess.equals("1"))
                    {
                        Toast.makeText(CreateClassroomActivity.this,"Classroom created Successfully!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateClassroomActivity.this,MainActivity.class);
                        editor.putString("class_name",et_class_name.getText().toString()).commit();
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(CreateClassroomActivity.this,"Invalid Data",Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(CreateClassroomActivity.this,"Server Error"+""+statusCode,Toast.LENGTH_SHORT).show();
            }
        });

    }
}