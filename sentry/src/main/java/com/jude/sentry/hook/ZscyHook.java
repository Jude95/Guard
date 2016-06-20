package com.jude.sentry.hook;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by Mr.Jude on 2016/6/19.
 */
public class ZscyHook implements IXposedHookLoadPackage {
    XSharedPreferences share;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.mredrock.cyxbs")){
            return;
        }
        share = new XSharedPreferences("com.jude.sentry","Sentry");
        hookZscyTab(loadPackageParam);
    }

    private void hookZscyTab(XC_LoadPackage.LoadPackageParam loadPackageParam){
            XposedHelpers.findAndHookConstructor("com.mredrock.cyxbs.ui.adapter.TabPagerAdapter",
                    loadPackageParam.classLoader,
                    "android.support.v4.app.FragmentManager",
                    "java.util.List",
                    "java.util.List",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            if (share.getBoolean("zscy_tab",false)){
                                List<Fragment> mFragmentsList = (List<Fragment>) XposedHelpers.getObjectField(param.thisObject,"mFragmentsList");
                                mFragmentsList.add(0,mFragmentsList.remove(1));
                                List<String> mTitleList = (List<String>) XposedHelpers.getObjectField(param.thisObject,"mTitleList");
                                mTitleList.add(0,mTitleList.remove(1));
                            }
                        }
                    });

        findAndHookMethod("com.mredrock.cyxbs.ui.activity.MainActivity", loadPackageParam.classLoader,"initView",new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
                if (share.getBoolean("zscy_tab",true)) {
                    LinearLayout mBottomBar = (LinearLayout) XposedHelpers.getObjectField(param.thisObject,"mBottomBar");
                    View view = mBottomBar.getChildAt(1);
                    mBottomBar.removeViewAt(1);
                    mBottomBar.addView(view,0);
                }
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
            }
        });

    }

}
