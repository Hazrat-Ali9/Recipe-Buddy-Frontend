package com.project.recipiebuddy.endpoints;

import com.project.recipiebuddy.entities.AppUser;
import com.project.recipiebuddy.entities.Recipe;
import com.project.recipiebuddy.entities.RecipeSteps;
import com.project.recipiebuddy.model.Comment;
import com.project.recipiebuddy.model.Reply;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServices {
    @GET("recipe/time/{time}")
    Call<ArrayList<Recipe>>getRecipesByTime(@Path("time") int time);
    @GET("recipe/id/{id}")
    Call <Recipe> getRecipesById(@Path("id") int id);
    @GET("recipe/steps/{id}")
    Call <ArrayList<RecipeSteps>> getStepssById(@Path("id") int id);
    @POST("user/")
    Call<String> addUser(@Body AppUser appUser);
    @POST("user/login/")
    Call <AppUser> getUser(@Body AppUser appUser);
    @POST("comment/")
    Call <String> addComent(@Body Comment comment);
    @GET("comment/{recipe_id}")
    Call <ArrayList<Comment>> getComent(@Path("recipe_id") int recipe_id);
    @POST("reply/")
    Call <String> addReplay(@Body Reply reply);
    @GET("reply/{comment_id}")
    Call <ArrayList<Reply>> getReply(@Path("comment_id") int comment_id);
    @DELETE("comment/{id}")
    Call <Void> deleteComent(@Path("id") int id);
    @DELETE("reply/{id}")
    Call <Void> deleteReply(@Path("id") int id);
}

