package com.levicore.silvermoon.entities.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.Character;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.TextEntity;
import com.levicore.silvermoon.entities.battle.LevelableBattler;

/**
 * Created by user on 2/10/2015.
 */
public class CharacterInfo extends Entity {

    // TODO : FINISH
    private Character character;

    private Entity background;
    private PercentageBar expBar;
    private TextEntity characterName;

    public CharacterInfo(Character character, Entity background, float x, float y) {
        super(character.getCharacterFace());

        this.character = character;
        this.background = background;

        setSize(96, 96);
        setPosition(x, y);

        expBar = new PercentageBar(Assets.SYSTEM_ATLAS.findRegion("HPBar"), 1, 1);
        expBar.setMaxWidth(96);
        expBar.setPosition(getX(), getY() - expBar.getHeight() - 5);

        characterName = new TextEntity(null, character.getBattleEntity().getName(), null, 0, expBar.getY() - 5, getWidth());
        characterName.setX(getX() - (characterName.getWidth() / 2) + (getWidth() / 2));
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(background != null) {
            background.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
            background.update(delta);
        }

        if(character.getBattleEntity() instanceof LevelableBattler) {
            LevelableBattler levelableBattler = (LevelableBattler) character.getBattleEntity();

            expBar.setCurrentValue(levelableBattler.getCurExp());
            expBar.setMaxValue(levelableBattler.getToNextLevel());
        }

        expBar.setColor(getColor().r, getColor().g, getColor().b, getColor().a);

        expBar.update(delta);

        characterName.setColor(getColor());
        characterName.update(delta);
    }

    @Override
    public void draw(Batch batch) {
        if(background != null) background.draw(batch);
        super.draw(batch);
        expBar.draw(batch);
        characterName.draw(batch);
    }

    public PercentageBar getExpBar() {
        return expBar;
    }

    public void setExpBar(PercentageBar expBar) {
        this.expBar = expBar;
    }


}
