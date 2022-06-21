package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyInf1 extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4,tv5,tv6;
    Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inf1);
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        tv1 = findViewById(R.id.myinfid);
        tv2 = findViewById(R.id.myinfname);
        tv3 = findViewById(R.id.myinfphone);
        tv4 = findViewById(R.id.myinfaddre);
        tv5 = findViewById(R.id.myinflat);
        tv6 = findViewById(R.id.myinflon);
        btn1 = findViewById(R.id.infch);
        btn2 = findViewById(R.id.con);
        try {
            String rst = String.valueOf(new MyInf1.Task().execute(id.trim()).get());

            JSONObject json = new JSONObject(rst);
            id = json.getString("id");
            String name = json.getString("name");
            String phone = json.getString("phone");
            String address = json.getString("address");
            double latitude = json.getDouble("latitude");
            double longitude = json.getDouble("longitude");
            tv1.setText("id: " + id);
            tv2.setText("이름: " + name);
            tv3.setText("전화번호: " + phone);
            tv4.setText("주소: " + address);
            tv5.setText("위도: " + latitude);
            tv6.setText("경도: " + longitude);
            String id1 = id;
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent1 = new Intent(getApplicationContext(), InfCh.class);
                    intent1.putExtra("ID1", id1);
                    intent1.putExtra("NAME", name);
                    intent1.putExtra("PHONE", phone);
                    intent1.putExtra("ADDRESS", address);
                    startActivity(intent1);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class Task extends AsyncTask<String, Void, String> {

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