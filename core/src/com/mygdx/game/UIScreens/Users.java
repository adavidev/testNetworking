package com.mygdx.game.UIScreens;

import com.mygdx.game.User;

import java.util.HashMap;

/**
 * Created by Alan on 7/10/2017.
 */
public class Users extends HashMap<String, User> {
    private static Users ourInstance = new Users();

    public static Users instance() {
        return ourInstance;
    }

    public void put(User user){
        this.put(user.name, user);
    }
}
