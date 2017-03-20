package com.example.loginandpaytools.base.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linsawako on 2017/2/22.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final boolean DEBUG = true;

    public static final String PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/PayLibrary/crash";
    public static final String FILE_NAME = "Crash";
    public static final String FILE_NAME_SUFFIX = ".trace";
    private Context mContext;
    private Thread.UncaughtExceptionHandler mCrashHandler;
    private static CrashHandler sInstance = new CrashHandler();

    public void init(Context context) {
        mCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    private CrashHandler() {}

    public static CrashHandler getInstance() {
        return sInstance;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long currentTime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yy-MM-dd hh:mm:ss").format(new Date(currentTime));
        File file = new File(dir, FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.print(time);
            savePhoneInfo(pw);
            pw.println();
            pw.println();
            e.printStackTrace(pw);
            pw.close();
        } catch (IOException | PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        if (mCrashHandler != null) {
            mCrashHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void savePhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo pi = packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);

        pw.print("Android OS Version:");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        pw.print("Phone:");
        pw.println(Build.MANUFACTURER);

        pw.print("Model:");
        pw.println(Build.MODEL);

    }
}

