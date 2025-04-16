package com.project.recipiebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.recipiebuddy.pages.Home;
import com.project.recipiebuddy.pages.Login;
import com.project.recipiebuddy.pages.Registration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            ProgressBar pb = findViewById(R.id.progress_bar);
            Thread t = new Thread(new Runnable(){
                @Override
                public void run() {
                    int progress=0;
                    while(progress<101){
                        pb.setProgress(progress);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                      progress+=10;
                    }
                    Intent go = new Intent(MainActivity.this, Login.class);
                    startActivity(go);
                    finish();
                }
            });
    t.start();

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}