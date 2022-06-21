package com.example.finalproject;

import static com.example.finalproject.MemberMainActivity.LOG_IN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Toast;

public class SignCheck1 extends AppCompatActivity {
    Button btn1, btn2, btn3;
    CheckBox ch1, ch2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_check1);
        btn1 = findViewById(R.id.allOk);
        btn2 = findViewById(R.id.signNext);
        btn3 = findViewById(R.id.signCancel);
        ch1 = findViewById(R.id.useCheck);
        ch2 = findViewById(R.id.locCheck);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ch1.setChecked(true);
                ch2.setChecked(true);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ch1.isChecked() && ch2.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), SignIn.class);
                    startActivityForResult(intent,LOG_IN);
                    finish();
                }
                else{
                    Toast.makeText(SignCheck1.this,
                            "필수항목에 체크해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}