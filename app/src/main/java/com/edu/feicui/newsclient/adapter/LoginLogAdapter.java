package com.edu.feicui.newsclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.feicui.newsclient.R;
import com.edu.feicui.newsclient.entity.LoginLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-12-7.
 */

public class LoginLogAdapter extends BaseAdapter {

    private List<LoginLog> list = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public LoginLogAdapter( Context context) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void appendDataToAdapter(List<LoginLog> dataList){
        if(dataList == null || dataList.size() == 0){
            return;
        }
        list.clear();
        list.addAll(dataList);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.loginlog_list_item,null);
            holder = new ViewHolder();
            ButterKnife.bind(holder,view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        LoginLog loginLog = list.get(position);
        holder.tvTime.setText(loginLog.getTime().split("\\s+")[0]);
        holder.tvAddress.setText(loginLog.getAddress());
        holder.tvDevice.setText(loginLog.getDevice() == 0 ? "手机客户端" : "PC客户端");

        return view;
    }
    class ViewHolder{
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_device)
        TextView tvDevice;
    }
}
