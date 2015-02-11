package com.levicore.silvermoon.states;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.TweenCallback;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.GSM;
import com.levicore.silvermoon.Silvermoon;
import com.levicore.silvermoon.State;
import com.levicore.silvermoon.entities.Entity;

/**
 * Created by Leonard on 12/9/2014.
 */
public class Splash extends State {

    private Entity splash;

    public Splash(final GSM gsm) {
        super(gsm);

        splash = new Entity(Assets.getTexture("splash"));
        splash.setSize(camera.viewportWidth, camera.viewportHeight);
        splash.setPosition(-Silvermoon.SCREEN_WIDTH / 2, -Silvermoon.SCREEN_HEIGHT / 2);
        splash.setAlpha(0);

        Timeline.createSequence()
                .push(splash.fadeIn(1))
                .pushPause(1)
                .push(splash.fadeOut(1))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        gsm.setPeek(new Title(gsm));
                    }
                })
                .start(tweenManager);

        entities.add(splash);
    }

    @Override
    public void resize() {
    }

}
