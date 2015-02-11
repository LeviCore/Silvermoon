package com.levicore.silvermoon.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Leonard on 1/7/2015.
 */
public class DrawableUtils {
    public static Drawable createDrawable(String texture) {
        return new TextureRegionDrawable(new TextureRegion(new Texture(texture)));
    }
}
