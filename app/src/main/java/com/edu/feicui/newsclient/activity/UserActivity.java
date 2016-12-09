package com.edu.feicui.newsclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.edu.feicui.newsclient.MainActivity;
import com.edu.feicui.newsclient.R;
import com.edu.feicui.newsclient.adapter.LoginLogAdapter;
import com.edu.feicui.newsclient.base.BaseActivity;
import com.edu.feicui.newsclient.biz.UserManager;
import com.edu.feicui.newsclient.entity.BaseEntity;
import com.edu.feicui.newsclient.entity.LoginLog;
import com.edu.feicui.newsclient.entity.UserResponse;
import com.edu.feicui.newsclient.entity.Users;
import com.edu.feicui.newsclient.listener.FailListener;
import com.edu.feicui.newsclient.listener.SuccessListener;
import com.edu.feicui.newsclient.parser.UserParser;
import com.edu.feicui.newsclient.utils.CommonUtils;
import com.edu.feicui.newsclient.utils.LoadImage;
import com.edu.feicui.newsclient.utils.SharedPreferencesUtils;
import com.edu.feicui.newsclient.utils.Url;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.edu.feicui.newsclient.MainActivity.slidingMenu;

/**
 * Created by Administrator on 2016-12-5.
 */

public class UserActivity extends BaseActivity {

    @BindView(R.id.rl_user)
    LinearLayout linearLayout;
    @BindView(R.id.iv_user_back)
    ImageView ivBack;
    @BindView(R.id.iv_user_icon)
    ImageView ivIcon;

    @BindView(R.id.tv_username)
    TextView tvName;
    @BindView(R.id.tv_jifen)
    TextView tvIntegral;
    @BindView(R.id.tv_tieshu)
    TextView tvCommentCount;

    @BindView(R.id.lv_userListView)
    ListView listView;
    @BindView(R.id.btn_userBack)
    Button btnUserBack;

    LoadImage loadImage;
    LoginLogAdapter loginLogAdapter;
    PopupWindow popupWindow;

    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        loadImage = new LoadImage(this);
        //从本地显示用户名与头像
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        tvName.setText(sp.getString("username", ""));
        String headImage = sp.getString("headImage", "");
        String localHeadImage = sp.getString("localHeadImage", "");
        File file = new File(localHeadImage);
        if(file.exists()){
            Uri uri = Uri.fromFile(file);
            ivIcon.setImageURI(uri);
        }else{
            loadImage.displayBitmap(headImage, ivIcon);
        }

        loginLogAdapter = new LoginLogAdapter(this);
        listView.setAdapter(loginLogAdapter);

        initListener();
        initPopupWindow();
        sendRequest();
    }
    private void initListener() {
        ivBack.setOnClickListener(listener);
        ivIcon.setOnClickListener(listener);
        btnUserBack.setOnClickListener(listener);

    }

    private void initPopupWindow(){
        View view = getLayoutInflater().inflate(R.layout.popup_photo_layout,null);
        LinearLayout llTakePhoto = (LinearLayout) view.findViewById(R.id.ll_take_photo);
        LinearLayout llSelectPhoto = (LinearLayout) view.findViewById(R.id.ll_sel_photo);

        llTakePhoto.setOnClickListener(listener);
        llSelectPhoto.setOnClickListener(listener);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

    }



    private void sendRequest(){
        UserManager.getUserInfo(this, new SuccessListener() {
            @Override
            public void onSuccess(String json) {
                System.out.println("---------------------------------------"  + json);
                BaseEntity<Users> baseEntity = UserParser.parseUsers(json);
                if(baseEntity.getStatus().equals("0")){//成功获取
                    Users user = baseEntity.getData();
                    tvName.setText(user.getUid());
                    tvIntegral.setText("积分: " + user.getIntergration());
                    tvCommentCount.setText("跟帖数统计：" + String.valueOf(user.getComnum()));

                    if(!TextUtils.isEmpty(user.getPortrait())){
                        loadImage.displayBitmap(user.getPortrait(),ivIcon);
                    }

                    SharedPreferencesUtils.saveUser(UserActivity.this,user);

                    List<LoginLog> loginLog = user.getLoginLog();

                    loginLogAdapter.appendDataToAdapter(loginLog);

                }else {
                    CommonUtils.showShortToast(UserActivity.this,"获取失败");
                }
            }
        }, new FailListener() {
            @Override
            public void onFail(String json) {

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //拍照请求返回
        if(requestCode == 100){
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle = intent.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                save(bitmap);
            }

        }else if(requestCode == 200){
            if(resultCode == Activity.RESULT_OK){
                Uri originalUri = intent.getData();
                System.out.println("===========================" + originalUri);
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),originalUri);
                    save(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.iv_user_back:
                    startActivity(new Intent(UserActivity.this,MainActivity.class));
                    if(slidingMenu != null && slidingMenu.isMenuShowing()){
                        slidingMenu.showContent();
                    }
                    break;
                case R.id.iv_user_icon:
                    popupWindow.showAtLocation(linearLayout, Gravity.BOTTOM,0,0);
                    break;
                case R.id.btn_userBack:
                    SharedPreferencesUtils.clearUser(UserActivity.this);

                    startActivity(new Intent(UserActivity.this, MainActivity.class));
                    finish();
                    break;
                case R.id.ll_take_photo:
                    popupWindow.dismiss();
                    takePhoto();
                    break;
                case R.id.ll_sel_photo:
                    popupWindow.dismiss();
                    selectPhoto();
                    break;
            }
        }
    };

    //通过拍照获取图片
    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }
    //从图片库中获取图片
    private void selectPhoto() {
        //想获取的内容
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 200);
    }
//        intent.putExtra("crop","true");
//        intent.putExtra("aspectX",1);
//        intent.putExtra("aspectY",1);
//        intent.putExtra("outputX",80);

//        intent.putExtra("outputY",80);
//        intent.putExtra("scale","true");
//        intent.putExtra("return-data","true");




    File cacheDir = null;
    private void save(Bitmap bitmap){
         cacheDir = new File(getCacheDir(),"newsClient");
        if(!cacheDir.exists()){
            cacheDir.mkdirs();
        }
        File file = new File(cacheDir,"headImage.jpg");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)){
                //发送网络请求上传头像
                UserManager.uploadUserImage(UserActivity.this, Url.USER_IMAGE,file,successListener,failListener);
                //图片路径缓存
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    private SuccessListener successListener = new SuccessListener() {
        @Override
        public void onSuccess(String json) {
            System.out.println("-------------------" + json);
            //解析返回的json字符串
            Gson gson = new Gson();
            BaseEntity<UserResponse> baseEntity = gson.fromJson(json,new TypeToken<BaseEntity<UserResponse>>(){}.getType());
            if(baseEntity.getStatus().equals("0")){
                if(baseEntity.getData().getResult() == 0){
                    ivIcon.setImageBitmap(bitmap);
                    File file = new File(cacheDir,"headImage.jpg");
                    SharedPreferencesUtils.saveUserHeadImagePath(UserActivity.this,file.getPath());
                    return;
                }
            }
            CommonUtils.showShortToast(UserActivity.this,"上传失败");
        }
    };
    private FailListener failListener = new FailListener() {
        @Override
        public void onFail(String json) {
            CommonUtils.showShortToast(UserActivity.this,"上传失败");
        }
    };

}
