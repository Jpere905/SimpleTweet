package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// our goal is to turn a JSON tweet data into a java object
public class Tweet {

    public String body;         // the body of our tweet
    public String createdAt;    // timestamp of tweet
    public User user;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {

        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {

        List<Tweet> tweetList = new ArrayList<>();
        for (int i=0; i < jsonArray.length(); i++){

            tweetList.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return tweetList;
    }

}
