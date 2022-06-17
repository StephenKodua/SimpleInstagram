package com.example.loginactivity;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

//extend ParseApplication to be a root class of
//android application
public class ParseApplication extends Application {

    //Initialize parse SDK when Application is created
    @Override
    public void onCreate() {
        super.onCreate();
        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("VzGkga1DXGAwQAmX7TzCLrA2Ngb8iAGjL58KEqTv")
                .clientKey("JI6sXqA88mvHRvGgdstA3nig3t3rMzqZLGTSf2PY")
                .server("https://parseapi.back4app.com")
                .build());
    }
}
