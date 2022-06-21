package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Member;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InfCh extends AppCompatActivity {
    EditText[] et = new EditText[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_ch);
        TextView tv = findViewById(R.id.chid1);
        et[0] = findViewById(R.id.chpass1);
        et[1] = findViewById(R.id.chname);
        et[2] = findViewById(R.id.chphone);
        et[3] = findViewById(R.id.chadd);
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID1");
        String name = intent.getStringExtra("NAME");
        String phone = intent.getStringExtra("PHONE");
        String address = intent.getStringExtra("ADDRESS");
        tv.setText("id: " + id);
        et[1].setText(name);
        et[2].setText(phone);
        et[3].setText(address);
        Button btn1, btn2;
        btn1 = findViewById(R.id.chbtn1);
        btn2 = findViewById(R.id.chbtn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            List<Member> list = new ArrayList<Member>();
            @Override
            public void onClick(View v) {
                try {
                    if (et[0].getText().toString().isEmpty() || et[1].getText().toString().isEmpty()) {
                        Toast.makeText(InfCh.this,
                                "필수항목이 빠졌습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        String[] params = new String[4];
                        for (int i = 0; i < params.length; i++) {
                            params[i] = et[i].getText().toString().trim();
                        }
                        String rst = String.valueOf(new InfCh.Task().execute(params).get());
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public class Task extends AsyncTask<String, Void, String> {

        String sendMsg="", receiveMsg = "0";
        String serverIp = "https://ehtest11-nhnopenlab005-member.toast.paas-ta.com/member/"; // 연결할 jsp주소

        @Override
        protected String doInBackground(String ... params) {
            try {
                String str;

                serverIp += (getIntent().getStringExtra("ID1")+"?name="+params[1]+"&phone="+params[2]+"&address="+params[3]+"&password="+params[0]);

                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");  //수정하고자 하면 POST, PUT-> 데이터를 DB 올리겠다.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return receiveMsg;

        }
    }
}