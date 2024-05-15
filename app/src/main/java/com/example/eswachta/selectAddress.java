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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class selectAddress extends AppCompatActivity {
    EditText[] arry;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    Button locationBtn,nextBtn;
    int pressed=0;
    private FirebaseAuth mAuth;
    String name, email, password,mobile,hNo,addressLine1,addressLine2,city,State,Pincode,Landmark;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        mobile=getIntent().getStringExtra("mobile");
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
                    Toast.makeText(selectAddress.this, "Location Stored", Toast.LENGTH_SHORT).show();
                } else {
                    gpsTracker.showSettingsAlert();
                }


                pressed=1;


            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hNo=arry[0].getText().toString().trim();
                addressLine1=arry[1].getText().toString().trim();
                addressLine2=arry[2].getText().toString().trim();
                city=arry[3].getText().toString().trim();
                State=arry[4].getText().toString().trim();
                Pincode=arry[5].getText().toString().trim();
                for(int i=0;i<6;i++){
                    if(arry[i].getText().toString().trim().isEmpty()){
                        Toast.makeText(selectAddress.this, "Mandatory Fields are Empty", Toast.LENGTH_SHORT).show();
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
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            //Sign Up Successful
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("name", name);
                                            user.put("email", email);
                                            user.put("mobile", mobile);
                                            user.put("hNo", hNo);
                                            user.put("addressLine1", addressLine1);
                                            user.put("addressLine2", addressLine2);
                                            user.put("city", city);
                                            user.put("state", State);
                                            user.put("pincode",Pincode);

                                            // Add a new document with a generated ID
                                            db.collection("usersInfo")
                                                    .document(email).set(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(selectAddress.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                            Intent nextPage=new Intent(selectAddress.this,HomeScreen.class);
                                                            startActivity(nextPage);
                                                            finish();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(selectAddress.this, "Error Uploading Data", Toast.LENGTH_SHORT).show();
                                                            Intent error =new Intent(selectAddress.this,MainActivity.class);
                                                            startActivity(error);
                                                            finish();
                                                        }
                                                    });


                                        } else {
                                            // sign Up fails,.
                                            Toast.makeText(selectAddress.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                            Intent failure=new Intent(selectAddress.this,MainActivity.class);
                                        }
                                    }
                                });
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