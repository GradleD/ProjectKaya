package com.durvasa.projectkaya;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStorageDirectory;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatActivity activity = DisplayActivity.this;
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
    TextView heading;
    // Context context;
    // ImageView imageView;
    Button shopbtn, sharebtn;
    private Bitmap bitmap;
    Date now = new Date();
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        t1 = findViewById(R.id.tvName);
        t2 = findViewById(R.id.tvAge);
        t3 = findViewById(R.id.tvHeight);
        t4 = findViewById(R.id.tvWeight);
        t5 = findViewById(R.id.tvWaist);
        t6 = findViewById(R.id.tvSeat);
        t7 = findViewById(R.id.tvTheigh);
        t8 = findViewById(R.id.tvNeck);
        t9 = findViewById(R.id.tvChest);
        t10 = findViewById(R.id.tvShoulder);
        // imageView = findViewById(R.id.ivss);
        sharebtn = findViewById(R.id.sharebtn);
        shopbtn = findViewById(R.id.btnShop);
        heading = findViewById(R.id.headingMeasure);
        Bundle b = getIntent().getExtras();
        t1.setText("Name:" + b.getString("Name"));
        t2.setText("Age:" + b.getInt("Age"));
        t3.setText("Height:" + b.getDouble("Height"));
        t4.setText("Weight:" + b.getInt("Weight"));
        t5.setText("Waist:" + b.getDouble("Waist"));
        shopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent shopIntent = new Intent(DisplayActivity.this,.class);
                // startActivity(shopIntent);
                //finish();
            }
        });
        sharebtn.setOnClickListener(this);
        //Measurement Computations Starts Here

        Double seat = new Double(b.getDouble("Waist"));
        seat += 4;

        t6.setText("Seat: " + df2.format(seat));

        Double thigh;
        thigh = seat / 3 * 2;
        t7.setText("Thigh: " + df2.format(thigh));

        Double Neck;
        Neck = b.getDouble("Waist");
        Neck = Neck / 2 - 1;
        t8.setText("Neck:" + df2.format(Neck));

        Double Chest;
        Chest = b.getDouble("Neck");
        Chest = (Neck * 2) + 3;
        t9.setText("Chest:" + df2.format(Chest));

        Double Shoulder;
        Shoulder = b.getDouble("Chest");
        Shoulder = Chest / 2;
        t10.setText("Shoulder:" + Shoulder);

    }

    @Override
    public void onClick(View v) {
        requestPermissionAndSave();
        bitmap = ScreenshotUtil.getInstance().takeScreenshotForScreen(activity);


    }


    private void requestPermissionAndSave() {


        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        if (bitmap != null) {
                            String path = Environment.getExternalStorageDirectory().toString() + "/download/" + now + ".png";
                            FileUtil.getInstance().storeBitmap(bitmap, path);
                            Toast.makeText(getApplicationContext(), "Success!" + " " + path, Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();



        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setDataAndType(Uri.fromFile(new File(getExternalStorageDirectory()+"/download/")),"image/*");
        startActivity(Intent.createChooser(shareIntent, "Share Via..."));

    }



}





