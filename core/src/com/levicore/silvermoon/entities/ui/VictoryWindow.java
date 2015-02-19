package com.levicore.silvermoon.entities.ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.Character;
import com.levicore.silvermoon.Silvermoon;
import com.levicore.silvermoon.State;
import com.levicore.silvermoon.core.Item;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.TextEntity;
import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.entities.battle.LevelableBattler;

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

    private List<Character> characters;
    private List<CharacterInfo> characterInfoList;
    private List<SimpleItemInfo> itemsGained;

    private TextEntity instruction;
    private TextEntity itemDescription;

    private Entity background;

    float x;
    float y;

    float width;
    float height;

    public float insetX = 30;
    public float insetY = 45;
    public float intervalX = 45;

    private float expGained;

    public VictoryWindow(State battleState, List<Character> characters, List<Item> items, float expGained ) {
        this.battleState = battleState;
        this.characters = characters;

        this.expGained = expGained;

        phase = PHASE.UNSTARTED;

        width = Silvermoon.SCREEN_WIDTH - 50;
        height = Silvermoon.SCREEN_HEIGHT / 3;

        x = -width / 2;
        y = -height / 2;

        background = new Entity(Assets.SYSTEM_ATLAS.findRegion("Skill_Info_Background"));
        background.setSize(width, height);
        background.setPosition(x, y);
        background.setAlpha(0);

        float curX = x + insetX;
        float curY = y + insetY;

        characterInfoList = new ArrayList<>();

        text = "Tap to continue.";

        instruction = new TextEntity(null, text, null, 0, 0);
        instruction.setX(-instruction.getWidth() / 2);
        instruction.setY(-100);

        itemDescription = new TextEntity(null, "", null, 0, 0);

        // Set default size of character face and position
        for(Character character : characters) {
            characterInfoList.add(new CharacterInfo(character, null, curX, curY));
            curX += intervalX + character.getCharacterFace().getWidth();
        }

        for(CharacterInfo characterInfo : characterInfoList) {
            characterInfo.setAlpha(0);
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

        itemDescription.update(delta);
        itemDescription.setColor(background.getColor());

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
        itemDescription.draw(batch);
    }

    public void updateAndRender(float delta, SpriteBatch batch) {
        update(delta);
        render(batch);
    }

    public Timeline next() {
        switch (phase) {
            case UNSTARTED :
                return Timeline.createSequence()
                        .push(fadeInVictoryWindow(0.25f))
                        .push(addExpGainedToBattlers(expGained, 0.25f))
                        .push(Tween.call(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                phase = PHASE.CHARACTER_INFO;
                            }
                        }))
                        .start(battleState.getTweenManager());
            case CHARACTER_INFO:
                return Timeline.createSequence()
                        .push(fadeOutCharacterInfos(0.25f))
                        .push(fadeInItemsGained(0.25f))
                        .push(Tween.call(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                if(itemsGained.isEmpty()) {
                                    itemDescription.setText("No items gained.");
                                    itemDescription.setX(-itemDescription.getWidth() / 2);
                                }

                                phase = PHASE.ITEMS_INFO;
                            }
                        }))
                        .start(battleState.getTweenManager());
            case ITEMS_INFO:
                return fadeOutVictoryWindow(0.25f).start(battleState.getTweenManager());
        }

        return null;
    }

    /**
     * Tweening and Animation Macros
     */
    private Timeline addExpGainedToBattlers(float expToAdd, float duration) {
        Timeline timeline = Timeline.createParallel();

        for(Character character : characters) {
            if(character.getBattleEntity() instanceof LevelableBattler) {
                timeline.push(((LevelableBattler) character.getBattleEntity()).addExp(expToAdd, duration));
            }
        }

        return timeline;
    }

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

    private Timeline fadeInVictoryWindow(float duration) {
        Timeline timeline = Timeline.createParallel();

        timeline.push(background.fadeIn(duration))
                .push(fadeInCharacterInfos(duration));


        return timeline;
    }

    private Timeline fadeOutVictoryWindow(float duration) {
        Timeline timeline = Timeline.createParallel();

        timeline.push(fadeOutItemsGained(duration))
                .push(fadeOutCharacterInfos(duration))
                .push(background.fadeOut(duration));

        return timeline;
    }

    public PHASE getPhase() {
        return phase;
    }

    public enum PHASE {
        UNSTARTED, CHARACTER_INFO, ITEMS_INFO
    }

}
