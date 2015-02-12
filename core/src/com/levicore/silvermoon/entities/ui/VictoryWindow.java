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
import com.levicore.silvermoon.entities.TextEntity;
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

    private TextEntity instruction;

    private Entity background;

    float x;
    float y;

    float width;
    float height;

    public float insetX = 30;
    public float insetY = 45;
    public float intervalX = 45;

    private float fontWidth;
    private float fontHeight;

    public VictoryWindow(State battleState, List<Character> characters, List<Item> items) {
        this.battleState = battleState;
        phase = PHASE.CHARACTER_INFO;

        width = Silvermoon.SCREEN_WIDTH - 50;
        height = Silvermoon.SCREEN_HEIGHT / 3;

        x = -width / 2;
        y = -height / 2;

        background = new Entity(Assets.SYSTEM_ATLAS.findRegion("Skill_Info_Background"));
        background.setSize(width, height);
        background.setPosition(x, y);

        float curX = x + insetX;
        float curY = y + insetY;

        characterInfoList = new ArrayList<>();

        text = "Tap to continue.";

        instruction = new TextEntity(null, text, null, 0, 0);
        instruction.setX(-instruction.getWidth() / 2);
        instruction.setY(-100);

        // Set default size of character face and position
        for(Character character : characters) {
            characterInfoList.add(new CharacterInfo(character, null, curX, curY));
            curX += intervalX + character.getCharacterFace().getWidth();
        }

        itemsGained = new ArrayList<>();

        for(Item item : items) {
            itemsGained.add(new SimpleItemInfo(item, null));
        }

        curX = 0;
        curY = 0;

        for(SimpleItemInfo simpleItemInfo : itemsGained) {
            simpleItemInfo.setAlpha(0);
            simpleItemInfo.setPosition(background.getX() + insetX + 10 + curX, background.getY() + background.getHeight() - (insetY + 10) + curY);
            curY += -simpleItemInfo.getHeight() - 5;

            if(curY == -37 * 3) {
                curX += 200;
                curY += 37 * 3;
            }
        }

    }

    public void update(float delta) {
        background.update(delta);
        instruction.update(delta);
        instruction.setColor(background.getColor());

        for(CharacterInfo characterInfo : characterInfoList) {
            characterInfo.update(delta);
        }

        for(SimpleItemInfo simpleItemInfo : itemsGained) {
            simpleItemInfo.update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        background.draw(batch);

        for(CharacterInfo characterInfo : characterInfoList) {
            characterInfo.draw(batch);
        }

        for(SimpleItemInfo simpleItemInfo : itemsGained) {
            simpleItemInfo.draw(batch);
        }

        instruction.draw(batch);
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
                        .push(fadeInItemsGained(0.25f))
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
            timeline.push(characterInfo.fadeIn(duration));
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
