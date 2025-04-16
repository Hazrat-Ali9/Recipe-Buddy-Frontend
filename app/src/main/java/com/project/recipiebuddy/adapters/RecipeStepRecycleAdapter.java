package com.project.recipiebuddy.adapters;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.project.recipiebuddy.R;
import com.project.recipiebuddy.entities.RecipeSteps;

import java.util.ArrayList;

public class RecipeStepRecycleAdapter extends RecyclerView.Adapter<StepsViewHolder>{
    Context context;
    private CountDownTimer countDownTimer;
    ArrayList<RecipeSteps> recipieSteps;
    public RecipeStepRecycleAdapter(Context context, ArrayList<RecipeSteps> recipieSteps) {
    this.context=context;
    this.recipieSteps=recipieSteps;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.steps_view,parent,false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        RecipeSteps steps = recipieSteps.get(position);
        holder.stepTime.setText(String.valueOf(steps.getRecipe_time())+"s");
        holder.stepNumber.setText(String.valueOf(position+1));
        holder.stepText.setText(steps.getRecipe_step());


        int time = steps.getRecipe_time();


        holder.imageView.setOnClickListener(e->{
//            Toast.makeText(context,"ttt",Toast.LENGTH_LONG).show();
           MediaPlayer mediaPlayer= MediaPlayer.create(context,R.raw.kitchen_timer);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(1.0F,1.0F);

            holder.imageView.setVisibility(View.GONE);
            holder.cancel.setVisibility(View.VISIBLE);

            countDownTimer =  new CountDownTimer(time * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    holder.stepTime.setText(""+millisUntilFinished / 1000 + "s");
                }

                @Override
                public void onFinish() {
                    Toast.makeText(context,"test",Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Step Finished!");
                    builder.setMessage("Now time to move to the next step");
                    builder.setIcon(R.drawable.baseline_done_24);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Get it",(dialog, which) -> {
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                        dialog.dismiss();
                    });

                    AlertDialog alertDialog =builder.create();
                    alertDialog.show();

                    mediaPlayer.start();


                }
            }.start();


        });

        holder.cancel.setOnClickListener(e->{
            holder.imageView.setVisibility(View.VISIBLE);
            holder.cancel.setVisibility(View.GONE);
            countDownTimer.cancel();
            holder.stepTime.setText(steps.getRecipe_time()+"s");

        });

        holder.video.setOnClickListener(e->{
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.youtude_view);
            dialog.setCancelable(true);

            // Set up the WebView
            WebView webView = dialog.findViewById(R.id.youtube_webview);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());

            // Load the YouTube video
            String embedUrl = "https://www.youtube.com/embed/" + "oupj0xj30ZQ";
            String html = "<iframe width=\"100%\" height=\"100%\" src=\"" + embedUrl + "\" frameborder=\"0\" allowfullscreen></iframe>";
            webView.loadData(html, "text/html", "UTF-8");

            // Show the dialog
            dialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return recipieSteps.size();
    }
}

class StepsViewHolder extends RecyclerView.ViewHolder{
    TextView stepNumber, stepText, stepTime;
    ImageView imageView, cancel, video;
    public StepsViewHolder(@NonNull View itemView) {
        super(itemView);
        stepNumber = itemView.findViewById(R.id.steps_number);
        stepText = itemView.findViewById(R.id.steps_text);
        stepTime = itemView.findViewById(R.id.step_time);
        imageView = itemView.findViewById(R.id.timer_btn);
        cancel = itemView.findViewById(R.id.timer_btn_cancel);
        video = itemView.findViewById(R.id.timer_btn_video);


    }
}
