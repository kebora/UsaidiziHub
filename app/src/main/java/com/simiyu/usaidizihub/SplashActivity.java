package com.simiyu.usaidizihub;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageViewBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        makeImageFoggy();
    }
    /*This function makes the background image a little bit cloudy.*/
    void makeImageFoggy(){
        imageViewBackground = findViewById(R.id.image_view_background);
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img5);

        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(50);

        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);

        Paint paint = new Paint();
        paint.setColorFilter(f);

        Bitmap newBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(originalBitmap, 0, 0, paint);

        imageViewBackground.setImageBitmap(newBitmap);
    }
}