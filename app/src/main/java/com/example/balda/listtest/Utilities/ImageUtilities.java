package com.example.balda.listtest.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by balda on 17.02.2017.
 */

public class ImageUtilities {
    public static boolean hasExternalStoragePrivateFile(Context context, String filename) {
        File file = new File(context.getExternalFilesDir(null), filename);
        if (file != null) {
            return file.exists();
        }
        return false;
    }

    public static Bitmap loadImageFromExternalStorage(Context context, String filename) {
        File file = new File(context.getExternalFilesDir(null), filename);
        return BitmapFactory.decodeFile(file.getPath());
    }

    public static File createExternalStoragePrivateFile(Context context, String filename) {
        // Create a path where we will place our private file on external
        // storage.
        return new File(context.getExternalFilesDir(null), filename);
    }

    public static void deleteExternalStoragePrivateFile(Context context, String filename) {
        // Get path for the file on external storage.  If external
        // storage is not currently mounted this will fail.
        File file = new File(context.getExternalFilesDir(null), filename);
        if (file != null) {
            file.delete();
        }
    }
}
