package com.project.recipiebuddy.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.recipiebuddy.R;
import com.project.recipiebuddy.endpoints.ApiServices;
import com.project.recipiebuddy.entities.AppUser;
import com.project.recipiebuddy.helper.RetrofitClient;
import com.project.recipiebuddy.model.Comment;
import com.project.recipiebuddy.model.Reply;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplyRecycleAdapter extends RecyclerView.Adapter<ReplyViewHolder>{
    Context context;
    ArrayList<Reply> replies;

    RecyclerView crv;
    public ReplyRecycleAdapter(Context context, ArrayList<Reply> replies) {
    this.context=context;
    this.replies=replies;
    }

    public ReplyRecycleAdapter(Context context, ArrayList<Reply> replies, RecyclerView crv) {
        this.context=context;
        this.replies=replies;
        this.crv=crv;
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reply_recycle_ui,parent,false);
        return new ReplyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
        Reply reply = replies.get(position);
//        Log.e("retrofit_eee", "onBindViewHolder: "+reply.toString());

            holder.reply.setText(reply.getReply_text());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            holder.time.setText(sdf.format(reply.getReply_time()));
            holder.name.setText(reply.getUser_name());

        if(AppUser.getInstance().getUser_id()!= reply.getUser_id()){
            holder.delete.setVisibility(holder.itemView.GONE);
        }



        ApiServices apiServices = RetrofitClient.getInstance().create(ApiServices.class);
//        DELETE REPLY
        holder.delete.setOnClickListener(e->{
            apiServices.deleteReply(reply.getReply_id()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    //REFETCH REPLY
                    apiServices.getReply(reply.getComment_id()).enqueue(new Callback<ArrayList<Reply>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Reply>> call, Response<ArrayList<Reply>> response) {
//                                                Log.d("retrofit2002", String.valueOf(.size()));
                            if (response.isSuccessful() && response.body() != null) {
                                ArrayList<Reply>replies=response.body();
                                Log.i("Retrofit", "Request failed: " + replies.size());
                                if(!replies.isEmpty()){
                                    ReplyRecycleAdapter replyRecycleAdapter = new ReplyRecycleAdapter(context,replies);
                                    crv.setLayoutManager(new LinearLayoutManager(context));
                                    crv.setAdapter(replyRecycleAdapter);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Reply>> call, Throwable t) {
                            Log.e("Retrofit", "Request failed: " + t.getMessage());
                        }
                    });


                    Toast.makeText(context, "Reply Deleted", Toast.LENGTH_SHORT).show();

                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Retrofit", "Request failed: " + t.getMessage());
                }
            });
        });




    }

    @Override
    public int getItemCount() {
        return replies.size();
    }
}

class ReplyViewHolder extends RecyclerView.ViewHolder{
    TextView name, delete,reply,time;
    public ReplyViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_comment_user_r);
        reply = itemView.findViewById(R.id.tv_reply_text);
        delete = itemView.findViewById(R.id.tv_delete_r);
        time = itemView.findViewById(R.id.tv_time_r);
    }
}
