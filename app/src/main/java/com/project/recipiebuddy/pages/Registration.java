package com.project.recipiebuddy.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.recipiebuddy.R;
import com.project.recipiebuddy.endpoints.ApiServices;
import com.project.recipiebuddy.entities.AppUser;
import com.project.recipiebuddy.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    EditText email,pass,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            TextView view = findViewById(R.id.login_text);
            view.setOnClickListener(e->{
                Intent intent  = new Intent(this, Login.class);
                startActivity(intent);
            });

            email = findViewById(R.id.email_input_r);
            pass = findViewById(R.id.password_input_r);
            name = findViewById(R.id.name_input_r);

                    AppUser appUser = new AppUser();
                    appUser.setUser_email(email.getText().toString());
                    appUser.setUser_password(pass.getText().toString());
                    appUser.setUser_name(name.getText().toString());

            findViewById(R.id.register_button).setOnClickListener(e->{
                if(!appUser.getUser_name().isEmpty() && !appUser.getUser_email().isEmpty() && !appUser.getUser_password().isEmpty()){
                    ApiServices apiService = RetrofitClient.getInstance().create(ApiServices.class);
                    apiService.addUser(appUser).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                Log.d("Retrofit", "Response: " + response.body());
                                Toast.makeText(getApplicationContext(),"Account Created!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            } else {
                                Log.e("Retrofit", "Request failed: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("Retrofit", "Error: " + t.getMessage());
                        }
                    });
                }

            });




            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}