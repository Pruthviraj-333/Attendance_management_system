package com.example.sampleproject;

import android.app.Activity;
import android.app.Notification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GetAllUserDetailsAdapter extends BaseAdapter {
    ArrayList<POJOGetAllUserDetails> pojoGetAllUserDetails;
    Activity activity;

    public GetAllUserDetailsAdapter(ArrayList<POJOGetAllUserDetails> pojoGetAllUserDetails, Activity activity){
        this.pojoGetAllUserDetails = pojoGetAllUserDetails;
        this.activity = activity;
    }



    @Override
    public int getCount() {
        return pojoGetAllUserDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoGetAllUserDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final GetAllUserDetailsAdapter.ViewHolder holder;
        LayoutInflater inflater =(LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(view == null)
        {
            holder =new GetAllUserDetailsAdapter.ViewHolder();
            view=inflater.inflate(R.layout.lv_get_all_user_details,null);
            holder.img_user_image= view.findViewById(R.id.civ_my_profile_cirle_image);
            holder.tv_name = view.findViewById(R.id.tv_my_profile_name);
            holder.tv_mobile_no =view.findViewById(R.id.tv_my_profile_mobile_no);
            holder.tv_email_id=view.findViewById(R.id.tv_my_profile_email_id);
            holder.tv_username= view.findViewById(R.id.tv_my_profile_username);

            view.setTag(holder);
        }
        else
        {
            holder = (GetAllUserDetailsAdapter.ViewHolder) view.getTag();
        }

        final POJOGetAllUserDetails obj = pojoGetAllUserDetails.get(position);
        holder.tv_name.setText(obj.getName());
        holder.tv_mobile_no.setText(obj.getMobile_no());
        holder.tv_email_id.setText(obj.getEmail_id());
        holder.tv_username.setText(obj.getUsername());

        Picasso.with(activity).load(Urls.OnlineImageAddress+""+obj.getProfile_pic()).error(R.drawable.my_profile)
                .into(holder.img_user_image);

        return view;
    }

    class  ViewHolder
    {
        ImageView img_user_image;
        TextView tv_name,tv_mobile_no,tv_email_id,tv_username;
    }
}
