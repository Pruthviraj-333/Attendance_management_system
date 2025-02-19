package com.example.sampleproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SessionActivity extends AppCompatActivity {

    TextView tv_session_number;
    TextView tv_session_name;
    TextView tv_session_date;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String create_session_session_number;
    String id;

    String QR_code_data;

    ListView lv_get_all_student_details;

    CardView cv_present_visibility;
    CardView cv_absent_visibility;

    ArrayList<POJOGetAllStudentsDetails> pojoGetAllStudentsDetails;
    GetAllStudentsDetailsAdapter getAllStudentsDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        setTitle("Session");

        cv_present_visibility=findViewById(R.id.cv_student_details_present);
        cv_absent_visibility=findViewById(R.id.cv_student_details_absent);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        create_session_session_number = preferences.getString("session_number", "");

        Intent QRintent= getIntent();
        QR_code_data = QRintent.getStringExtra("QR_data");
        Toast.makeText(this, "QR is "+QR_code_data, Toast.LENGTH_SHORT).show();

        getStudentAttendance();

        tv_session_number =findViewById(R.id.tv_session_session_number);
        tv_session_name =findViewById(R.id.tv_session_session_name);
        tv_session_date=findViewById(R.id.tv_session_session_date);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();

        getMySessionInfo();

        lv_get_all_student_details=findViewById(R.id.lv_get_all_studenta_Details);

        pojoGetAllStudentsDetails = new ArrayList<POJOGetAllStudentsDetails>();

        getAllStudentDeatails();
    }

    private void getStudentAttendance() {

        if(QR_code_data!=null)
        {
//            Toast.makeText(this, "QR code is null", Toast.LENGTH_SHORT).show();
            cv_present_visibility.setVisibility(View.VISIBLE);
            cv_absent_visibility.setVisibility(View.GONE);
        }
        else
        {

            Toast.makeText(this, "QR is null", Toast.LENGTH_SHORT).show();

        }


    }

    private void getAllStudentDeatails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Urls.urlGetAllStudentDetails,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray=response.getJSONArray("getAllStudentDetails");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String student_roll_number = jsonObject.getString("student_roll_number");
                        String student_name = jsonObject.getString("student_name");
                        String student_sem_year = jsonObject.getString("student_sem_year");


                        pojoGetAllStudentsDetails.add(new POJOGetAllStudentsDetails(id,student_roll_number,student_name,student_sem_year));
                    }

                    getAllStudentsDetailsAdapter = new GetAllStudentsDetailsAdapter(pojoGetAllStudentsDetails,SessionActivity.this);
                    lv_get_all_student_details.setAdapter(getAllStudentsDetailsAdapter);

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

    private void getMySessionInfo() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("id",id);
//        params.put("class_name", create_classroom_class_name);

        client.post(Urls.urlGetMySessionInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getMySessionInfo");
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String session_number = jsonObject.getString("session_number");
                        String session_name = jsonObject.getString("session_name");
                        String session_date = jsonObject.getString("session_date");

                        tv_session_number.setText(session_number);
                        tv_session_name.setText(session_name);
                        tv_session_date.setText(session_date);


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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.session_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.scan_QR_code:
                Intent intent = new Intent(SessionActivity.this,ScanQRcodeActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}