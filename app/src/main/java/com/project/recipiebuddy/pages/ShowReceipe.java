package com.project.recipiebuddy.pages;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.recipiebuddy.R;
import com.project.recipiebuddy.adapters.CommentRecycleAdapter;
import com.project.recipiebuddy.adapters.RecipeStepRecycleAdapter;
import com.project.recipiebuddy.endpoints.ApiServices;
import com.project.recipiebuddy.entities.AppUser;
import com.project.recipiebuddy.entities.Recipe;
import com.project.recipiebuddy.entities.RecipeSteps;
import com.project.recipiebuddy.helper.RetrofitClient;
import com.project.recipiebuddy.model.Comment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowReceipe extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_receipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            TextView ingredient = findViewById(R.id.ing_txt);
            TextView r_name = findViewById(R.id.sr_title);
            ImageView imageView = findViewById(R.id.r_photo);
            RecyclerView recyclerView = findViewById(R.id.steps_view);

                    Recipe r = getIntent().getParcelableExtra("breakfast");
                    r_name.setText(r.getRecipe_name());
                    ingredient.setText(r.getRecipe_ing());
                    Glide.with(getApplicationContext()).load(r.getRecipe_img()).into(imageView);


            ApiServices apiServices = RetrofitClient.getInstance().create(ApiServices.class);
            apiServices.getStepssById(r.getRecipe_id()).enqueue(new Callback<ArrayList<RecipeSteps>>(){

                @Override
                public void onResponse(Call<ArrayList<RecipeSteps>> call, Response<ArrayList<RecipeSteps>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ArrayList<RecipeSteps> steps = response.body();


            RecipeStepRecycleAdapter recipeStepRecycleAdapter = new RecipeStepRecycleAdapter(ShowReceipe.this, steps);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//            Log.d("rahim", String.valueOf(recipie.getRecipieSteps().size()));
            recyclerView.setAdapter(recipeStepRecycleAdapter);
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<RecipeSteps>> call, Throwable t) {

                }
            });

            editText = findViewById(R.id.et_comment_input);
            Comment comment = new Comment();
            comment.setRecipe_id(r.getRecipe_id());
            comment.setComment_text(editText.getText().toString());
            comment.setUser_id(AppUser.getInstance().getUser_id());
            comment.setUser_name(AppUser.getInstance().getUser_name());
            findViewById(R.id.btn_send_comment).setOnClickListener(e->{
                apiServices.addComent(comment).enqueue(new Callback<String>(){


                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("retrofit", "onResponse: "+response.body());
                        if (response.isSuccessful() && response.body() != null) {
                            if(response.body().equals("0")){
                                Toast.makeText(ShowReceipe.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ShowReceipe.this, "Comment Added!", Toast.LENGTH_SHORT).show();
                                editText.setText("");





                                //FETCH AGAIN
                                apiServices.getComent(r.getRecipe_id()).enqueue(new Callback<ArrayList<Comment>>() {

                                    @Override
                                    public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {

                                        if (response.isSuccessful() && response.body() != null) {
                                            ArrayList<Comment> comments = response.body();
//                        comments.add(comment);
                                            Log.d("retrofit", String.valueOf(comments.size()));
                                            RecyclerView recyclerView1 = findViewById(R.id.recycler_comment);
                                            recyclerView1.setLayoutManager(new LinearLayoutManager(ShowReceipe.this));
                                            CommentRecycleAdapter commentRecycleAdapter = new CommentRecycleAdapter(ShowReceipe.this,comments,recyclerView1);
                                            recyclerView1.setAdapter(commentRecycleAdapter);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                                        Log.e("Retrofit", "Request failed: " + t.getMessage());

                                    }
                                });







                            }
                        }
                        
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("Retrofit", "Request failed: " + t.getMessage());
                    }
                });
                
            });



            //SHOW COMMENT
            apiServices.getComent(r.getRecipe_id()).enqueue(new Callback<ArrayList<Comment>>() {

                @Override
                public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        ArrayList<Comment> comments = response.body();
//                        comments.add(comment);
                        Log.d("retrofit", String.valueOf(comments.size()));
                        RecyclerView recyclerView1 = findViewById(R.id.recycler_comment);
recyclerView1.setLayoutManager(new LinearLayoutManager(ShowReceipe.this));
CommentRecycleAdapter commentRecycleAdapter = new CommentRecycleAdapter(ShowReceipe.this,comments, recyclerView1);
recyclerView1.setAdapter(commentRecycleAdapter);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                    Log.e("Retrofit", "Request failed: " + t.getMessage());

                }
            });










            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}