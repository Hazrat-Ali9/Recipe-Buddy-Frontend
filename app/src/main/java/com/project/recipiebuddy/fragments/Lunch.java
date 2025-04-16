package com.project.recipiebuddy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.recipiebuddy.R;
import com.project.recipiebuddy.adapters.RecycleAdapters;
import com.project.recipiebuddy.endpoints.ApiServices;
import com.project.recipiebuddy.entities.Recipe;
import com.project.recipiebuddy.helper.RetrofitClient;
import com.project.recipiebuddy.model.CustomCardViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lunch extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Recipe> cardList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lunch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ShimmerFrameLayout shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);

        ApiServices apiService = RetrofitClient.getInstance().create(ApiServices.class);

        apiService.getRecipesByTime(1).enqueue(new Callback<ArrayList<Recipe>>() {

            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    ArrayList<Recipe> recipes = response.body();
                    Log.d("rahim2", "onResponse: "+recipes.size());
                    for (Recipe recipe : recipes) {
                        Log.d("rahim3", "name: "+recipe.getRecipe_name());
                        Recipe recipe1 = new Recipe();
                        recipe1.setRecipe_id(recipe.getRecipe_id());
                        recipe1.setRecipe_name(recipe.getRecipe_name());
                        recipe1.setRecipe_img(recipe.getRecipe_img());
                        recipe1.setRecipe_ing(recipe.getRecipe_ing());
                        cardList.add(recipe1);

                    }
                } else {
                    Log.e("Retrofit", "Request failed with code: " + response.code());
                }
                Log.e("rahim", "onViewCreated: "+cardList.size() );

                recyclerView = view.findViewById(R.id.recycle);
                recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
                RecycleAdapters reAdp = new RecycleAdapters(cardList,getContext(),0);
                recyclerView.setAdapter(reAdp);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });

    }
}