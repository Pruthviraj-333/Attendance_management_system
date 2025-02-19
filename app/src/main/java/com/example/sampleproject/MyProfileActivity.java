package com.example.sampleproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {

    TextView tv_name,tv_mobile_no,tv_email_id,tv_username;

    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    private static final String TAG = null;

    TextView my_QR_code;
    TextView log_out;
    TextView my_teaching_classes;
    TextView security;
    TextView help;
    Button btn_update_image;

    ProgressBar progress;

    CircleImageView img_profile_pic;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String login_username;
    ImageButton imgbtn_edit_image;

    Bitmap bitmap;
    Uri filepath;
    private int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        setTitle("My Profile");

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
/*         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName() ;
            String personEmail = acct.getEmail();
//            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            tv_name.setText(personName);
            tv_email_id.setText(personEmail);
            Picasso.with(this).load(String.valueOf(personPhoto)).into(img_profile_pic);


            Toast.ma keText(this,"email",Toast.LENGTH_SHORT).show();


        }
*/


        tv_name=findViewById(R.id.tv_my_profile_name);
        tv_mobile_no=findViewById(R.id.tv_my_profile_mobile_no);
        tv_email_id=findViewById(R.id.tv_my_profile_email_id);
        tv_username=findViewById(R.id.tv_my_profile_username);
        img_profile_pic =findViewById(R.id.civ_my_profile_cirle_image);
        imgbtn_edit_image=findViewById(R.id.imgbtn_my_profile_edit);
        btn_update_image = findViewById(R.id.btn_my_profile_update);

        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor= preferences.edit();
        login_username=preferences.getString("username","");



        my_QR_code=findViewById(R.id.tv_my_profile_my_qr_code);

        my_QR_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this,MyQrCodeActivity.class);
                startActivity(intent);
            }
        });

        security=findViewById(R.id.tv_my_profile_security);
        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://policies.google.com"));
                startActivity(intent);
            }
        });

        help=findViewById(R.id.tv_my_profile_help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://support.google.com"));
                startActivity(intent);
            }
        });

        my_teaching_classes=findViewById(R.id.tv_my_profile_my_teaching_classes);
        my_teaching_classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        log_out= findViewById(R.id.tv_my_profile_log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(MyProfileActivity.this);
                ad.setTitle("Logout");
                ad.setMessage("Aru you sure you want to Logout");
                ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i2 = new Intent(MyProfileActivity.this,LoginActivity.class);
                        editor.putBoolean("isLogin",false).commit();
                        startActivity(i2);
                        finish();
                    }
                }).create().show();

            }
        });

        imgbtn_edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        getMyInfo();



    }





    // chose the image from Internal storage
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null)
        {
            filepath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                img_profile_pic.setImageBitmap(bitmap) ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this,"Unable to Select Image",Toast.LENGTH_SHORT).show();
        }
    }

    private void getMyInfo() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",login_username);

        client.post(Urls.urlgetMyDetails,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("getMyDetails");
                    for(int i=0;i<=jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String myimage = jsonObject.getString("myimage");
                        String mobile_no = jsonObject.getString("mobile_no");
                        String email_id = jsonObject.getString("email_id");
                        String username = jsonObject.getString("username");

                        tv_name.setText(name);
                        tv_mobile_no.setText(mobile_no);
                        tv_email_id.setText(email_id);
                        tv_username.setText(username);

                        Picasso.with(MyProfileActivity.this)
                                .load(Urls.OnlineImageAddress+""+myimage)
                                .error(R.drawable.my_profile)
                                .into(img_profile_pic);
 //                       Toast.makeText(MyProfileActivity.this,""+id,Toast.LENGTH_SHORT).show();

                        btn_update_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                uploadBitmap(bitmap,id);
                            }
                        });
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

    private void uploadBitmap(Bitmap bitmap, String id) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.urlAddProfileImage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(MyProfileActivity.this, "Profile Picture Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyProfileActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }

            protected Map<String, DataPart> getByteData() throws AuthFailureError{
                Map<String, DataPart> params = new HashMap<>();
                String imagename = String.valueOf(System.currentTimeMillis());
                params.put("pic",new DataPart(imagename+".png",getFileDataFromDrawable(bitmap)));
                return params;
            }


        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }

    private byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byteArrayOutputStream);
        return  byteArrayOutputStream.toByteArray();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_profile_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.edit_my_profile:
                Intent intent = new Intent(MyProfileActivity.this,MyInfoActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}