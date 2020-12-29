package com.example.drawingcanvasalwi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.service.controls.actions.FloatAction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap;
    Button clear,save;
    SignatureView signatureView;
    int path;
    private static final String Image_DIRECTORY="/signdemo";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera_Activity();
            }
        });

        signatureView=findViewById(R.id.signatureview);
        save=findViewById(R.id.save);
        clear=findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signatureView.clearCanvas();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap=signatureView.getSignatureBitmap();
                path=Integer.parseInt(SaveImage(bitmap));
            }
        });

    }

    public void openCamera_Activity() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    private String SaveImage(Bitmap myBitmap) {

        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90,bytes);
        File walpaperDirectory=new File(Environment.getExternalStorageDirectory()+Image_DIRECTORY);
        if (!walpaperDirectory.exists()){
            walpaperDirectory.mkdirs();
            Log.d("hhhh",walpaperDirectory.toString());
        }

        File f=new File(walpaperDirectory, Calendar.getInstance().getTimeInMillis()+".jpg");
        try {
            f.createNewFile();
            FileOutputStream fo=new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(MainActivity.this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            f.getAbsolutePath();
            return f.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
 return "";
    }
}