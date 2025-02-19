package com.example.sampleproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ClassroomActivity extends AppCompatActivity /*implements BottomNavigationView.OnNavigationItemSelectedListener*/ {

    TextView tv_class_name;
    TextView tv_section;
    TextView tv_teacher_name;
    TextView tv_subject_name;

    ListView lv_get_all_session_info;

    ArrayList<POJOGetAllSessionInfo> pojoGetAllSessionInfo;
    GetAllSessionInfoAdapter getAllSessionInfoAdapter;

    FloatingActionButton btn_create_session;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String create_classroom_class_name;
    String id;

//    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        setTitle("Class Room");

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        create_classroom_class_name = preferences.getString("class_name", "");

//        bottomNavigationView = findViewById(R.id.bottomNavigationView);

//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        tv_class_name = findViewById(R.id.tv_classroom_class_name);
        tv_section = findViewById(R.id.tv_classroom_section);
        tv_teacher_name = findViewById(R.id.tv_classroom_teacher_name);
        tv_subject_name = findViewById(R.id.tv_classroom_subject_name);

        btn_create_session = findViewById(R.id.action_btn_create_session);

        btn_create_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });



        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();

        getMyClassroomInfo();

        lv_get_all_session_info=findViewById(R.id.lv_get_all_session_info);

        pojoGetAllSessionInfo = new ArrayList<POJOGetAllSessionInfo>();

        getAllSessionInfo();
    }

    private void getAllSessionInfo() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Urls.urlGetAllSessionInfo,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray=response.getJSONArray("getAllSessionInfo");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String session_number = jsonObject.getString("session_number");
                        String session_name = jsonObject.getString("session_name");
                        String session_date = jsonObject.getString("session_date");


                        pojoGetAllSessionInfo.add(new POJOGetAllSessionInfo(id,session_number,session_name,session_date));
                    }

                    getAllSessionInfoAdapter = new GetAllSessionInfoAdapter(pojoGetAllSessionInfo,ClassroomActivity.this);
                    lv_get_all_session_info.setAdapter(getAllSessionInfoAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bootomsheetlayout_create_session);

        TextView tv_create_session = dialog.findViewById(R.id.tv_bootom_sheet_create_session);


        tv_create_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassroomActivity.this,CreateSessionActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    private void getMyClassroomInfo() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("id", id);
//        params.put("class_name", create_classroom_class_name);

        client.post(Urls.urlGetMyClassRoomDetails, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getMyClassroomDetails");
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String class_name = jsonObject.getString("class_name");
                        String section = jsonObject.getString("section");
                        String room = jsonObject.getString("room");
                        String subject = jsonObject.getString("subject");

                        tv_class_name.setText(class_name);
                        tv_section.setText(section);
                        tv_teacher_name.setText(room);
                        tv_subject_name.setText(subject);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
/*
    Home2Fragment home2Fragment = new Home2Fragment();
    Attendance2Fragment attendance2Fragment = new Attendance2Fragment();
    People2Fragment people2Fragment = new People2Fragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_Fragment, home2Fragment).commit();
                return true;

            case R.id.navigation_attendance:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_Fragment, attendance2Fragment).commit();
                return true;

            case R.id.navigation_people:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_Fragment, people2Fragment).commit();
                return true;
        }
        return false;
    }

 */

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.classroom_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_students_classroom:
                Intent intent = new Intent(ClassroomActivity.this,AddStudentsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}