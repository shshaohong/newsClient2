package com.edu.feicui.newsclient.fragment;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edu.feicui.newsclient.Md5;
import com.edu.feicui.newsclient.R;
import com.edu.feicui.newsclient.activity.UserActivity;
import com.edu.feicui.newsclient.base.BaseActivity;
import com.edu.feicui.newsclient.biz.VersionManager;
import com.edu.feicui.newsclient.entity.MessageEvent;
import com.edu.feicui.newsclient.entity.Version;
import com.edu.feicui.newsclient.listener.FailListener;
import com.edu.feicui.newsclient.listener.SuccessListener;
import com.edu.feicui.newsclient.utils.CommonUtils;
import com.edu.feicui.newsclient.utils.LoadImage;
import com.edu.feicui.newsclient.utils.SharedPreferencesUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administr
 * ator on 2016-11-24.
 */

public class RightMenuFragment extends Fragment {
    //没登陆的
    @BindView(R.id.ll_unLogin)
    LinearLayout unLayout;
    @BindView(R.id.iv_right_unimg)
    ImageView univRightImg;
    @BindView(R.id.tv_unlijidenglu)
    TextView untvRightText;

    //登陆了的
    @BindView(R.id.ll_Login)
    LinearLayout layout;
    @BindView(R.id.iv_right_img)
    ImageView ivRightImg;
    @BindView(R.id.tv_lijidenglu)
    TextView tvRightText;

    @BindView(R.id.iv_weixin)
    ImageView ivWeixin;
    @BindView(R.id.iv_qq)
    ImageView inqq;
    @BindView(R.id.iv_pengyouquan)
    ImageView ivPengyouquan;
    @BindView(R.id.iv_weibo)
    ImageView ivWeibo;

    private long downloadId = -1;
    private DownloadManager downloadManager;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(getActivity(), "19c6a9fee4ca3");

    }
    @OnClick(R.id.iv_weixin) public void shareWeiXin(){
        Platform platform = ShareSDK.getPlatform(getActivity(), Wechat.NAME);
        showShare(platform.getName());
    }

    @OnClick(R.id.iv_qq) public void shareQQ(){
        Platform platform = ShareSDK.getPlatform(getActivity(), QQ.NAME);
        showShare(platform.getName());
    }
    @OnClick(R.id.iv_pengyouquan) public void shareFriend(){
        Platform platform = ShareSDK.getPlatform(getActivity(), WechatMoments.NAME);
        showShare(platform.getName());
    }
    @OnClick(R.id.iv_weibo) public void shareWeibo(){
        Platform platform = ShareSDK.getPlatform(getActivity(), SinaWeibo.NAME);
        showShare(platform.getName());
    }

    private void showShare(String platform) {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭soo授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

//        oks.setPlatform(platform);//取消九宫格的
        // 启动分享GUI
        oks.show(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SharedPreferencesUtils.isLogin(getActivity())) {
            unLayout.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            //设置头像与用户名
            SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            String username = sp.getString("username", "");
            String headImage = sp.getString("headImage", "");
            String localHeadImage = sp.getString("localHeadImage", "");

            tvRightText.setText(username);
            File file = new File(localHeadImage);
            if (file.exists()) {
                ivRightImg.setImageURI(Uri.fromFile(file));
            } else {
                LoadImage loadImage = new LoadImage(getActivity());
                loadImage.displayBitmap(headImage, ivRightImg);
            }

        } else {
            layout.setVisibility(View.GONE);
            unLayout.setVisibility(View.VISIBLE);
        }


    }

    @OnClick(R.id.iv_right_unimg)
    public void publishAddLoginFragmentEvent() {
        MessageEvent event = new MessageEvent();
        event.setType(MessageEvent.TYPE_LOGIN_FRAGMENT);
        event.setFragmentFullName(LoginFragment.class.getName());

        //发布事件
        EventBus.getDefault().post(event);
    }

    @OnClick(R.id.tv_unlijidenglu)
    public void publishAddLoginFragment_2() {

        publishAddLoginFragmentEvent();
    }

    @OnClick(R.id.iv_right_img)
    public void gotoUserCenter() {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.startActivity(UserActivity.class);
    }

    @OnClick(R.id.tv_lijidenglu)
    public void gotoUserCenter2() {
        gotoUserCenter();
    }

//    private AlertDialog dialog;
    @OnClick(R.id.tv_gengxin)
    public void getUpdateVersion() {
//        dialog = new AlertDialog.Builder(getActivity()).setMessage("正在检测更新...").show();
        VersionManager.getUpdateVersion(getActivity(), successListener, failListener);


    }
    private Version md5;
    private SuccessListener successListener = new SuccessListener() {
        @Override
        public void onSuccess(String json) {
            System.out.println("+++++++++++++++++++++++" + json);
            Gson gson = new Gson();
            Version version = gson.fromJson(json, Version.class);
            md5 = gson.fromJson(json,Version.class);
            Version pkgName = gson.fromJson(json,Version.class);

            String md = md5.getMd5();
            String pkg = pkgName.getPkgName();

            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = null;
            try {
                info = manager.getPackageInfo(getActivity().getPackageName(),0);


            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if(!(version.getVersion().equals(info.versionName + ""))){

                System.out.println("+++++++++++++++++++++++" + version.getVersion() + "+++++++++++++" + info.versionName);
                new AlertDialog.Builder(getActivity())
                        .setTitle("更新提示！")
                        .setMessage("发现最新版本,是否更新？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                initDownloadManager();
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
//                dialog.dismiss();
            }else {
                CommonUtils.showShortToast(getActivity(),"已是最新版本");
//                dialog.dismiss();
            }
        }
//        getActivity().getPackageManager().equals()
    };

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        getActivity().registerReceiver(receiver,filter);

    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(receiver);
    }

    private void initDownloadManager(){
        String in = Context.DOWNLOAD_SERVICE;
         downloadManager = (DownloadManager) getActivity().getSystemService(in);
//        Uri uri = Uri.parse("http://192.168.1.101:8080/users/HouseKeeper.apk");
        Uri uri = Uri.parse("http://10.0.1.12:8080/users/HouseKeeper.apk");
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("下载newsClient最新版");
        request.setDescription("使用newsClient最新案例说明");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String data = sdf.format(new Date());
        request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOWNLOADS,"newsClient-" + data + ".apk");

        downloadId = downloadManager.enqueue(request);



    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if(id == downloadId){
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(id);

                Cursor cursor = downloadManager.query(query);
                if(cursor.moveToFirst()){
                    String localFileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                    if(localFileName == null){
                        CommonUtils.showShortToast(getActivity(),"服务器地址失效");
                        return;
                    }
                    File file = new File(localFileName);

                    String result = Md5.md5(file);
                    if(result.equals(md5)){
                        CommonUtils.showShortToast(getActivity(),"md5相同");
                        Intent other = new Intent(Intent.ACTION_VIEW);
                        other.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        other.setDataAndType(Uri.parse("file://" + localFileName),"application/vnd.android.package-archive");
                        context.startActivity(other);
                    }else {
                        CommonUtils.showShortToast(getActivity(),"下载数据丢失");
                    }

                }else {

                }
            }

        }
    };
    private FailListener failListener = new FailListener() {
        @Override
        public void onFail(String json) {
//            dialog.dismiss();
            CommonUtils.showShortToast(getActivity(),"无法检测到最新版本");
        }
    };
}
