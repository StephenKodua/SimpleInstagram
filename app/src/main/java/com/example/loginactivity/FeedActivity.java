package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    ParseQuery<Post> query;
    private RecyclerView rvPosts;
    protected PostsAdapter adapter ;
    protected List<Post> allPosts;
    public static final String TAG = "FeedActivity";
    private SwipeRefreshLayout swipeContainer;
    int currentLimit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        rvPosts = findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(this, allPosts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(linearLayoutManager);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        queryPosts(0);
        swipeContainer = findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts(0);
                currentLimit = 0;
                swipeContainer.setRefreshing(false);
                //fetchTimelineAsync(0);
            }
        });
         //Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);

            }
        };
        rvPosts.addOnScrollListener(scrollListener);
    }

    private void loadNextDataFromApi(int offset) {
        queryPosts(offset);
    }


    private void queryPosts(int offset) {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        // limit query to latest 20 items
        query.setLimit(10);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        if (allPosts.size() > 0){
            Post lastPost = allPosts.get(allPosts.size() - 1);
            query.whereLessThan("createdAt", lastPost.getCreatedAt());

        }
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    swipeContainer.setRefreshing(false);
                    return;
                }
                // for debugging purposes let's print every post description to logcat
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                // save received posts to list and notify adapter of new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });

    }
}