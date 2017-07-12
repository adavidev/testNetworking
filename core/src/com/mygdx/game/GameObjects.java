package com.mygdx.game;

import java.util.HashMap;

/**
 * Created by Alan on 7/11/2017.
 */
public class GameObjects extends HashMap<String, Integer> {

    private static GameObjects ourInstance = new GameObjects();

    public static GameObjects instance() {
        return ourInstance;
    }

    private GameObjects() {
    }

    public GameObject get(String key) {
        return GameObjectsById.instance().get(super.get(key));
    }

    public GameObject put(GameObject go) {
        this.put(go.name, go.id);
        return GameObjectsById.instance().put(go.id, go);
    }
}
