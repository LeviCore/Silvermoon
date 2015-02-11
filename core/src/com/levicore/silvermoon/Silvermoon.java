package com.levicore.silvermoon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.levicore.silvermoon.core.Bonus;
import com.levicore.silvermoon.utils.debug.DebugState;
import test.StateTest;

public class Silvermoon extends ApplicationAdapter {

	// TODO Database
	// TODO : MOVE ALL ASSETS TO DATABASE

	public static String TITLE = "Silvermoon";
	public static int SCREEN_WIDTH = 640;
	public static int SCREEN_HEIGHT = 480;

	private GSM gsm;
	private InputMultiplexer inputMultiplexer;
	private SpriteBatch spriteBatch;
	private BitmapFont bitmapFont;

	boolean selfSwitch = true;

	@Override
	public void create () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);

		gsm = new GSM(this);
		inputMultiplexer = new InputMultiplexer();
		spriteBatch = new SpriteBatch();
		bitmapFont = new BitmapFont();

		Gdx.input.setInputProcessor(inputMultiplexer);
		Assets.queueLoading();
    }

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float delta = Gdx.graphics.getDeltaTime();

		if(Assets.update()) {
			if(selfSwitch) {
				Assets.initResources();

				gsm.push(new StateTest(gsm));
                gsm.push(new DebugState(gsm));

				selfSwitch = false;
			}

			gsm.update(delta);
			gsm.render(spriteBatch, delta);
		}
	}

	@Override
	public void resize(int width, int height) {
		gsm.resizeStates();
	}

	@Override
	public void dispose() {
		Assets.dispose();
	}

	/**
	 * Accessor methods
	 **/
	public InputMultiplexer getInputMultiplexer() {
		return inputMultiplexer;
	}

	public BitmapFont getBitmapFont() {
		return bitmapFont;
	}
}