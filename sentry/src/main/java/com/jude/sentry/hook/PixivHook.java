package com.jude.sentry.hook;

import android.view.View;
import android.widget.FrameLayout;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by Mr.Jude on 2016/6/18.
 */
public class PixivHook implements IXposedHookLoadPackage {
    XSharedPreferences share;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("jp.pxv.android")){
            return;
        }
        share = new XSharedPreferences("com.jude.sentry","Sentry");
        hookPixivAd(loadPackageParam);
    }

    private void hookPixivAd(XC_LoadPackage.LoadPackageParam loadPackageParam){
        findAndHookMethod("jp.pxv.android.activity.AdActivity", loadPackageParam.classLoader,"setContentView",int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
                share.reload();
                FrameLayout mAdContainer = (FrameLayout) XposedHelpers.getObjectField(param.thisObject,"mAdViewContainer");
                if (share.getBoolean("pixiv_ad",false)){
                    mAdContainer.setVisibility(View.GONE);
                }else {
                    mAdContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
