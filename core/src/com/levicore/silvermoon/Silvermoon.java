package com.levicore.silvermoon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.levicore.silvermoon.presets.characters.MainCharacters;
import com.levicore.silvermoon.states.Title;

public class Silvermoon extends ApplicationAdapter {

	// TODO Database
    // TODO Check if frame rate drops

	public static String TITLE = "Silvermoon";
	public static int SCREEN_WIDTH = 640;
	public static int SCREEN_HEIGHT = 480;

	private GSM gsm;
	private InputMultiplexer inputMultiplexer;
	private SpriteBatch spriteBatch;
	private BitmapFont bitmapFont;

	boolean selfSwitch = true;

    private boolean debug = true;

    public Player player;

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

                if(!Loader.PREFS.contains("save")) {
                    player = new Player();
                    Loader.savePlayer(player);
                } else {
                    player = Loader.loadPlayer();
                }


                gsm.push(new Title(gsm));

				selfSwitch = false;
			}

			gsm.update(delta);
			gsm.render(spriteBatch, delta);
		}

        if(debug) {
            Gdx.graphics.setTitle(TITLE + " " + Gdx.graphics.getFramesPerSecond());
        }
	}

    @Override
	public void resize(int width, int height) {
		gsm.resizeStates();
	}

	@Override
	public void dispose() {
		Assets.dispose();
        Loader.savePlayer(player);
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
