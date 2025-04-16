package com.project.recipiebuddy.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Recipe implements Parcelable {
	int recipe_id;
	String recipe_name;
	String recipe_img;
	String recipe_ing;
	int recipe_time;

	public Recipe(){

	}
	protected Recipe(Parcel in) {
		recipe_id = in.readInt();
		recipe_name = in.readString();
		recipe_img = in.readString();
		recipe_ing = in.readString();
		recipe_time = in.readInt();
	}

	public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
		@Override
		public Recipe createFromParcel(Parcel in) {
			return new Recipe(in);
		}

		@Override
		public Recipe[] newArray(int size) {
			return new Recipe[size];
		}
	};

	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public String getRecipe_name() {
		return recipe_name;
	}
	public void setRecipe_name(String recipe_name) {
		this.recipe_name = recipe_name;
	}
	public String getRecipe_img() {
		return recipe_img;
	}
	public void setRecipe_img(String recipe_img) {
		this.recipe_img = recipe_img;
	}
	public String getRecipe_ing() {
		return recipe_ing;
	}
	public void setRecipe_ing(String recipe_ing) {
		this.recipe_ing = recipe_ing;
	}
	public int getRecipe_time() {
		return recipe_time;
	}
	public void setRecipe_time(int recipe_time) {
		this.recipe_time = recipe_time;
	}

	@Override
	public String toString() {
		return "Recipe{" +
				"recipe_id=" + recipe_id +
				", recipe_name='" + recipe_name + '\'' +
				", recipe_img='" + recipe_img + '\'' +
				", recipe_ing='" + recipe_ing + '\'' +
				", recipe_time=" + recipe_time +
				'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel dest, int flags) {
		dest.writeInt(recipe_id);
		dest.writeString(recipe_name);
		dest.writeString(recipe_img);
		dest.writeString(recipe_ing);
		dest.writeInt(recipe_time);
	}
}
