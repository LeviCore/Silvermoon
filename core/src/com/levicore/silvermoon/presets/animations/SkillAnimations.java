package com.levicore.silvermoon.presets.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.levicore.silvermoon.Assets;

/**
 * Created by Leonard on 1/30/2015.
 */
public class SkillAnimations {

    public static Animation SWORD_SLASH() {
        return new Animation(0.05f, Assets.ANIMATIONS.findRegion("Sword", 2),
                                    Assets.ANIMATIONS.findRegion("Sword", 3),
                                    Assets.ANIMATIONS.findRegion("Sword", 4),
                                    Assets.ANIMATIONS.findRegion("Sword", 5),
                                    Assets.ANIMATIONS.findRegion("Sword", 6)
        );
    }

}
