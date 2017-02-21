package com.example.balda.listtest.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.example.balda.listtest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;

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

    public static void setLogoImage(final Context context, String logoFileID, final ImageView imageView, StorageReference storageReference) {
        if (logoFileID == null) {
            // load default image
            imageView.setImageResource(R.mipmap.ic_logo_default);
        } else {
            // try to load image from store, then Firebase store
            final String filename = logoFileID;
            if (ImageUtilities.hasExternalStoragePrivateFile(context, filename)) {
                // load image from local cache
                Bitmap logoImage = ImageUtilities.loadImageFromExternalStorage(context, filename);
                imageView.setImageBitmap(logoImage);
            }
            else {
                // load image from firebase
                File imageFile = ImageUtilities.createExternalStoragePrivateFile(context, filename);
                StorageReference imageStorageReference = storageReference.child(filename);
                imageStorageReference.getFile(imageFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap logoImage = ImageUtilities.loadImageFromExternalStorage(context, filename);
                        imageView.setImageBitmap(logoImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // load default image
                        imageView.setImageResource(R.mipmap.ic_logo_default);
                    }
                });
            }
        }
    }
}
