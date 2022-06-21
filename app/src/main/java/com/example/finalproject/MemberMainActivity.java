package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.LogIn;

public class MemberMainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4, btn5;
    TextView tv;
    String id;
    final static int SIGN_IN = 0;
    final static int LOG_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_main);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);
        btn1 = findViewById(R.id.login);
        btn2 = findViewById(R.id.signin);
        btn3 = findViewById(R.id.myInf);
        btn4 = findViewById(R.id.logout);
        btn5 = findViewById(R.id.nearCheck);
        tv = findViewById(R.id.mainText);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivityForResult(intent,LOG_IN);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignCheck1.class);
                startActivityForResult(intent,SIGN_IN);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyInf1.class);
                intent.putExtra("ID", id);
                startActivityForResult(intent,LOG_IN);
            }

        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NearCheck1.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("");
                btn3.setVisibility(View.GONE);
                btn4.setVisibility(View.GONE);
                btn5.setVisibility(View.GONE);
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
                id = null;
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case LOG_IN:
                if (resultCode == RESULT_OK){
                    id = data.getStringExtra("result");
                    Toast.makeText(MemberMainActivity.this,
                            "로그인 되었습니다.", Toast.LENGTH_LONG).show();
                    tv.setText(id+"님 환영합니다.");
                    btn5.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
                    btn4.setVisibility(View.VISIBLE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);

                }
                break;
        }
    }
}