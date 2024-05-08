package com.example.eswachta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IntroPage extends AppCompatActivity {
    TextView subheading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
                    });
        Animation move = AnimationUtils.loadAnimation(this, R.anim.home_animation);
        subheading=findViewById(R.id.subheading);
        subheading.startAnimation(move);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                     public void run() {
                                          Intent homeScreen =new Intent(IntroPage.this, MainActivity.class);
                                          startActivity(homeScreen);
                                          finish();
                                      }
                                  },3000);
    }
}