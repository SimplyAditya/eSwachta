package com.example.eswachta;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class productForm extends AppCompatActivity {

TextView date;
Button btn;

EditText name,description,price;
String Name,Descr,Price;
FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=FirebaseFirestore.getInstance();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        date=findViewById(R.id.date);
        name=findViewById(R.id.productName);
        description=findViewById(R.id.enterDetails);
        price=findViewById(R.id.price);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c=Calendar.getInstance();
                int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH);
                int day=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(

                        productForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },
                        year,month,day
                );
                datePickerDialog.show();
            }
        });
        btn=findViewById(R.id.nextBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=name.getText().toString().trim();
                Price=price.getText().toString().trim();
                Descr=description.getText().toString().trim();
                Map<String, Object> user = new HashMap<>();
                user.put("name", Name);
                user.put("price", Price);
                user.put("description", Descr);

                // Add a new document with a generated ID
                db.collection("productCollection")
                        .document(Name).set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(productForm.this, "Data Stored to Database", Toast.LENGTH_SHORT).show();
                                Intent nextPage=new Intent(productForm.this, uploadFiles.class);
                                startActivity(nextPage);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(productForm.this, "Error Uploading Data", Toast.LENGTH_SHORT).show();
                                Intent error =new Intent(productForm.this,HomeScreen.class);
                                startActivity(error);
                                finish();
                            }
                        });
                Intent changePage=new Intent(productForm.this, uploadFiles.class);
                startActivity(changePage);
            }
        });
    }
}