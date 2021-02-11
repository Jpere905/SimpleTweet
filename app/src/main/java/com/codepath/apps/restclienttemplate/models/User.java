package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String name;
    public String screenName;
    public String profileImageURL;

    // empty constructor required by Parceler
    public User () {

    }
    public static User fromJson(JSONObject jsonUserObject) throws JSONException {
        User user = new User();
        user.name = jsonUserObject.getString("name");
        user.screenName = jsonUserObject.getString("screen_name");
        user.profileImageURL = jsonUserObject.getString("profile_image_url_https");


        return user;
    }
}