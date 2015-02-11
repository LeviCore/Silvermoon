package com.levicore.silvermoon.utils.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.levicore.silvermoon.GSM;
import com.levicore.silvermoon.Silvermoon;
import com.levicore.silvermoon.State;

/**
 * Created by Leonard on 12/29/2014.
 */
public class DebugState extends State {

    BitmapFont bmp;
    ShapeRenderer sr;

    float width = 32;
    float height = 32;

    public DebugState(GSM gsm) {
        super(gsm);

        bmp = new BitmapFont();
        sr = new ShapeRenderer();
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        Vector3 vector3 = new Vector3();
        vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        vector3 = camera.unproject(vector3);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        bmp.draw(batch, "FPS : " + Gdx.graphics.getFramesPerSecond() +
                        " X : " + vector3.x +
                        " , Y : " + vector3.y,
                -Silvermoon.SCREEN_WIDTH / 2, Silvermoon.SCREEN_HEIGHT / 2);

        bmp.draw(batch, width+ "x" + height, vector3.x, vector3.y);

        batch.end();

        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);

        sr.rect(vector3.x, vector3.y, width, height);

        sr.end();
    }

    @Override
    public boolean keyUp(int keycode) {

        switch (keycode) {
            case Input.Keys.A :
                width += 32;
                break;
            case Input.Keys.D :
                width -= 32;
                break;
            case Input.Keys.Q :
                height += 32;
                break;
            case Input.Keys.E :
                height -= 32;
        }

        return super.keyUp(keycode);
    }

    @Override
    public void resize() {
    }

}
