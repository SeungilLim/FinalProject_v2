package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.MapAddress;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {
    final static int GET_ADR = 2;
    EditText[] et = new EditText[7];
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Button button1 = (Button)findViewById(R.id.signinBtn);
        Button btn2 = (Button)findViewById(R.id.signCanc);
        Button btn3 = (Button)findViewById(R.id.addbtn);
        et[0] = findViewById(R.id.SignId);
        et[1] = findViewById(R.id.Signpw);
        et[2] = findViewById(R.id.SignPhone);
        et[3] = findViewById(R.id.SignAdress);
        et[4] = findViewById(R.id.SignName);
        et[5] = findViewById(R.id.SignLat);
        et[6] = findViewById(R.id.SignLon);
        button1.setOnClickListener(new View.OnClickListener() {
            List<Member> list = new ArrayList<Member>();
            @Override
            public void onClick(View v) {
                try {
                    try {
                        a = Integer.parseInt(et[2].getText().toString());
                    } catch (Exception e) {

                    }
                    if (et[0].getText().toString().isEmpty() || et[4].getText().toString().isEmpty()
                            || et[3].getText().toString().isEmpty()|| et[5].getText().toString().isEmpty()|| et[6].getText().toString().isEmpty()) {
                        Toast.makeText(SignIn.this,
                                "필수항목이 빠졌습니다.", Toast.LENGTH_SHORT).show();
                    }
                    if (et[2].getText().toString().isEmpty()) {
                        Toast.makeText(SignIn.this,
                                "필수항목이 빠졌습니다.", Toast.LENGTH_SHORT).show();
                    }

                    else if (a==0){
                        Toast.makeText(SignIn.this,
                                "전화번호는 숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String[] params = new String[7];
                        for (int i = 0; i < params.length; i++) {
                            params[i] = et[i].getText().toString().trim();
                        }
                        String rst = String.valueOf(new Task().execute(params).get());
                        if (rst.equals("0")){
                            Toast.makeText(SignIn.this,
                                    "아이디 중복입니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapAddress.class);
                startActivityForResult(intent,GET_ADR);
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

                serverIp += (params[0]+"?name="+params[1]+"&phone="+params[2]+"&address="+params[3]+"&password="+params[4]+"&latitude="+params[5]+"&longitude="+params[6]);

                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("PUT");  //수정하고자 하면 POST, PUT-> 데이터를 DB 올리겠다.
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
                    Toast.makeText(SignIn.this, "아이디중복입니다.", Toast.LENGTH_SHORT).show();;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return receiveMsg;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case GET_ADR:
                Toast.makeText(SignIn.this,"주소를 등록했습니다.",Toast.LENGTH_SHORT).show();
                if (resultCode == RESULT_OK){
                    double lat = data.getDoubleExtra("lat1",0);
                    double lng = data.getDoubleExtra("lng1",0);
                    et[5].setText(Double.toString(lat));
                    et[6].setText(Double.toString(lng));
                }
                break;
        }
    }
}