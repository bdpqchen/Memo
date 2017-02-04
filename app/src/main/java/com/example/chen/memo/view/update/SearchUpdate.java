package com.example.chen.memo.view.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;


import com.example.chen.memo.bean.GithubUpdate;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chen on 17-2-2.
 */

public class SearchUpdate {

    private Handler mHandler;
    private Context mContext;
    private final String version_url = "https://raw.githubusercontent.com/bdpqchen/UpdateApps/master/memo/latest/latest.json";
    private boolean isAuto;
    private DialogInterface.OnClickListener newApkDownLoadListener;

    public SearchUpdate(Context context){
        this.mContext = context;
        this.mHandler = new Handler();

    }

    public void checkUpdate(final int versionCode, int delayTime) {

        LogUtils.i("versionCode", String.valueOf(versionCode));
        this.isAuto = delayTime != 0;
        this.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        //获取成功
                        GithubUpdate githubUpdate = (GithubUpdate) msg.obj;
                        LogUtils.i("gson parse successful", githubUpdate.version_name);
                        if(githubUpdate.version_code > versionCode){
                            showUpdateDialog(githubUpdate.new_features);
                        }else{
                            if (!isAuto){
                                ToastUtils.showMessage(mContext, "已是最新版本");
                            }
                        }
                        break;
                    case 0:
                        if(!isAuto){
                            ToastUtils.showMessage(mContext, "网络请求失败, 请检查网络");
                        }
                        break;
                }
            }
        };

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                executeTask();
            }
        }, delayTime);

        newApkDownLoadListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 启动后台服务下载apk
                Intent service = new Intent(mContext, UpdateService.class);
                mContext.startService(service);
            }
        };

    }

    private void executeTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(version_url).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Message msg = new Message();
                        String jsonString = response.body().string();
                        LogUtils.i("successful", jsonString);
                        GithubUpdate githubUpdate = new Gson().fromJson(replaceBlank(jsonString), GithubUpdate.class);
                        msg.obj = githubUpdate;
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }else{
                        Message msg = new Message();
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showUpdateDialog(String new_features) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("发现最新版本");
        builder.setMessage("新特性：" + "\n" + "    " + new_features);
        builder.setPositiveButton("下载更新", newApkDownLoadListener);
        builder.show();
    }



    private static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
