package com.example.eswachta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class registerPage extends AppCompatActivity {
    TextView register;
    EditText name,username,pass,number;
    Button nextBtn;
    String myName,email,password,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        nextBtn=findViewById(R.id.RegisterButton);
        register=findViewById(R.id.LoginText);
        name=findViewById(R.id.name);
        username=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        number=findViewById(R.id.mobile);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(registerPage.this,MainActivity.class);
                startActivity(login);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myName = name.getText().toString().trim();
                email=username.getText().toString().trim();
                password=pass.getText().toString().trim();
                mobile=number.getText().toString().trim();
                if(myName.isEmpty()){
                    Toast.makeText(registerPage.this, "Name Can't be Empty", Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty()){
                    Toast.makeText(registerPage.this, "Email Can't Be Empty", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()){
                    Toast.makeText(registerPage.this, "Password Can't Be Empty", Toast.LENGTH_SHORT).show();
                } else if (mobile.isEmpty()) {
                    Toast.makeText(registerPage.this, "Mobile Number Can't Be Empty", Toast.LENGTH_SHORT).show();
                } else if (mobile.length()!=10) {
                    Toast.makeText(registerPage.this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                } else{
                    Intent address=new Intent(registerPage.this, selectAddress.class);
                    address.putExtra("name",myName);
                    address.putExtra("email",email);
                    address.putExtra("password",password);
                    address.putExtra("mobile",mobile);
                    startActivity(address);
                    ;
                }
            }
        });

    }
}