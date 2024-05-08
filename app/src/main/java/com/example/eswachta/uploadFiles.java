package com.example.eswachta;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class uploadFiles extends AppCompatActivity {
    ImageView img1,img2,img3;
    ImageButton upload1,upload2,upload3;
    Button button;
    private final int GALLERY_REQ_CODE=1000;
    int pressed=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_files);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        img1=findViewById(R.id.Image1);
        img2=findViewById(R.id.Image2);
        img3=findViewById(R.id.Image3);
        upload1=findViewById(R.id.uploadImage1);
        upload2=findViewById(R.id.uploadImage2);
        upload3=findViewById(R.id.uploadImage3);
        button=findViewById(R.id.nextBtn);

        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery =new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);
                pressed=1;
            }
        });
        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery =new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);
                pressed=2;
            }
        });
        upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery =new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);
                pressed=3;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePage=new Intent(uploadFiles.this,HomeScreen.class);
                startActivity(changePage);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(pressed==1) {
                if (requestCode == GALLERY_REQ_CODE) {
                    img1.setImageURI(data.getData());
                }
            } else if (pressed==2) {
                if (requestCode == GALLERY_REQ_CODE) {
                    img2.setImageURI(data.getData());
                }
            }else if (pressed==3){
                if (requestCode == GALLERY_REQ_CODE) {
                    img3.setImageURI(data.getData());
                }
            }
        }
    }
}