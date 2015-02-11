package com.levicore.silvermoon.utils.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Leonard on 1/8/2015.
 */
public class OrthographicCameraAccessor implements TweenAccessor<OrthographicCamera> {

    public static final int ZOOM = 0;
    public static final int POSXY = 1;

    @Override
    public int getValues(OrthographicCamera target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ZOOM :
                returnValues[0] = target.zoom;
                return 1;
            case POSXY :
                returnValues[0] = target.position.x;
                returnValues[1] = target.position.y;
                return 2;
            default:
                assert false;
        }

        return 0;
    }

    @Override
    public void setValues(OrthographicCamera target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ZOOM :
                target.zoom = newValues[0];
                target.update();
                break;
            case POSXY :
                target.position.x = newValues[0];
                target.position.y = newValues[1];
                break;
            default:
                assert false;
        }
    }
}
