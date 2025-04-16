package com.project.recipiebuddy.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.recipiebuddy.R;
import com.project.recipiebuddy.entities.Recipe;
import com.project.recipiebuddy.model.CustomCardViewModel;
import com.project.recipiebuddy.pages.ShowReceipe;

import java.util.List;

public class RecycleAdapters extends RecyclerView.Adapter <RecycleAdapters.CardViewHolder>{

    private List<Recipe> cardList;
    static Context context;
    //0=breakfast; 1=lunch; 2=dinner
    static int f;


    public RecycleAdapters(List<Recipe> cardList, Context context, int f) {
        this.cardList=cardList;
        this.context = context;
        this.f=f;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_card_view,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Recipe customCardViewModel = cardList.get(position);
        Log.e("rahim_cu_rid", "onBindViewHolder: "+customCardViewModel.toString());
        holder.rId.setText(customCardViewModel.getRecipe_id()+"");
        holder.cardName.setText(customCardViewModel.getRecipe_name());
        Glide.with(context).load(customCardViewModel.getRecipe_img()).into(holder.cardImage);

        holder.itemView.setOnClickListener((v -> {
            Intent intent = new Intent(v.getContext(), ShowReceipe.class);
            if(f==0){
                intent.putExtra("breakfast",customCardViewModel);
            }
//            else if(f==1){
//                intent.putExtra("lunch",rId.getText());
//            }else{
//                intent.putExtra("dinner",rId.getText());
//            }
            v.getContext().startActivity(intent);
//            Toast.makeText(v.getContext(), cardName.getText(),Toast.LENGTH_LONG).show();
        }));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        ImageView cardImage;
        TextView cardName,rId;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardName = itemView.findViewById(R.id.card_person_name);
            rId = itemView.findViewById(R.id.r_id);
            cardImage = itemView.findViewById(R.id.card_img_view);

        }
    }
}
