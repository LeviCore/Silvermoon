package com.levicore.silvermoon.utils.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Leonard on 2/7/2015.
 */
public class SpriteBatchAccessor implements TweenAccessor<SpriteBatch> {

    public static final int ALPHA = 0;

    @Override
    public int getValues(SpriteBatch target, int tweenType, float[] returnValues) {

        switch (tweenType) {
            case ALPHA :
                returnValues[0] = target.getColor().a;
                break;
        }

        return 1;
    }

    @Override
    public void setValues(SpriteBatch target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA :
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
        }

    }
}
