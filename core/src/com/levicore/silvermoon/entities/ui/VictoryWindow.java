package com.levicore.silvermoon.entities.ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.levicore.silvermoon.*;
import com.levicore.silvermoon.Character;
import com.levicore.silvermoon.core.Item;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.states.BattleState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/9/2015.
 */
public class VictoryWindow {

    // TODO : FINISH THIS ASAP (Smoothen transitions, Exp gained tweening, connect to BattleState)

    private State battleState;
    private PHASE phase;

    private String text;

    private List<CharacterInfo> characterInfoList;
    private List<SimpleItemInfo> itemsGained;

    private Entity background;
    private BitmapFont bitmapFont;

    float x;
    float y;

    float width;
    float height;

    public float insetX = 30;
    public float insetY = 45;
    public float intervalX = 45;

    private float fontWidth;
    private float fontHeight;

    public VictoryWindow(State battleState, BitmapFont bitmapFont, List<Character> characters, List<Item> items) {
        this.battleState = battleState;
        phase = PHASE.CHARACTER_INFO;

        width = Silvermoon.SCREEN_WIDTH - 50;
        height = Silvermoon.SCREEN_HEIGHT / 3;

        x = -width / 2;
        y = -height / 2;

        background = new Entity(Assets.SYSTEM_ATLAS.findRegion("Skill_Info_Background"));
        background.setSize(width, height);

        float curX = x + insetX;
        float curY = y + insetY;

        characterInfoList = new ArrayList<>();

        text = "Tap to continue.";

        this.bitmapFont = bitmapFont != null ? bitmapFont : new BitmapFont();
        fontWidth = this.bitmapFont.getBounds(text).width;
        fontHeight = this.bitmapFont.getBounds(text).height;

        // Set default size of character face and position
        for(Character character : characters) {
            characterInfoList.add(new CharacterInfo(character, null, curX, curY));
            curX += intervalX + character.getCharacterFace().getWidth();
        }

        itemsGained = new ArrayList<>();

        for(Item item : items) {
            itemsGained.add(new SimpleItemInfo(item, bitmapFont));
        }

        for(SimpleItemInfo simpleItemInfo : itemsGained) {
//            simpleItemInfo.setPosition();
        }

    }

    public void update(float delta) {
        background.setPosition(x, y);
        background.update(delta);

        switch (phase) {
            case CHARACTER_INFO:
                for(CharacterInfo characterInfo : characterInfoList) {
                    characterInfo.update(delta);
                }

                break;
            case ITEMS_INFO:
                for(SimpleItemInfo simpleItemInfo : itemsGained) {
                    simpleItemInfo.update(delta);
                }
                break;
        }
    }

    public void render(SpriteBatch batch) {
        background.draw(batch);

        switch (phase) {
            case CHARACTER_INFO :
                for(CharacterInfo characterInfo : characterInfoList) {
                    characterInfo.draw(batch);
                }

                bitmapFont.draw(batch, text, -fontWidth / 2, -100  );

                break;
            case ITEMS_INFO :
                for(SimpleItemInfo simpleItemInfo : itemsGained) {
                    simpleItemInfo.draw(batch);
                }

                break;
        }
    }

    public void updateAndRender(float delta, SpriteBatch batch) {
        update(delta);
        render(batch);
    }


    // TODO : Execute phase switching here.
    public void next() {
        switch (phase) {
            case CHARACTER_INFO:
                Timeline.createSequence()
                        .push(fadeOutCharacterInfos(0.25f))
                        .push(fadeInItemsGained(1))
                        .push(Tween.call(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                phase = PHASE.ITEMS_INFO;
                            }
                        }))
                        .start(battleState.getTweenManager());

                break;
            case ITEMS_INFO:
                // TODO : CALL RETURN TO MAPSTATE
                fadeOutVictoryWindow(0.25f).start(battleState.getTweenManager());
                break;
        }
    }

    /**
     * Tweening and Animation Macros
     */
    private Timeline fadeInCharacterInfos(float duration) {
        Timeline timeline = Timeline.createParallel();

        for(CharacterInfo characterInfo : characterInfoList) {
            timeline.push(characterInfo.fadeIn(duration)    );
        }

        return timeline;
    }

    private Timeline fadeOutCharacterInfos(float duration) {
        Timeline timeline = Timeline.createParallel();

        for(CharacterInfo characterInfo : characterInfoList) {
            timeline.push(characterInfo.fadeOut(duration));
        }

        return timeline;
    }

    private Timeline fadeInItemsGained(float duration) {
        Timeline timeline = Timeline.createParallel();

        for(SimpleItemInfo simpleItemInfo : itemsGained) {
            timeline.push(simpleItemInfo.fadeIn(duration));
        }

        return timeline;
    }

    private Timeline fadeOutItemsGained(float duration) {
        Timeline timeline = Timeline.createParallel();

        for(SimpleItemInfo simpleItemInfo : itemsGained) {
            timeline.push(simpleItemInfo.fadeOut(duration));
        }

        return timeline;
    }

    private Timeline fadeOutVictoryWindow(float duration) {
        Timeline timeline = Timeline.createParallel();

        timeline.push(fadeOutItemsGained(duration))
                .push(fadeOutCharacterInfos(duration))
                .push(background.fadeOut(duration));

        return timeline;
    }

    private enum PHASE {
        CHARACTER_INFO, ITEMS_INFO
    }

}
