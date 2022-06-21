package com.example.finalproject;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FragmentHome extends Fragment {

    ListView listView;
    ArrayList<BoardListViewItem> boardListViewItems;
    BitmapConverter bitmapConverter;
    private CustomAdapter customAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentHome.GetBoardList getBoardList = new FragmentHome.GetBoardList();
        getBoardList.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        listView = rootView.findViewById(R.id.homeListView);

        boardListViewItems = new ArrayList<>();

        GetBoardList getBoardList = new GetBoardList();
        getBoardList.execute();

        customAdapter = new CustomAdapter(getContext(), boardListViewItems);
        listView.setAdapter(customAdapter);

        customAdapter.notifyDataSetChanged();

        return rootView;
    }


    class GetBoardList extends AsyncTask<Void, Void, String> {
        String sendMsg="", receiveMsg;
        String serverIp = "https://finalproject-308377.toast.paas-ta.com/Board/all"; // 연결할 jsp주소
        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);


            boardListViewItems.clear();

            try {

                JSONArray jsonArray = new JSONArray();

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String title = jsonObject.optString("title");
                    int boardId = jsonObject.optInt("boardId");
                    int price = jsonObject.optInt("price");
                    //Bitmap image = BitmapConverter.StringToBitmap(jsonObject.optString("boardImage"));
                    String image = "https://drive.google.com/file/d/14eb8omNlfxKp4dEFo8sMQyNYo7zZLbwC/view?usp=sharing";
                    String date = jsonObject.optString("updatedAt");

                    boardListViewItems.add(new BoardListViewItem(title, boardId, price, image, date));


                    /*titleList.add(title);
                    boardIdList.add(boardId);*/

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                    Intent intent = new Intent(getContext(), BoardDetail.class);
                    intent.putExtra("boardId", boardListViewItems.get(position).getBoardId());
                    //intent.putExtra("userid", userid);
                    startActivity(intent);
                }
            });


        }


        @Override
        protected String doInBackground(Void ... Void) {
            try {
                String str;
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
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return receiveMsg;

        }
    }


}