package com.levicore.silvermoon.entities.ui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.levicore.silvermoon.State;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.utils.menu.Selection;

import java.util.List;

/**
 * Created by Leonard on 1/24/2015.
 */
public class OneSelectionMenu extends Selection {

    private State state;
    private List<Animation> animations;
    private Entity selectedOption;

    public OneSelectionMenu(State state, List<Animation> animations, Entity selectedOption) {
        this.state = state;
        this.animations = animations;
        this.selectedOption = selectedOption;
    }

    @Override
    public void next() {
        super.next();
        selectedOption.setAnimation(animations.get(current), false).start(state.getTweenManager());
    }

    @Override
    public void previous() {
        super.previous();
        selectedOption.setAnimation(animations.get(current), false).start(state.getTweenManager());
    }

}
