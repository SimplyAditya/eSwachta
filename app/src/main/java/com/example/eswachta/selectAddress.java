package com.example.eswachta;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class selectAddress extends AppCompatActivity {
    EditText[] arry;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    Button locationBtn,nextBtn;
    int pressed=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_address);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GPSTracker gpsTracker = new GPSTracker(this);

        // Check permissions and request them if necessary
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
        arry=new EditText[6];
        arry[0]=findViewById(R.id.houseNo);
        arry[1]=findViewById(R.id.addressLine1);
        arry[2]=findViewById(R.id.addressLine2);
        arry[3]=findViewById(R.id.city);
        arry[4]=findViewById(R.id.state);
        arry[5]=findViewById(R.id.pincode);
        locationBtn=findViewById(R.id.preciseLocation);
        nextBtn=findViewById(R.id.nextPage);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gpsTracker.canGetLocation()) {
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();
                    Toast.makeText(selectAddress.this, "Location Stored", Toast.LENGTH_LONG).show();
                } else {
                    gpsTracker.showSettingsAlert();
                }


                pressed=1;


            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<6;i++){
                    if(arry[i].getText().toString().trim().isEmpty()){
                        Toast.makeText(selectAddress.this, "Mandatory Fields are Empty", Toast.LENGTH_LONG).show();
                        pressed=2;
                    }
                    else{
                        pressed=1;
                    }
                }
                switch (pressed){
                    case 0:
                        Toast.makeText(selectAddress.this, "Precise Location Not Picked", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Intent nextPage=new Intent(selectAddress.this,HomeScreen.class);
                        startActivity(nextPage);
                        finish();
                        break;
                    default:
                        break;
                }

            }
        });


    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Permission denied. Cannot access location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}