package com.comov.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.comov.myapp.apiTools.APIService;
import com.comov.myapp.apiTools.APIUtils;
import com.comov.myapp.datamodel.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestActivity extends AppCompatActivity {

    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        // Get an API instance
        apiService = APIUtils.getAPIService();
    }

    public void getPosts(View v) {
        apiService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.i("RESPONSE", "" + response.body());
                TextView title = findViewById(R.id.postTitle);
                TextView body = findViewById(R.id.postBody);
                List<Post> postsList = response.body();

                // Get a random post
                Random rand = new Random();
                int randomPost = rand.nextInt(postsList.size() - 0) + 0;
                Post post = postsList.get(randomPost);
                title.setText(post.getTitle());
                body.setText(post.getBody());
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                // Handle error when GET. i.e resource not found
            }
        });
    }

    public void getOnePost(View v) {
        int id = 5;
        apiService.getPost(id).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.i("RESPONSE", "" + response.body());
                TextView title = findViewById(R.id.postTitle);
                TextView body = findViewById(R.id.postBody);

                Post post = response.body();
                title.setText(post.getTitle());
                body.setText(post.getBody());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void newPost(View v) {
        // Create a new post for the user id 1
        Post post = new Post("Mi post", "Este es un post de ejemplo", 1);
        apiService.newPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.i("RESPONSE", "" + response.body());
                Post createdPost = response.body();
                TextView title = findViewById(R.id.postTitle);
                TextView body = findViewById(R.id.postBody);
                title.setText(createdPost.getTitle());
                body.setText(createdPost.getBody());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                // Handle error when POST.
            }
        });
    }
}
