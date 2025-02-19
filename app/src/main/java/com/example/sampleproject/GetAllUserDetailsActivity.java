package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class GetAllUserDetailsActivity extends AppCompatActivity {

    TextView tv_data_not_found;
    ListView lv_Get_all_user_details;
    ProgressBar progress;

    ArrayList<POJOGetAllUserDetails> pojoGetAllUserDetails;
    GetAllUserDetailsAdapter getAllUserDetailsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_user_details);


        tv_data_not_found = findViewById(R.id.tv_get_all_user_details_data_not_message);
        lv_Get_all_user_details=findViewById(R.id.lv_get_all_user_details_list);
        progress = findViewById(R.id.pb_get_all_user_details_progress);

        pojoGetAllUserDetails = new ArrayList<>();

        getAllUserDetails();
    }

    private void getAllUserDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Urls.urlGetAllUserDetails,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray=response.getJSONArray("getAllUserDetails");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String my_image = jsonObject.getString("myimage");
                        String mobile_no = jsonObject.getString("mobile_no");
                        String email_id = jsonObject.getString("email_id");
                        String username = jsonObject.getString("username");

                        pojoGetAllUserDetails.add(new POJOGetAllUserDetails(id,name,my_image,mobile_no,email_id,username));
                    }

                    getAllUserDetailsAdapter = new GetAllUserDetailsAdapter(pojoGetAllUserDetails,GetAllUserDetailsActivity.this);
                    lv_Get_all_user_details.setAdapter(getAllUserDetailsAdapter);
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
}