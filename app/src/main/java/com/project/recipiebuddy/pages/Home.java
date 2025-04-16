package com.project.recipiebuddy.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.project.recipiebuddy.R;
import com.project.recipiebuddy.entities.AppUser;
import com.project.recipiebuddy.fragments.Breakfast;
import com.project.recipiebuddy.fragments.Dinner;
import com.project.recipiebuddy.fragments.Lunch;
import com.project.recipiebuddy.model.CustomCardViewModel;

import java.util.ArrayList;
import java.util.Random;

public class Home extends AppCompatActivity {

    String[] quotes = {
            "Cooking is like love. It should be entered into with abandon or not at all. – Harriet Van Horne",
            "The only real stumbling block is fear of failure. In cooking, you've got to have a what-the-hell attitude. – Julia Child",
            "Cooking is all about people. Food is maybe the only universal thing that really has the power to bring everyone together. – Guy Fieri",
            "You don't have to cook fancy or complicated masterpieces—just good food from fresh ingredients. – Julia Child",
            "The way you make an omelet reveals your character. – Anthony Bourdain",
            "Cooking is like painting or writing a song. Just as there are only so many notes or colors, there are only so many flavors—it’s how you combine them that sets you apart. – Wolfgang Puck",
            "I cook with wine. Sometimes I even add it to the food. – W.C. Fields",
            "Great cooking is about being inspired by the simple things around you—fresh markets, various spices. – G. Garvin",
            "Cooking is at once child’s play and adult joy. And cooking done with care is an act of love. – Craig Claiborne",
            "One cannot think well, love well, sleep well, if one has not dined well. – Virginia Woolf"
    };


    FrameLayout frameLayout;
    TabLayout tabLayout;
    ArrayList<CustomCardViewModel> personList = new ArrayList<>();
    int f=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {


            Toolbar toolbar = findViewById(R.id.tool_bar);
            setSupportActionBar(toolbar);

            Random random = new Random();
            int index = random.nextInt(quotes.length);

            TextView movingText = findViewById(R.id.moving_text);
            movingText.setText("Hello "+ AppUser.getInstance().getUser_name()+"! Let's cooke something. <<"+quotes[index]+">>");
            movingText.setSelected(true);

            frameLayout = findViewById(R.id.fragmentLayout);
            tabLayout = findViewById(R.id.tabLayout);
            if(f==0){
               loadFragment(new Breakfast());
               f=1;
            }

            tabLayout.addOnTabSelectedListener(
                    new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            Fragment tmpFragment = null;
                            switch (tab.getPosition()){
                                case 0: {tmpFragment = new Breakfast();

                                break;}
                                case 1: {tmpFragment = new Lunch();
                                break;}
                                case 2: {tmpFragment = new Dinner();
                                break;}
                            }

//                            assert tmpFragment != null;
                            loadFragment(tmpFragment);
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout,new Breakfast()).commit();
                        }
                    }
            );
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, fragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list,menu);

        MenuItem menuItem = menu.findItem(R.id.share);
        menuItem.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"Sharing is Caring");
            intent.setType("text/plain");
            startActivity(intent);
        return false;
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(R.id.about_item==item.getItemId()){
            Toast.makeText(this,"This is Final Project",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}