package com.wangyuchao.a1500310106wyc_androidsy;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class ExternalStorage {
    /**
     * 检查外部存储是否可读写（可写隐含可读）
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 检查外部存储是否至少可读
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 在外部存储上保存公共文件:创建公共相册的目录
     * @return /外部存储根目录/Pictures/albumName
     */
    public static File getAlbumStorageDir(String albumName) {
        // getExternalStoragePublicDirectory()的参数只能是Environment API常数提供的目录名称
        // 这些目录名称可确保系统正确处理文件。 例如，保存在 DIRECTORY_RINGTONES 中的文件由系统媒体扫描程序归类为铃声，而不是音乐。
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("LOG_TAG", "Directory not created");
        }
        return file;
    }

    /**
     * 在外部存储上保存私有文件:创建个人相册的目录
     * @return /外部存储根目录/Android/data/package_name/Pictures/albumName
     */
    public static File getAlbumStorageDir(Context context, String albumName) {
        // getExternalFilesDir()的参数只能是Environment API常数提供的目录名称
        // 如果没有适合您的名称，可以传null，这将返回外部存储上您的应用的专用目录的根目录
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("LOG_TAG", "Directory not created");
        }
        return file;
    }

    /**
     * 获得外部存储上私有缓存根目录
     * @return /外部存储根目录/Android/data/package_name/cache
     */
    public static File getCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    /**
     * 获得外部存储根目录
     */
    public static File getRoot() {
        return Environment.getExternalStorageDirectory();
    }

}
