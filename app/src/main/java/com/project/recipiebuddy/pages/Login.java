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

public class Login extends AppCompatActivity {
    EditText email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            TextView reg_text = findViewById(R.id.signup_text);
            reg_text.setOnClickListener(e->{
                Intent intent  = new Intent(this, Registration.class);
                startActivity(intent);
            });


            ///////////////////
//            Intent intent = new Intent(getApplicationContext(), Home.class);
//            startActivity(intent);
            ///////////////

            email = findViewById(R.id.email_input);
            pass = findViewById(R.id.password_input);
            String  pass2 = pass.getText().toString();
            String email2 = email.getText().toString();
            findViewById(R.id.login_button).setOnClickListener(e->{
                if(!pass2.isEmpty()&&!email2.isEmpty()){
                    AppUser appUser = new AppUser();
                    appUser.setUser_email(email2);
                    appUser.setUser_password(pass2);
                    ApiServices apiService = RetrofitClient.getInstance().create(ApiServices.class);
                    apiService.getUser(appUser).enqueue(new Callback<AppUser>() {
                        @Override
                        public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                            if (response.isSuccessful()) {
                                Log.d("Retrofit", "Response: " + response.body());
                                AppUser u = response.body();
                                if(u.getUser_id()!=0){
                                    AppUser.setInstance(u);
                                    Toast.makeText(getApplicationContext(),"Successfully Loged In", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"User not found!", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Log.e("Retrofit", "Request failed: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<AppUser> call, Throwable t) {
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