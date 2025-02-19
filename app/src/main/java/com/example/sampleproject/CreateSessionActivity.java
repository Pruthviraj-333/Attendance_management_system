package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class CreateSessionActivity extends AppCompatActivity {

    EditText et_session_number,et_session_name,et_session_date;
    Button btn_create_session;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        setTitle("Create session");

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        et_session_number= findViewById(R.id.et_create_session_session_number);
        et_session_name =findViewById(R.id.et_create_session_session_name);
        et_session_date = findViewById(R.id.et_create_session_session_date);
        btn_create_session = findViewById(R.id.btn_create_session_create_session);



        btn_create_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_session_number.getText().toString().isEmpty())
                {
                    et_session_number.setError("Please Enter your session number");
                }
                else if (et_session_name.getText().toString().isEmpty())
                {
                    et_session_name.setError("Please Enter your session name");
                }
                else if(et_session_date.getText().toString().isEmpty())
                {
                    et_session_date.setError("Please Enter your session date");
                }
                else
                {
                    createSession();
                }
            }
        });
    }

    private void createSession() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        params.put("session_number",et_session_number.getText().toString());
        params.put("session_name",et_session_name.getText().toString());
        params.put("session_date",et_session_date.getText().toString());


        client.post(Urls.urlCreateSession,params,new JsonHttpResponseHandler()
        {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String isSuccess = response.getString("success");
                    if (isSuccess.equals("1"))
                    {
                        Toast.makeText(CreateSessionActivity.this,"Session created Successfully!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateSessionActivity.this,SessionActivity.class);
                        editor.putString("session_number",et_session_number.getText().toString()).commit();
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(CreateSessionActivity.this,"Invalid Data",Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(CreateSessionActivity.this,"Server Error"+""+statusCode,Toast.LENGTH_SHORT).show();
            }
        });

    }
}