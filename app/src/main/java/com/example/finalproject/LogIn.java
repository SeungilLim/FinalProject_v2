package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Member;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity {

    EditText et, et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button1 = (Button)findViewById(R.id.loginBtn);
        et = findViewById(R.id.loginid);
        et2 = findViewById(R.id.loginPassword);
        button1.setOnClickListener(new View.OnClickListener() {
            List<Member> list = new ArrayList<Member>();
            @Override
            public void onClick(View v) {
                try {
                    String rst = String.valueOf(new Task().execute(et.getText().toString().trim()).get());
                    JSONObject json = new JSONObject(rst);
                    String id = json.getString("id");
                    String password = json.getString("password");
                    if(password.equals(et2.getText().toString()) ){
                        Intent intent = new Intent();
                        intent.putExtra("result", id);
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                    else {
                        Toast.makeText(LogIn.this, "아이디또는 비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LogIn.this,
                            e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public class Task extends AsyncTask<String, Void, String> {

        String sendMsg="", receiveMsg;
        String serverIp = "https://ehtest11-nhnopenlab005-member.toast.paas-ta.com/member/"; // 연결할 jsp주소

        @Override
        protected String doInBackground(String ... params) {
            try {
                String str;
                serverIp += params[0];
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

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
                    Toast.makeText(LogIn.this, "에러", Toast.LENGTH_SHORT).show();;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return receiveMsg;

        }
    }

}