package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List list;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    class ViewHolder {
        public TextView itemtitle;
        public TextView itemprice;
        public TextView itemdate;
        public TextView itemimage;
    }

    public CustomAdapter(Context context, ArrayList list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.row_item, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.itemtitle = (TextView) convertView.findViewById(R.id.itemtitle);
        viewHolder.itemprice = (TextView) convertView.findViewById(R.id.itemprice);
        viewHolder.itemdate = (TextView) convertView.findViewById(R.id.itemdate);
        viewHolder.itemimage = (TextView) convertView.findViewById(R.id.itemimage);


        final BoardListViewItem boardListViewItem = (BoardListViewItem) list.get(position);
        viewHolder.itemtitle.setText(boardListViewItem.getTitle());
        viewHolder.itemprice.setText(boardListViewItem.getPrice());
        viewHolder.itemdate.setText(boardListViewItem.getDate());
        viewHolder.itemimage.setText(boardListViewItem.getImage());

        return convertView;
    }
}