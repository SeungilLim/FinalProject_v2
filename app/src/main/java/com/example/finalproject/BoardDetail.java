package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BoardDetail extends AppCompatActivity {

    final private String TAG = getClass().getSimpleName();


    TextView textViewTitle, textViewContent;
    LinearLayout commentLayout;
    EditText commentEditText;
    Button commentRegisterButton;


    String boardId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        boardId = getIntent().getStringExtra("userid");
        textViewTitle = findViewById(R.id.TextViewTitle);
        textViewContent = findViewById(R.id.TextViewContent);
        commentLayout = findViewById(R.id.comment);
        commentEditText = findViewById(R.id.commentEditText);
        commentRegisterButton = findViewById(R.id.commentRegisterButton);

        commentRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        InitData();

    }

    private void InitData() {

        LoadBoard loadBoard = new LoadBoard();
        loadBoard.execute(boardId);

    }

    //게시판 정보 불러오기
    class LoadBoard extends AsyncTask<String, Void, String> {
        String sendMsg = "", receiveMsg;
        String serverIp = "https://ehtest14-openlab05-26.toast.paas-ta.com/user/all"; // 연결할 jsp주소

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONArray jsonArray = null;
                jsonArray = new JSONArray(s);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                    String title = jsonObject.optString("title");
                    String content = jsonObject.optString("content");

                    textViewTitle.setText(title);
                    textViewContent.setText(content);

                    LoadComment loadComment = new LoadComment();
                    loadComment.execute(boardId);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        @Override
        protected String doInBackground(String... voids) {
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

    //게시판 댓글 불러오기
    class LoadComment extends AsyncTask<String, Void, String> {

        String sendMsg = "", receiveMsg;
        String serverIp = "https://ehtest14-openlab05-26.toast.paas-ta.com/user/all"; // 연결할 jsp주소

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONArray jsonArray = null;
                jsonArray = new JSONArray(s);

                LayoutInflater inflater = LayoutInflater.from(BoardDetail.this);

                for (int i = 0; i < jsonArray.length(); i++) {

                    View commentView = inflater.inflate(R.layout.comment, null);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                    String userid = jsonObject.optString("userid");
                    String registerdate = jsonObject.optString("registerdate");
                    String content = jsonObject.optString("content");

                    ((TextView) commentLayout.findViewById(R.id.commentUserid)).setText(userid);
                    ((TextView) commentLayout.findViewById(R.id.commentRegisterDate)).setText(registerdate);
                    ((TextView) commentLayout.findViewById(R.id.commentContent)).setText(content);

                    commentLayout.addView(commentView);
                }
            } catch (JSONException e) {

            }
        }

        protected String doInBackground(String... voids) {
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
    //댓글 등록 함수
    class RegisterComment extends AsyncTask<String, Void, String> {

        String sendMsg="", receiveMsg = "0";
        String serverIp = "https://projectDB-openlab05-26.toast.paas-ta.com/project/"; // 연결할 jsp주소

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result.equals("success")){


                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(commentEditText.getWindowToken(), 0);


                Toast.makeText(BoardDetail.this, "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();

                LoadComment loadComment = new LoadComment();
                loadComment.execute(boardId);
            }else
            {
                Toast.makeText(BoardDetail.this, "에러", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            try {
                String str;

                serverIp += (params[0] + "?title=" + params[1] + "&content=" + params[2] + "&author=" + params[3] + "&createdAt=" + params[4]);

                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("PUT");//DB 등록시 PUT? 수정시 POST?
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                osw.write(sendMsg);
                osw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
}