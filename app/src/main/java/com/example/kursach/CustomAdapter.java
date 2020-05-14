package com.example.kursach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserModel> userModelArrayList;

    public CustomAdapter(Context context, ArrayList<UserModel> userModelArrayList) {

        this.context = context;
        this.userModelArrayList = userModelArrayList;
    }


    @Override
    public int getCount() {
        return userModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.tvdate = (TextView) convertView.findViewById(R.id.date);
            holder.tvvalue = (TextView) convertView.findViewById(R.id.value);
            holder.tvproducer = (TextView) convertView.findViewById(R.id.producer);


            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText("Название: "+userModelArrayList.get(position).getName());
        holder.tvdate.setText("Дата: "+userModelArrayList.get(position).getDate());
        holder.tvvalue.setText("Количество: "+userModelArrayList.get(position).getValue());
        holder.tvproducer.setText("Производитель: "+userModelArrayList.get(position).getProducer());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, tvdate, tvvalue, tvproducer;
    }

}