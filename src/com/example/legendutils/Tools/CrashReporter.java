
package com.example.legendutils.Tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Environment;

/**
 * 将下面两行添加到APP的Application文件的onCreate()方法里。 CrashReporter crashReporter=new
 * CrashReporter(getApplicationContext());
 * Thread.setDefaultUncaughtExceptionHandler(crashReporter);
 * 如果需要Application处理崩溃问题，比如关闭应用程序，则需要调用如下：
 * crashReporter.setOnCrashListener(xxx);
 * 
 * @author NashLegend
 */
public class CrashReporter implements UncaughtExceptionHandler {
    private Context mContext;
    private CrashListener onCrashListener;

    public CrashReporter(Context context) {
        mContext = context;
    }

    /*
     * 发生未能捕获错误的时候调用。
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String infoString = "";
        ByteArrayOutputStream baos = null;
        PrintStream printStream = null;

        String mtypeString = android.os.Build.MODEL;
        String mSystem = android.os.Build.VERSION.RELEASE;

        infoString += "手机型号：" + mtypeString + "\n";
        infoString += "系统版本：" + mSystem + "\n";

        long threadId = thread.getId();
        infoString += ("ThreadInfo : Thread.getName()=" + thread.getName()
                + " id=" + threadId + " state=" + thread.getState() + "\n");
        try {
            baos = new ByteArrayOutputStream();
            printStream = new PrintStream(baos);
            ex.printStackTrace(printStream);
            byte[] data = baos.toByteArray();
            infoString += new String(data);
            data = null;
        } catch (Exception e) {

        } finally {
            try {
                if (printStream != null) {
                    printStream.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        writeCrashLog(infoString);

        if (onCrashListener != null) {
            onCrashListener.onCrash(infoString);
        }
    }

    /**
     * 向磁盘写入错误信息。
     * 
     * @param info 错误信息。
     */
    private void writeCrashLog(String info) {
        FileOutputStream fos = null;
        String path = Environment.getExternalStorageDirectory()
                + "/Android/data/" + mContext.getPackageName()
                + "/Log/CrashLog";
        File file = new File(path);
        try {
            if (file.exists()) {
                file.delete();
            } else {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(info.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取存储在磁盘上的Error Log;
     * 
     * @param context
     * @return
     */
    @SuppressWarnings("resource")
    public static String getCrashLog(Context context) {
        String CrashLog = "";
        File file = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/" + context.getPackageName() + "/Log/CrashLog");
        FileInputStream fis = null;
        try {
            if (file.exists()) {
                byte[] data = new byte[(int) file.length()];
                fis = new FileInputStream(file);
                fis.read(data);
                CrashLog = new String(data);
                data = null;
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return CrashLog;
    }

    /**
     * 返回Crash Log文件；
     * 
     * @param context
     * @return
     */
    public static File getCrashLogFile(Context context) {
        File file = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/" + context.getPackageName() + "/Log/CrashLog");

        return file;
    }

    public static void clearCrashLog(Context context) {
        File file = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/" + context.getPackageName() + "/Log/CrashLog");
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 设置崩溃时的回调
     * 
     * @param crashListener
     */
    public void setOnCrashListener(CrashListener crashListener) {
        onCrashListener = crashListener;
    }

    /**
     * 发生UncaughtException时的回调
     * 
     * @author NashLegend
     */
    public interface CrashListener {
        void onCrash(String info);
    }

}
