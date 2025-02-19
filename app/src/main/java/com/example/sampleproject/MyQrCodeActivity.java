package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MyQrCodeActivity extends AppCompatActivity {

    ImageView img_qr_code;
    public static final int QRCodeWidth =500;
    public static final int QRCodeHeight =500;
    Bitmap bitmap;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String student_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qr_code);

        setTitle("My QR code");

        preferences= PreferenceManager.getDefaultSharedPreferences(MyQrCodeActivity.this);
        editor=preferences.edit();
        student_name=preferences.getString("student_name","");
        Toast.makeText(this, ""+student_name, Toast.LENGTH_SHORT).show();

        img_qr_code=findViewById(R.id.iv_my_qr_code_qrcode);
        try {
            createQRCode();
        }
        catch(WriterException e)
        {
            e.printStackTrace();
        }

    }

    private void createQRCode() throws WriterException {
        bitmap= TextToImageEncode(student_name);
        img_qr_code.setImageBitmap(bitmap);

    }

    private Bitmap TextToImageEncode(String username) throws WriterException{
        BitMatrix bitMatrix;

        try {
            bitMatrix = new MultiFormatWriter().encode(username,BarcodeFormat.QR_CODE,QRCodeWidth,QRCodeHeight,null);
        }catch(IllegalArgumentException e)
        {
            return null;
        }

        int bitmatrixWidth=bitMatrix.getWidth();
        int bitmatrixHeight=bitMatrix.getHeight();

        int[]pixels = new int[bitmatrixWidth*bitmatrixHeight];

        for (int y=0;y<bitmatrixHeight;y++)
        {
            int offset =y* bitmatrixWidth;
            for(int x=0;x<bitmatrixWidth;x++)
            {
                pixels[offset + x]=bitMatrix.get(x,y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }

        bitmap = Bitmap.createBitmap(bitmatrixWidth,bitmatrixHeight,Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels,0,500,0,0,bitmatrixWidth,bitmatrixHeight);
        return bitmap;

    }
}