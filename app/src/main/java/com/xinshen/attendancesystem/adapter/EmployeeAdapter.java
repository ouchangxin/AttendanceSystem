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
import com.xinshen.attendancesystem.main.employee.EmployeeBean;
import com.xinshen.attendancesystem.util.glide.GlideCircleTransform;

import java.util.List;

/**
 * Created by thinkpad on 2017/11/15.
 */

public class EmployeeAdapter extends BaseAdapter {
    private List<EmployeeBean> employeeBeanList;
    private LayoutInflater mInflater;
    private Context mContext;

    public EmployeeAdapter(List<EmployeeBean> beanList, Context context){
        this.employeeBeanList = beanList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    public void addItem(EmployeeBean bean){
        if (bean == null)
            return;
        employeeBeanList.add(bean);
        notifyDataSetChanged();
    }
    public void addAll(List<EmployeeBean> beanList){
        if (beanList == null)
            return;
        employeeBeanList.addAll(beanList);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return employeeBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_employee,parent,false);
            holder = new ViewHolder();
            holder.avator = convertView.findViewById(R.id.img_card);
            holder.name = convertView.findViewById(R.id.text_name);
            holder.depart = convertView.findViewById(R.id.text_depart);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        EmployeeBean bean = employeeBeanList.get(position);
        Glide.with(mContext).load(bean.getAvator_url()).transform(new GlideCircleTransform(mContext))
                .into(holder.avator);
        holder.name.setText(bean.getName());
        holder.depart.setText(bean.getDepart());

        return convertView;
    }


    public static class ViewHolder{
        public ImageView avator;
        public TextView name;
        public TextView depart;
    }
}
