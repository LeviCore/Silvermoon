package com.levicore.silvermoon.states;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.levicore.silvermoon.*;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.ui.OneSelectionMenu;
import com.levicore.silvermoon.states.chapter1.BasicRPGMap;
import com.levicore.silvermoon.utils.map.MapState;
import com.levicore.silvermoon.utils.menu.OptionCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 1/24/2015.
 */
public class Title extends State {

    // TODO Reposition Arrows based on width of option
    // TODO CHANGE BG_MUSIC!!!!
    // TODO CHANGE LOGO

    private Entity leftArrow;
    private Entity rightArrow;
    private Entity option;

    private OneSelectionMenu selection;

    public Title(final GSM gsm) {
        super(gsm);

        Entity background = new Entity(Assets.TITLE_ATLAS.findRegion("Title-BG"));
        background.setSize(Silvermoon.SCREEN_WIDTH, Silvermoon.SCREEN_HEIGHT);
        background.setPosition(-background.getWidth() / 2, -background.getHeight() / 2);
        background.setAlpha(0);
        entities.add(background);

        Entity logo = new Entity(Assets.TITLE_ATLAS.findRegion("Title-Logo"));
        logo.setPosition(-logo.getWidth() / 2, -logo.getHeight() / 2 + 80);
        logo.setAlpha(0);
        entities.add(logo);

        leftArrow = new Entity(Assets.TITLE_ATLAS.findRegion("Title-ArrowL"));
        leftArrow.setPosition(-Silvermoon.SCREEN_WIDTH / 3, -150);
        leftArrow.setAlpha(0);
        entities.add(leftArrow);

        rightArrow = new Entity(Assets.TITLE_ATLAS.findRegion("Title-ArrowR"));
        rightArrow.setPosition((Silvermoon.SCREEN_WIDTH / 3) - rightArrow.getWidth(), -150);
        rightArrow.setAlpha(0);
        entities.add(rightArrow);

        final List<Entity> sparkles = new ArrayList<>();
        sparkles.add(new Entity(Assets.TITLE_ATLAS.findRegion("Title-Sparkle01")));
        sparkles.add(new Entity(Assets.TITLE_ATLAS.findRegion("Title-Sparkle02")));
        sparkles.add(new Entity(Assets.TITLE_ATLAS.findRegion("Title-Sparkle03")));

        for(Entity sparkle : sparkles) {
            sparkle.setSize(Silvermoon.SCREEN_WIDTH, Silvermoon.SCREEN_HEIGHT);
            sparkle.setPosition(-sparkle.getWidth() / 2, -sparkle.getHeight() / 2);
            sparkle.setAlpha(0);
            entities.add(sparkle);
        }

        option = new Entity(Assets.TITLE_ATLAS.findRegion("Title-New01"));
        option.setPosition(-option.getWidth() / 2, leftArrow.getY());
        entities.add(option);

        selection = new OneSelectionMenu(this, Assets.TITLE_OPTION_ANIMATIONS, option);
        selection.addOption(new OptionCallback() {
            @Override
            public void execute() {
                Timeline.createSequence()
                        .push(fadeOutAll(1))
                        .push(Tween.call(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                gsm.setPeek(new BasicRPGMap(gsm));
                            }
                        }))
                .start(tweenManager);
            }
        });

        SoundManager.playBackgroundMusic(Assets.getMusic("battle"), 1, true).start(tweenManager);

        selection.addOption(new OptionCallback() {
            @Override
            public void execute() {
                System.out.print("Continue");
            }
        });

        selection.addOption(new OptionCallback() {
            @Override
            public void execute() {
                System.out.print("Exit");
            }
        });

        fadeInAll(1).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                Timeline.createParallel()
                        .push(leftArrow.moveTo(leftArrow.getX() - 10, leftArrow.getY(), 1))
                        .push(rightArrow.moveTo(rightArrow.getX() + 10, rightArrow.getY(), 1))
                        .repeatYoyo(Tween.INFINITY, 0)
                        .start(tweenManager);

                sparkles.get(0).fadeOut(1).repeatYoyo(Tween.INFINITY, 1).start(tweenManager);
                sparkles.get(1).fadeOut(1).repeatYoyo(Tween.INFINITY, 1.5f).start(tweenManager);
                sparkles.get(2).fadeOut(1).repeatYoyo(Tween.INFINITY, 2).start(tweenManager);
            }
        }).start(tweenManager);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(leftArrow.contains(worldCoordinates.x, worldCoordinates.y)) {
            SoundManager.playSound(Assets.getSound("cursor_1")).start(tweenManager);
            selection.previous();
        } else if(rightArrow.contains(worldCoordinates.x, worldCoordinates.y)) {
            SoundManager.playSound(Assets.getSound("cursor_1")).start(tweenManager);
            selection.next();
        } else if(option.contains(worldCoordinates.x, worldCoordinates.y)) {
            SoundManager.playSound(Assets.getSound("cursor_1")).start(tweenManager);
            selection.executeSelected();
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public void resize() {
    }

}
