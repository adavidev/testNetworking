package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.UIScreens.GameContext;
import com.mygdx.game.UIScreens.MenuUI;

public class TestNetworking extends ApplicationAdapter {
	public GameContext cli;
	
	@Override
	public void create () {
		cli = new MenuUI(this);
	}

	@Override
	public void render () {
		cli.render(this, Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		cli.dispose();
	}




}
