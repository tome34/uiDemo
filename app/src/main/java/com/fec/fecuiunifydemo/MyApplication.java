package com.fec.fecuiunifydemo;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * @author tome
 * @date 2018/7/25  16:45
 * @describe ${应用application}
 */
public class MyApplication extends Application{

    public static MyApplication sMyApplication;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sMyApplication = this ;
    }

    //SmartRefreshLayout 配置方式1 :static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context,@NonNull RefreshLayout layout) {
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                //指定为经典Header
                return new ClassicsHeader(sMyApplication);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context,@NonNull RefreshLayout layout) {
                //指定为经典Footer
                return new ClassicsFooter(context).setDrawableSize(20);
        }});

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化二维码扫描
        ZXingLibrary.initDisplayOpinion(this);
    }
}
