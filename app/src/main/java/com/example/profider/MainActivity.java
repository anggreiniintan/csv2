package com.example.profider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText fileName, text;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileName = findViewById(R.id.filename);
        text = findViewById(R.id.text);
        button= findViewById(R.id.button);
        cekpermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        checkSDCardStatus();

    }
    private boolean checkSDCardStatus() {
        String SDCardStatus = Environment.getExternalStorageState();

        // MEDIA_UNKNOWN: unrecognized SD card
        // MEDIA_REMOVED: no SD card at all
        // MEDIA_UNMOUNTED: SD card exist but not mounted, not available in Android 4.0+
        // MEDIA_CHECKING: preparing SD card, e.g. powered on and booting
        // MEDIA_MOUNTED: mounted and ready to use
        // MEDIA_MOUNTED_READ_ONLY
        switch (SDCardStatus) {
            case Environment.MEDIA_MOUNTED:
                Toast.makeText(this, "available.", Toast.LENGTH_LONG).show();
                return true;
            case Environment.MEDIA_MOUNTED_READ_ONLY:
                Toast.makeText(this, "SD card is read only.", Toast.LENGTH_LONG).show();
                return false;
            default:
                Toast.makeText(this, "SD card is not available.", Toast.LENGTH_LONG).show();
                return false;
        }
    }


    private boolean isExternalStorageWriteable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.i("State", "writable");
            return true;
        }else{
            return false;
        }
    }

    public void WriteFile(View view){
        if(isExternalStorageWriteable() && cekpermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ){
            File textFile = new File(Environment.getExternalStorageDirectory(), fileName.getText().toString());
            try{
                FileOutputStream fos = new FileOutputStream(textFile);
                fos.write(text.getText().toString().getBytes());
                fos.close();

                Toast.makeText(this, "file saved!", Toast.LENGTH_SHORT).show();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            Toast.makeText(this, "cannt write!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean cekpermission(String permission){
        int cek = ContextCompat.checkSelfPermission(this, permission);
        return (cek == PackageManager.PERMISSION_GRANTED);
    }
}
