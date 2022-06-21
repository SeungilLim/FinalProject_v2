package com.example.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.NearCheck1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NearCheck2 extends Fragment {
    public static NearCheck2 newInstance() {
        return new NearCheck2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_near_check2, null);
        Button button1 = (Button)view.findViewById(R.id.forMapBtn);
        TextView tv1 = (TextView)view.findViewById(R.id.textView2);
        double location = 0;
        List<Member> mapData = new ArrayList<Member>();
        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra("ID");
        int sum=0;
        try {
            String rst1 = String.valueOf(new Task1().execute(id).get());

            JSONObject json = new JSONObject(rst1);
            id = json.getString("id");
            String name = json.getString("name");
            double latitude = json.getDouble("latitude");
            double longitude = json.getDouble("longitude");
            location = latitude+longitude;

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String rst = String.valueOf(new Task().execute().get());
            JSONArray jArr = new JSONArray(rst);

            String id1="";
            String name="";
            String phone = "";
            String address = "";
            String password = "";
            double latitude = 0;
            double longitude = 0;
            JSONObject json = null;

            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                id1 = json.getString("id");
                name = json.getString("name");
                phone = json.getString("phone");
                address = json.getString("address");
                password = json.getString("password");
                latitude = json.getDouble("latitude");
                longitude = json.getDouble("longitude");
                double location1 = location-(latitude+longitude);
                if(location1 < 0.1 && location1 > -0.1 && location1!=0) {
                    mapData.add(new Member(id1, name, phone, address, password, latitude, longitude));
                    sum+=1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        tv1.setText(String.valueOf(sum)+"명이 근처에 있습니다.");
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ((NearCheck1)getActivity()).replaceFragment(NearCheck3.newInstance(mapData));
            }
        });

        return view;
    }
    public class Task extends AsyncTask<Void, Void, String> {

        String sendMsg="", receiveMsg;
        String serverIp = "https://ehtest11-nhnopenlab005-member.toast.paas-ta.com/member/all"; // 연결할 jsp주소

        @Override
        protected String doInBackground(Void ... voids) {
            try {
                String str;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }

    }
    public class Task1 extends AsyncTask<String, Void, String> {

        String receiveMsg;
        String serverIp = "https://ehtest11-nhnopenlab005-member.toast.paas-ta.com/member/"; // 연결할 jsp주소

        @Override
        protected String doInBackground(String... params) {
            try {
                String str;
                serverIp += params[0];
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return receiveMsg;

        }
    }

}
