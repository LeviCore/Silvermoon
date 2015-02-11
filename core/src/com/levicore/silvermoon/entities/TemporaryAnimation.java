package com.levicore.silvermoon.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Leonard on 9/21/2014.
 */
public class TemporaryAnimation extends Sprite {

    private Animation animation;
    private float stateTime;

    /** Constructor, initialize and set parameters.
     *
     * @param x
     * @param y
     * @param animation
     */
    public TemporaryAnimation(float x, float y, Animation animation) {
        this.animation = animation;

        setRegion(animation.getKeyFrame(0));
        setBounds(x, y, animation.getKeyFrame(stateTime).getRegionWidth(), animation.getKeyFrame(stateTime).getRegionHeight());
        stateTime = 0;
    }

    /** Override draw method.
     *
     * @param batch
     */
    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    /** Update method, called before
     *
     * @param delta
     */
    private void update(float delta) {
        /** Update frame */
        setRegion(animation.getKeyFrame(stateTime));

        /** Dispose if animation is finished */
        if(animation.isAnimationFinished(stateTime)) {
            getTexture().dispose();
        }

        /**
         * Increment stateTime to update
         * temporary animation to next frame.
         */
        stateTime += delta;
    }

    /**
     * Getters
     */
    public Animation getAnimation() {
        return animation;
    }

    public float getStateTime() {
        return stateTime;
    }
}
