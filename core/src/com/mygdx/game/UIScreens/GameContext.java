package com.mygdx.game.UIScreens;

import com.badlogic.gdx.ApplicationAdapter;

/**
 * Created by Alan on 7/3/2017.
 */
public interface GameContext {
    public void render(ApplicationAdapter owner, float delta);
    public void dispose ();
}