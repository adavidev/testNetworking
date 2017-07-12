package com.mygdx.game.UIScreens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.*;
import com.mygdx.game.network.Network;
import com.mygdx.game.network.SomeRequest;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Alan on 7/3/2017.
 */
public class ClientUI implements GameContext {
    SpriteBatch batch;
    Texture img;
    public String name;

    public ClientUI(ApplicationAdapter owner) {
        this(owner, "localhost", "test");
    }
    public ClientUI(ApplicationAdapter owner, String ip, String cname) {
        this.name = cname;
        img = new Texture("badlogic.jpg");

        GameClient.instance().start();
        Network.register(GameClient.instance());
        GameClient.instance().connect(ip);

        batch = new SpriteBatch();

        User.login(name);
    }

    @Override
    public void render(ApplicationAdapter owner, float delta) {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
            SomeRequest request = new SomeRequest();
            request.text = "Here is the request";
            GameClient.instance().sendTCP(request);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
            GameObject.create();
        }

        GameObject go = GameObjects.instance().get(name);

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            go.x = go.x + 2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            go.x = go.x - 2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            go.y = go.y + 2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            go.y = go.y - 2;
        }

        batch.begin();

        for (GameObject go1 : GameObjectsById.instance().values()) {
            go1.update(this, Gdx.graphics.getDeltaTime());
            batch.draw(img, go1.x, go1.y);
        }

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
//        img.dispose();
    }
}

