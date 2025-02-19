package com.example.sampleproject;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class GetAllSessionInfoAdapter extends BaseAdapter {

    ArrayList<POJOGetAllSessionInfo> pojoGetAllSessionInfos;
    Activity activity;

    public GetAllSessionInfoAdapter(ArrayList<POJOGetAllSessionInfo> pojoGetAllSessionInfos, Activity activity){
        this.pojoGetAllSessionInfos = pojoGetAllSessionInfos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojoGetAllSessionInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoGetAllSessionInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final GetAllSessionInfoAdapter.ViewHolder holder;
        LayoutInflater inflater =(LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(view == null)
        {
            holder =new GetAllSessionInfoAdapter.ViewHolder();
            view=inflater.inflate(R.layout.lv_get_all_session_info,null);
            holder.tv_session_number= view.findViewById(R.id.tv_session_session_number);
            holder.tv_session_name = view.findViewById(R.id.tv_session_session_name);
            holder.tv_session_date =view.findViewById(R.id.tv_session_session_date);
            holder.cv_session =view.findViewById(R.id.cv_session);


            view.setTag(holder);
        }
        else
        {
            holder = (GetAllSessionInfoAdapter.ViewHolder) view.getTag();
        }

        final POJOGetAllSessionInfo obj = pojoGetAllSessionInfos.get(position);
        holder.tv_session_number.setText(obj.getSession_number());
        holder.tv_session_name.setText(obj.getSession_name());
        holder.tv_session_date.setText(obj.getSession_date());

        holder.cv_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,SessionActivity.class);
                intent.putExtra("id",obj.getId());
                Toast.makeText(activity, ""+obj.getId(), Toast.LENGTH_SHORT).show();
                activity.startActivity(intent);
            }
        });

        return view;
    }

    public class ViewHolder {
        TextView tv_session_number,tv_session_name,tv_session_date;
        CardView cv_session;
    }
}
