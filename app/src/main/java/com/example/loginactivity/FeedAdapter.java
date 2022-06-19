package com.example.loginactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.Viewholder> {
    Context context;
    List<Post> posts;

    FeedAdapter (Context context, ArrayList<Post> posts){
        this.context = context;
        this.posts = posts;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
        }
    }
    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        this.notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void AddAll(List<Post> list) {
        posts.addAll(list);
    }
}
