package com.edu.feicui.newsclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.feicui.newsclient.R;
import com.edu.feicui.newsclient.entity.Comments;
import com.edu.feicui.newsclient.entity.News;
import com.edu.feicui.newsclient.utils.LoadImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-12-6.
 */

public class CommentsAdapter extends BaseAdapter {

    private Context context;
    private List<Comments> list;
    private LayoutInflater inflater;
    private LoadImage loadImage;

    public CommentsAdapter(Context context, List<Comments> list) {
        this.context = context;
        this.list = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        loadImage = new LoadImage(context);
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

    public void appendDataToAdapter(List<Comments> data, boolean isClear){
        if(data == null || data.size() == 0){
            return;
        }
        if(isClear){
            list.clear();
        }
        list.addAll(data);

    }
    public List<Comments> getData(){
        return  list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.layout_comments_item,null);
            ButterKnife.bind(holder,view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Comments comments = list.get(position);

        loadImage.displayBitmap(comments.getPortrait(),holder.ivIcon);
//        holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        holder.tvUserName.setText(comments.getUid());
        holder.tvComments.setText(comments.getContent());
        holder.tvTime.setText(comments.getStamp());
        return view;
    }

    class ViewHolder{
        @BindView(R.id.iv_comments_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_comment_username)
        TextView tvUserName;
        @BindView(R.id.tv_comment_content)
        TextView tvComments;
        @BindView(R.id.tv_comment_time)
        TextView tvTime;
    }
}
