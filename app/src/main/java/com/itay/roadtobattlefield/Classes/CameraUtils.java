package com.itay.roadtobattlefield.Classes;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;


import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.itay.roadtobattlefield.Activities.MainActivity;
import com.itay.roadtobattlefield.Activities.SettingsActivity;
import com.itay.roadtobattlefield.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class CameraUtils {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_CAMERA_PERMISSION = 1001;

    public static void openCamera(Activity activity, ActivityResultLauncher<Intent> launcher) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            // Permission is already granted, proceed to open the camera
            openCameraInternal(activity, launcher);
        }
    }

    private static void openCameraInternal(Activity activity, ActivityResultLauncher<Intent> launcher) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = createImageFile(activity);
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(activity,
                        activity.getPackageName() + ".fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                launcher.launch(takePictureIntent);
            }
        }
    }

    public static String getLastPhotoPath(Context context) {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), context.getResources().getString(R.string.app_name));
        if (storageDir.exists()) {
            File[] files = storageDir.listFiles();
            if (files != null && files.length > 0) {
                Arrays.sort(files, new Comparator<File>() {
                    public int compare(File f1, File f2) {
                        return Long.compare(f2.lastModified(), f1.lastModified());
                    }
                });
                return files[0].getAbsolutePath();
            }
        }
        return null;
    }

    private static File createImageFile(Context context) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";
        String FOLDER_NAME = context.getResources().getString(R.string.app_name);
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), FOLDER_NAME);
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Toast.makeText(context, "Failed to create directory", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        return new File(storageDir.getPath() + File.separator + imageFileName);
    }

    public static void loadPhoto(Context context, String imagePath) {
        File photoFile = new File(imagePath);
        Uri photoUri = Uri.fromFile(photoFile);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(photoUri);
        context.sendBroadcast(mediaScanIntent);
        Toast.makeText(context, "Photo saved to gallery", Toast.LENGTH_SHORT).show();
    }
}
