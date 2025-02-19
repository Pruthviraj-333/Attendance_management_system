package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class  RegisterActivity extends AppCompatActivity {


    EditText et_name,et_mobile_no,et_email_id,et_username,et_password;
    Button btn_register;
    ProgressBar progress;
    TextView tv_login;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_name = findViewById(R.id.et_register_name);
        et_mobile_no = findViewById(R.id.et_register_mobile_no);
        et_email_id = findViewById(R.id.et_register_email_id);
        et_username = findViewById(R.id.et_register_username);
        et_password = findViewById(R.id.et_register_password);
        btn_register = findViewById(R.id.btn_register_register);
        progress = findViewById(R.id.pb_registration_progress);
        tv_login = findViewById(R.id.tv_register_to_login);

        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor= preferences.edit();


        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent3);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().isEmpty())
                {
                    et_name.setError("Please Enter your Name");
                }
                else if (et_mobile_no.getText().toString().isEmpty())
                {
                    et_mobile_no.setError("Please Enter Your Mobile No");
                }
                else if (et_mobile_no.getText().toString().length()!=10)
                {
                    et_mobile_no.setError("Please Enter 10 Digit Mobile No");
                }
                else if (et_username.getText().toString().isEmpty())
                {
                    et_username.setError("Please Enter Your Username");
                }
                else if (et_password.getText().toString().isEmpty())
                {
                    et_password.setError("Please Enter Your password");
                }
                else
                {
                    progress.setVisibility(View.VISIBLE);
                    addUserDetails();
                }
            }
        });


    }

    private void addUserDetails() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",et_name.getText().toString());
        params.put("mobile_no",et_mobile_no.getText().toString());
        params.put("email_id",et_email_id.getText().toString());
        params.put("username",et_username.getText().toString());
        params.put("password",et_password.getText().toString());

        client.post(Urls.urlRegisterUser,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progress.setVisibility(View.GONE);
                try {
                    String isSuccess = response.getString("success");
                    if (isSuccess.equals("1"))
                    {
                        Toast.makeText(RegisterActivity.this,"Registration Successfully Done!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        editor.putString("student_name",et_name.getText().toString()).commit();
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,"Invalid Data",Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progress.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this,"Server Error"+""+statusCode,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

//call kar