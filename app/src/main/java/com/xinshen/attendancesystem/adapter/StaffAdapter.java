package com.xinshen.attendancesystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.util.glide.GlideCircleTransform;

import java.util.List;

/**
 * Created by thinkpad on 2017/11/15.
 */

public class StaffAdapter extends BaseAdapter {
    private List<StaffBean> staffList;
    private LayoutInflater mInflater;
    private Context mContext;

    public StaffAdapter(List<StaffBean> beanList, Context context){
        this.staffList = beanList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return staffList.size();
    }

    @Override
    public Object getItem(int position) {
        return staffList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_preson,parent,false);
            holder = new ViewHolder();
            holder.avator = convertView.findViewById(R.id.img_card);
            holder.name = convertView.findViewById(R.id.text_name);
            holder.depart = convertView.findViewById(R.id.text_depart);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        StaffBean bean = staffList.get(position);
        Glide.with(mContext).load(bean.getImgURL()).transform(new GlideCircleTransform(mContext))
                .into(holder.avator);
        holder.name.setText(bean.getName());
        holder.depart.setText(bean.getDepart());

        return convertView;
    }


    class ViewHolder{
        public ImageView avator;
        public TextView name;
        public TextView depart;
    }
}
