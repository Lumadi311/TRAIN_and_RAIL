package com.example.trainrail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (hasConnection(MainActivity.this)) {
            //call methods
            //getJsonData();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else {
            showNetDisabledAlertToUser(MainActivity.this);
        }
    }

    public boolean hasConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    public static void showNetDisabledAlertToUser(final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL);
        alertDialogBuilder.setMessage("Would you like to enable it?")
                .setTitle("No Internet Connection")
                .setPositiveButton(" Enable Internet ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(dialogIntent);
                    }
                });

        alertDialogBuilder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static Bitmap generateQR(String content) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;

        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}