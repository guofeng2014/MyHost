package com.example.myhost;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * create by guofeng
 * date on 2019-09-24
 */
public class APKLoadUtil {

    public static APKLoadUtil getInstance() {
        return instance;
    }

    private static APKLoadUtil instance = new APKLoadUtil();

    private APKLoadUtil() { }

    private final Map<String, PlugPackage> cache = new HashMap<>();


    public void initAllPlugINfo(Activity activity, List<String> apkList) {
        for (String path : apkList) {
            File apk = new File(path);
            PlugPackage plugPackage = cache.get(apk.toString());
            if (plugPackage == null) {
                plugPackage = new PlugPackage();
                plugPackage.classLoader = createDexClassLoader(activity, apk);
                plugPackage.assetManager = createAssetManager(apk);
                plugPackage.packageInfo = getPackageInfo(activity, apk);
                plugPackage.defaultActivity = getDefaultActivity(plugPackage.packageInfo);
                if (plugPackage.packageInfo != null) {
                    plugPackage.packageName = plugPackage.packageInfo.packageName;
                }
                plugPackage.resources = createResource(activity, plugPackage.assetManager);
                cache.put(apk.toString(), plugPackage);
            }
        }
    }



    //获得APK的插件信息
    public PlugPackage getDexPlugPackage(File apk) {
        return cache.get(apk.toString());
    }

    private String getDefaultActivity(PackageInfo packageInfo) {
        if (packageInfo != null) {
            if (packageInfo.activities != null && packageInfo.activities.length > 0) {
                return packageInfo.activities[0].name;
            }
        }
        return "";
    }

    private Resources createResource(Activity activity, AssetManager assetManager) {
        Resources superRes = activity.getResources();
        Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        return resources;
    }

    private AssetManager createAssetManager(File apk) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apk.toString());
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PackageInfo getPackageInfo(Activity activity, File apk) {
        PackageManager pm = activity.getPackageManager();
        return pm.getPackageArchiveInfo(apk.toString(), PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
    }

    private DexClassLoader createDexClassLoader(Activity activity, File apk) {
        File dexOutputDir = activity.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apk.toString(), dexOutputDir.toString(), null, activity.getClassLoader());
    }


}
