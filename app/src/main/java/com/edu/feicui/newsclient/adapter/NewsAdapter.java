package com.edu.feicui.newsclient.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.feicui.newsclient.R;
import com.edu.feicui.newsclient.entity.News;
import com.edu.feicui.newsclient.utils.LoadImage;
import com.edu.feicui.newsclient.utils.VolleyHttp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-11-29.
 */

public class NewsAdapter extends BaseAdapter {

    private Context context;
    private List<News> list = new ArrayList<>();
    private LayoutInflater inflater;
    private LoadImage loadImage;

    public NewsAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.loadImage = new LoadImage(context);
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

    public List<News> getData(){
        return list;
    }

    public void appendDataToAdapter(List<News> data,boolean isClear){
        if(data == null || data.size() == 0){
            return;
        }
        if(isClear){
            list.clear();
        }
        list.addAll(data);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_listview_item,null);
            holder = new ViewHolder();
            ButterKnife.bind(holder,convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        News news = list.get(position);
        holder.tvContent.setText(news.getSummary());
        holder.tvTitle.setText(news.getTitle());
        holder.tvDate.setText(news.getStamp());
        loadImage.displayBitmap(news.getIcon(),holder.imageView);

        return convertView;
    }
    class ViewHolder{
        @BindView(R.id.iv_icon)
        ImageView imageView;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_date)
        TextView tvDate;
    }
}
