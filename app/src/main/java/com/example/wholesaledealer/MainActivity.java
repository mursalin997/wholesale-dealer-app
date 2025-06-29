package com.example.wholesaledealer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.wholesaledealer.data.DbHelper;
import com.example.wholesaledealer.ui.GalleryActivity;

public class MainActivity extends AppCompatActivity {

    EditText productName, model, purchasePrice, sellPrice, customerName, customerPhone;
    ImageView productImage;
    Button captureBtn, saveBtn, galleryBtn;
    Bitmap imageBitmap;

    ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productName = findViewById(R.id.productName);
        model = findViewById(R.id.model);
        purchasePrice = findViewById(R.id.purchasePrice);
        sellPrice = findViewById(R.id.sellPrice);
        customerName = findViewById(R.id.customerName);
        customerPhone = findViewById(R.id.customerPhone);
        productImage = findViewById(R.id.productImage);
        captureBtn = findViewById(R.id.captureBtn);
        saveBtn = findViewById(R.id.saveBtn);
        galleryBtn = findViewById(R.id.galleryBtn);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                productImage.setImageBitmap(imageBitmap);
            }
        });

        captureBtn.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            } else {
                openCamera();
            }
        });

        saveBtn.setOnClickListener(v -> saveRecord());
        galleryBtn.setOnClickListener(v -> startActivity(new Intent(this, GalleryActivity.class)));
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }

    private void saveRecord() {
        try {
            String name = productName.getText().toString();
            String mod = model.getText().toString();
            double buy = Double.parseDouble(purchasePrice.getText().toString());
            double sell = Double.parseDouble(sellPrice.getText().toString());
            String cust = customerName.getText().toString();
            String phone = customerPhone.getText().toString();
            double profit = sell - buy;

            DbHelper db = new DbHelper(this);
            long id = db.insertRecord(name, mod, buy, sell, profit, cust, phone, imageBitmap);
            Toast.makeText(this, "Saved record ID: " + id, Toast.LENGTH_SHORT).show();
            clearFields();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        productName.setText("");
        model.setText("");
        purchasePrice.setText("");
        sellPrice.setText("");
        customerName.setText("");
        customerPhone.setText("");
        productImage.setImageResource(android.R.color.transparent);
    }
}