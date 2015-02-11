package com.levicore.silvermoon.entities.ui;

import aurelienribon.tweenengine.Timeline;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.levicore.silvermoon.entities.Entity;

/**
 * Created by Leonard on 1/8/2015.
 */
public class CircularMenu {

    public Option[] basicActions = new Option[3];
    public SkillOption[] options = new SkillOption[6];

    private float optionWidth = 32;
    private float optionHeight = 32;

    private float optionDistance = 16;

    private float x = 0;
    private float y = 0;

    public CircularMenu() {
        TextureAtlas textureAtlas = new TextureAtlas("640x480/pack.atlas");

        for (int i = 0; i < basicActions.length; i++) {
            basicActions[i] = new Option(textureAtlas.findRegion("IconSlotSmallCircle"), null);
        }

        basicActions[0].setIcon(new Entity("data/images/icons/sword_1.png"));
        basicActions[1].setIcon(new Entity("data/images/icons/skill_book.png"));
        basicActions[2].setIcon(new Entity("data/images/icons/shield_1.png"));

        for (int i = 0; i < options.length; i++) {
            options[i] = new SkillOption(textureAtlas.findRegion("IconSlotSmallCircle"), null);
            options[i].setSize(24, 24);
        }

        updatePositions();

        for (Option entity : basicActions) {
            entity.setAlpha(0);
        }

        for (Option option : options) {
            option.setAlpha(0);
        }

    }

    public void updatePositions() {
        basicActions[0].setPosition(x, y + optionWidth + optionDistance);
        basicActions[1].setPosition(x + optionWidth + optionDistance, y);
        basicActions[2].setPosition(x, y - optionDistance - optionHeight);

        options[0].setPosition(basicActions[1].getX(), basicActions[1].getY());
        options[1].setPosition(basicActions[1].getX(), basicActions[1].getY());

        options[2].setPosition(basicActions[2].getX(), basicActions[2].getY());
        options[3].setPosition(basicActions[0].getX(), basicActions[0].getY());

        options[4].setPosition(basicActions[2].getX(), basicActions[2].getY());
        options[5].setPosition(basicActions[0].getX(), basicActions[0].getY());
    }

    public void updateAndDraw(Batch batch, float delta) {
        for (Option basicAction : basicActions) {
            basicAction.update(delta);
            basicAction.draw(batch);
        }

        for (Option extendedOption : options) {
            extendedOption.update(delta);
            extendedOption.draw(batch);
        }
    }

    /**
     * Basic Actions
     */
    public Timeline transitionInExtendedOptions(float duration) {
        Timeline timeline = Timeline.createParallel();

        timeline.push(fadeInExtendedOptions(duration));

        timeline.push(options[0].moveTo(basicActions[1].getX() + optionWidth + optionDistance, basicActions[1].getY() + optionHeight + optionDistance, duration));
        timeline.push(options[1].moveTo(basicActions[1].getX() + optionWidth + optionDistance, basicActions[1].getY() - optionHeight - optionDistance, duration));

        timeline.push(options[2].moveTo(basicActions[2].getX() + optionWidth, basicActions[2].getY() - optionDistance - optionHeight, duration));
        timeline.push(options[3].moveTo(basicActions[0].getX() + optionWidth, basicActions[0].getY() + optionDistance + optionHeight, duration));

        timeline.push(options[4].moveTo(basicActions[2].getX() - optionWidth * 2, basicActions[2].getY() - optionDistance - optionHeight, duration));
        timeline.push(options[5].moveTo(basicActions[0].getX() - optionWidth * 2, basicActions[0].getY() + optionDistance + optionHeight, duration));

        return timeline;
    }

    public Timeline transitionOutExtendedOptions(float duration) {
        Timeline timeline = Timeline.createParallel();

        timeline.push(fadeOutExtendedOptions(duration));

        timeline.push(options[0].moveTo(basicActions[1].getX(), basicActions[1].getY(), duration));
        timeline.push(options[1].moveTo(basicActions[1].getX(), basicActions[1].getY(), duration));

        timeline.push(options[2].moveTo(basicActions[2].getX(), basicActions[2].getY(), duration));
        timeline.push(options[3].moveTo(basicActions[0].getX(), basicActions[0].getY(), duration));

        timeline.push(options[4].moveTo(basicActions[2].getX(), basicActions[2].getY(), duration));
        timeline.push(options[5].moveTo(basicActions[0].getX(), basicActions[0].getY(), duration));

        return timeline;
    }

    public Timeline fadeInBasicActions(float duration) {
        Timeline timeline = Timeline.createParallel();
        for (Option basicAction : basicActions) {
            timeline.push(basicAction.fadeIn(duration));
        }

        return timeline;
    }

    public Timeline hide(float duration) {
        Timeline timeline = Timeline.createParallel();

        timeline.push(transitionOutExtendedOptions(duration));
        timeline.push(fadeOutAll(duration));

        return timeline;
    }

    public Timeline fadeOutBasicActions(float duration) {
        Timeline timeline = Timeline.createParallel();
        for (Option basicAction : basicActions) {
            timeline.push(basicAction.fadeOut(duration));
        }

        return timeline;
    }


    /**
     * Extended Options
     */
    public Timeline fadeInExtendedOptions(float duration) {
        Timeline timeline = Timeline.createParallel();
        for (Option extendedOption : options) {
            timeline.push(extendedOption.fadeIn(duration));
        }

        return timeline;
    }


    public Timeline fadeOutExtendedOptions(float duration) {
        Timeline timeline = Timeline.createParallel();

        for (Option extendedOption : options) {
            timeline.push(extendedOption.fadeOut(duration));
        }

        return timeline;
    }


    /**
     * Animation Macros
     */
    public Timeline resetScale(float duration) {
        Timeline timeline = Timeline.createParallel();

        for (Option basicAction : basicActions) {
            timeline.push(basicAction.scaleTo(1, duration));
        }

        for (Option extendedOption : options) {
            timeline.push(extendedOption.scaleTo(1, duration));
        }

        return timeline;

    }

    public Timeline fadeInAll(float duration) {
        Timeline timeline = Timeline.createParallel();
        for (Option basicAction : basicActions) {
            timeline.push(basicAction.fadeIn(duration));
        }

        for (Option extendedOption : options) {
            timeline.push(extendedOption.fadeIn(duration));
        }

        return timeline;
    }

    public Timeline fadeOutAll(float duration) {
        Timeline timeline = Timeline.createParallel();
        for (Option basicAction : basicActions) {
            timeline.push(basicAction.fadeOut(duration));
        }

        for (Option extendedOption : options) {
            timeline.push(extendedOption.fadeOut(duration));
        }

        return timeline;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        updatePositions();
    }

    public void setPosition(Entity entity) {
        x = entity.getX();
        y = entity.getY();

        updatePositions();
    }

}