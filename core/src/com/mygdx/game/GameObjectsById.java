package com.mygdx.game;

import java.util.HashMap;

/**
 * Created by Alan on 7/11/2017.
 */
public class GameObjectsById extends HashMap<Integer, GameObject> {

    private static GameObjectsById ourInstance = new GameObjectsById();

    public static GameObjectsById instance() {
        return ourInstance;
    }

    private GameObjectsById() {
    }
}
