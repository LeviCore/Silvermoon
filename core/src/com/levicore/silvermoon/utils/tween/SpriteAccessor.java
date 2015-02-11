package com.levicore.silvermoon.utils.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteAccessor implements TweenAccessor<Sprite> {

    public static final int ROTATION = 2;
    public static final int POSXY = 1;
	public static final int ALPHA = 0;


	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch(tweenType) {
            case ROTATION :
                returnValues[0] = target.getRotation();
                return 1;
            case POSXY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                assert false;
                return -1;
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch(tweenType) {
            case ROTATION :
                target.setRotation(newValues[0]);
            case POSXY :
                target.setPosition(newValues[0], newValues[1]); break;
            case ALPHA :
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            default :
                assert false;
		}
	}

}