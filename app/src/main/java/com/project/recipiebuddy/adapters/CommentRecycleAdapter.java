package com.project.recipiebuddy.adapters;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.recipiebuddy.R;
import com.project.recipiebuddy.endpoints.ApiServices;
import com.project.recipiebuddy.entities.AppUser;
import com.project.recipiebuddy.entities.RecipeSteps;
import com.project.recipiebuddy.helper.RetrofitClient;
import com.project.recipiebuddy.model.Comment;
import com.project.recipiebuddy.model.Reply;
import com.project.recipiebuddy.pages.ShowReceipe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentRecycleAdapter extends RecyclerView.Adapter<CommentViewHolder>{
    Context context;
    private CountDownTimer countDownTimer;
    RecyclerView recyclerView;
    ArrayList<Comment> comments;
    public CommentRecycleAdapter(Context context, ArrayList<Comment> comments) {
    this.context=context;
    this.comments=comments;
    }

    public CommentRecycleAdapter(Context context, ArrayList<Comment> comments, RecyclerView recyclerView) {
        this.context=context;
        this.comments=comments;
        this.recyclerView=recyclerView;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_recycle_ui,parent,false);
        return new CommentViewHolder(view);
    }

    public void addComment(Comment comment) {
        comments.add(comment); // Add the new comment to the dataset
        notifyItemInserted(comments.size() - 1); // Notify RecyclerView to update
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.comment.setText(comment.getComment_text());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.time.setText(sdf.format(comment.getComment_time()));
        holder.name.setText(comment.getUser_name());

        if(AppUser.getInstance().getUser_id()!= comment.getUser_id()){
            holder.delete.setVisibility(holder.itemView.GONE);
        }


        holder.reply.setOnClickListener(e->{
            final EditText input = new EditText(holder.itemView.getContext());
            input.setHint("Write your reply...");

            // Build the AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Reply")
                    .setView(input) // Add the EditText to the dialog
                    .setPositiveButton("Send", (dialog, which) -> {
                        // Get the user's input and handle it
                        String reply = input.getText().toString();
                        if (!reply.isEmpty()) {

                            ApiServices apiServices = RetrofitClient.getInstance().create(ApiServices.class);
                            Reply reply1 = new Reply();
                            reply1.setReply_text(reply);
                            reply1.setComment_id(comment.getComment_id());
                            reply1.setRecipe_id(comment.getRecipe_id());
                            reply1.setUser_id(AppUser.getInstance().getUser_id());
                            reply1.setUser_name(comment.getUser_name());
                            apiServices.addReplay(reply1).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
//                                                Log.d("retrofit", String.valueOf(.size()));


                                    //RE FETCH REPLY
                                    ApiServices apiServices = RetrofitClient.getInstance().create(ApiServices.class);
                                    apiServices.getReply(comment.getComment_id()).enqueue(new Callback<ArrayList<Reply>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<Reply>> call, Response<ArrayList<Reply>> response) {
//                                                Log.d("retrofit2002", String.valueOf(.size()));
                                            if (response.isSuccessful() && response.body() != null) {
                                                ArrayList<Reply>replies=response.body();
                                                Log.i("Retrofit", "Request failed: " + replies.size());
                                                if(!replies.isEmpty()){
                                                    ReplyRecycleAdapter replyRecycleAdapter = new ReplyRecycleAdapter(context,replies);
                                                    holder.recycleView.setLayoutManager(new LinearLayoutManager(context));
                                                    holder.recycleView.setAdapter(replyRecycleAdapter);
                                                }

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ArrayList<Reply>> call, Throwable t) {
                                            Log.e("Retrofit", "Request failed: " + t.getMessage());
                                        }
                                    });



                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.e("Retrofit", "Request failed: " + t.getMessage());
                                }
                            });


                            Toast.makeText(context, "Reply Sent: " + reply, Toast.LENGTH_SHORT).show();
                            // Add logic to update your RecyclerView or database here
                        } else {
                            Toast.makeText(context, "Reply cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });



        //FETCH REPLAY
        ApiServices apiServices = RetrofitClient.getInstance().create(ApiServices.class);
        apiServices.getReply(comment.getComment_id()).enqueue(new Callback<ArrayList<Reply>>() {
            @Override
            public void onResponse(Call<ArrayList<Reply>> call, Response<ArrayList<Reply>> response) {
//                                                Log.d("retrofit2002", String.valueOf(.size()));
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Reply>replies=response.body();
                    Log.i("Retrofit", "Request failed: " + replies.size());
                    if(!replies.isEmpty()){
                        ReplyRecycleAdapter replyRecycleAdapter = new ReplyRecycleAdapter(context,replies, holder.recycleView);
                        holder.recycleView.setLayoutManager(new LinearLayoutManager(context));
                        holder.recycleView.setAdapter(replyRecycleAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Reply>> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });



        //DELETE COMMENT
        holder.delete.setOnClickListener(e->{
            apiServices.deleteComent(comment.getComment_id()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                        //REFETCH COMMENT
                    apiServices.getComent(comment.getRecipe_id()).enqueue(new Callback<ArrayList<Comment>>() {

                        @Override
                        public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {

                            if (response.isSuccessful() && response.body() != null) {
                                ArrayList<Comment> comments = response.body();
//                        comments.add(comment);
                                Log.d("retrofit", String.valueOf(comments.size()));
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                CommentRecycleAdapter commentRecycleAdapter = new CommentRecycleAdapter(context,comments);
                                recyclerView.setAdapter(commentRecycleAdapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                            Log.e("Retrofit", "Request failed: " + t.getMessage());

                        }
                    });


                        Toast.makeText(context, "Comment Deleted", Toast.LENGTH_SHORT).show();

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
        return comments.size();
    }
}

class CommentViewHolder extends RecyclerView.ViewHolder{
    TextView name, comment, delete,reply,time;
    RecyclerView recycleView;
    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_comment_user);
        comment = itemView.findViewById(R.id.tv_comment_text);
        delete = itemView.findViewById(R.id.tv_delete);
        reply = itemView.findViewById(R.id.tv_reply);
        time = itemView.findViewById(R.id.tv_time);
        recycleView = itemView.findViewById(R.id.recycler_reply);
    }
}
