package com.mygdx.game.UIScreens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.PositionServer;
import com.mygdx.game.TestNetworking;
import com.mygdx.game.UIScreens.ClientUI;
import com.mygdx.game.UIScreens.GameContext;

import java.io.IOException;

/**
 * Created by Alan on 7/3/2017.
 */
public class MenuUI implements GameContext {
    Stage stage;
    Label outputLabel;
    TextField txtUsername;
    TestNetworking owner;

    public MenuUI(TestNetworking aowner){
        owner = aowner;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        int Help_Guides = 12;
        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;

        Skin mySkin = new Skin(Gdx.files.internal("flat/flat-earth-ui.json"));

        Label title = new Label("Buttons with Skins",mySkin,"title");
        title.setSize(Gdx.graphics.getWidth(),row_height*2);
        title.setPosition(0,Gdx.graphics.getHeight()-row_height*2);
        title.setAlignment(Align.center);
        stage.addActor(title);

        // Button
        Button button1 = new TextButton("Play",mySkin,"default");
        button1.setSize(col_width*4,row_height);
        button1.setPosition(col_width,Gdx.graphics.getHeight()-row_height*3);
        button1.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Press a Button");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Pressed Button");
                try {
                    PositionServer server = new PositionServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                owner.cli = new ClientUI(owner,"localhost",txtUsername.getText());
                return true;
            }
        });
        stage.addActor(button1);

        // Text Button
        Button button2 = new TextButton("Join",mySkin,"default");
        button2.setSize(col_width*4,row_height);
        button2.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*3);
        button2.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Press a Button");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Pressed Text Button");
                owner.cli = new ClientUI(owner,"localhost",txtUsername.getText());
                return true;
            }
        });
        stage.addActor(button2);

        Label nameLabel = new Label("Name:",mySkin,"title");
        nameLabel.setSize(col_width*3,row_height);
        nameLabel.setPosition(col_width*1,Gdx.graphics.getHeight()-row_height*5 + 15);
        nameLabel.setAlignment(Align.right);
        stage.addActor(nameLabel);

        txtUsername = new TextField("test", mySkin);
        txtUsername.setMessageText("test");
        txtUsername.setSize(col_width*7,row_height);
        txtUsername.setPosition(col_width*4,Gdx.graphics.getHeight()-row_height*5 + 15);
        stage.addActor(txtUsername);

        outputLabel = new Label("Press a Button",mySkin,"title");
        outputLabel.setSize(Gdx.graphics.getWidth(),row_height);
        outputLabel.setPosition(0,row_height);
        outputLabel.setAlignment(Align.center);
        stage.addActor(outputLabel);

    }

    @Override
    public void render(ApplicationAdapter owner, float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose () {
    }
}