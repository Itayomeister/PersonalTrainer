package com.itay.roadtobattlefield.Activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.Manifest;
import android.widget.Toast;


import com.google.android.material.appbar.MaterialToolbar;
import com.itay.roadtobattlefield.Classes.CameraUtils;
import com.itay.roadtobattlefield.Fragments.HomeFragment;
import com.itay.roadtobattlefield.R;
import com.itay.roadtobattlefield.TraineeLevel;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private MaterialToolbar topBar;
    private CircleImageView imageProfile;
    private EditText editName, editEmail, editPhone;
    private Spinner spinnerLevels;
    private Button btn_Save;
    private ImageButton btn_takePic;

    private ActivityResultLauncher<Intent> cameraLauncher;
    private String currentImagePath;
    private boolean reloaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        topBar = findViewById(R.id.topSettingsBar);
        topBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhoneNumber);
        spinnerLevels = findViewById(R.id.spinnerOptions);
        btn_takePic = findViewById(R.id.btn_takePicture);
        btn_Save = findViewById(R.id.btnSaveSettings);
        imageProfile = findViewById(R.id.imageView_User);
        loadTraineeData();

        loadTraineeData();

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        if (currentImagePath != null) {
                            Uri photoUri = Uri.fromFile(new File(currentImagePath));
                            imageProfile.setImageURI(photoUri);
                        }
                    }
                });

        btn_takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloaded = false;
                CameraUtils.openCamera(SettingsActivity.this, cameraLauncher);
            }
        });

        currentImagePath = CameraUtils.getLastPhotoPath(this);
        if (currentImagePath != null) {
            Uri photoUri = Uri.fromFile(new File(currentImagePath));
            imageProfile.setImageURI(photoUri);
        }

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TraineeLevel traineeLevel = MainActivity.trainee.getTraineeLevel();
                switch (spinnerLevels.getSelectedItem().toString()) {
                    case "Beginner":
                        traineeLevel = TraineeLevel.Begginer;
                        break;
                    case "Intermediate":
                        traineeLevel = TraineeLevel.Intermediate;
                        break;
                    case "Advanced":
                        traineeLevel = TraineeLevel.Advanced;
                        break;
                    case "Hardcore":
                        traineeLevel = TraineeLevel.Hardcore;
                        break;
                }
                if (editPhone.getText().length() == 0 && editName.getText().length() == 0 && editEmail.getText().length() == 0 && MainActivity.trainee.getTraineeLevel() == traineeLevel) {
                    Toast.makeText(SettingsActivity.this, "Please make sure to fill in the desired fields", Toast.LENGTH_SHORT).show();
                }
                if (editEmail.getText().length() > 0)
                    MainActivity.trainee.setEmail(editEmail.getText().toString());
                if (editName.getText().length() > 0)
                    MainActivity.trainee.setFullName(editName.getText().toString());
                if (editPhone.getText().length() > 0)
                    MainActivity.trainee.setPhoneNum(editPhone.getText().toString());
                if (traineeLevel != MainActivity.trainee.getTraineeLevel())
                    MainActivity.trainee.setTraineeLevel(traineeLevel);
                loadTraineeData();
            }
        });

    }

    private void loadTraineeData() {
        editEmail.setHint(MainActivity.trainee.getEmail());
        editName.setHint(MainActivity.trainee.getFullName());
        editPhone.setHint(MainActivity.trainee.getPhoneNum());
        int selectionPos = 0;
        switch (MainActivity.trainee.getTraineeLevel()) {
            case Begginer:
                selectionPos = 0;
                break;
            case Intermediate:
                selectionPos = 1;
                break;
            case Advanced:
                selectionPos = 2;
                break;
            case Hardcore:
                selectionPos = 3;
                break;
        }
        spinnerLevels.setSelection(selectionPos);
        editEmail.setText("");
        editName.setText("");
        editPhone.setText("");
    }

    private void displayProfileImage(String imagePath) {
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            Uri imageUri = Uri.fromFile(imageFile);
            imageProfile.setImageURI(imageUri);
        } else {
            imageProfile.setImageResource(R.drawable.ic_baseline_person_24);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (currentImagePath != null) {
            imageProfile.setImageURI(Uri.fromFile(new File(currentImagePath)));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        currentImagePath = CameraUtils.getLastPhotoPath(SettingsActivity.this);
        if (requestCode == CameraUtils.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (currentImagePath != null) {
                Uri photoUri = Uri.fromFile(new File(currentImagePath));
                imageProfile.setImageURI(photoUri);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CameraUtils.REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, proceed to open the camera
                CameraUtils.openCamera(this, cameraLauncher);
            } else {
                // Camera permission denied, handle it as needed (e.g., show a message)
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}