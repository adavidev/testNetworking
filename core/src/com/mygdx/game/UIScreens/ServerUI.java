package com.mygdx.game.UIScreens;

import com.badlogic.gdx.ApplicationAdapter;
import com.mygdx.game.PositionServer;
import com.mygdx.game.UIScreens.GameContext;

import java.io.IOException;

/**
 * Created by Alan on 7/3/2017.
 */
public class ServerUI implements GameContext {

    PositionServer server;

    public ServerUI(ApplicationAdapter owner){
        try {
            server = new PositionServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(ApplicationAdapter owner, float delta) {

    }

    @Override
    public void dispose () {
    }
}

