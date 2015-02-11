package com.levicore.silvermoon.entities.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.levicore.silvermoon.*;
import com.levicore.silvermoon.Character;
import com.levicore.silvermoon.entities.Entity;

/**
 * Created by user on 2/10/2015.
 */
public class CharacterInfo extends Entity {

    private float textwidth;

    // TODO : FINISH
    private Character character;

    private Entity background;
    private PercentageBar expBar;
    private BitmapFont bitmapFont;

    public CharacterInfo(Character character, Entity background, BitmapFont bitmapFont, float x, float y) {
        super(character.getCharacterFace());

        this.character = character;
        this.background = background;
        this.bitmapFont = bitmapFont != null ? bitmapFont : new BitmapFont();

        setSize(96, 96);
        setPosition(x, y);

        expBar = new PercentageBar(Assets.SYSTEM_ATLAS.findRegion("HPBar"), 1, 1);
        expBar.setMaxWidth(96);
        expBar.setPosition(getX(), getY() - expBar.getHeight() - 5);

        textwidth = this.bitmapFont.getWrappedBounds(character.getName(), getWidth()).width;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(background != null) {
            background.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
            background.update(delta);
        }

        expBar.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        expBar.update(delta);
    }

    @Override
    public void draw(Batch batch) {
        if(background != null) background.draw(batch);
        super.draw(batch);
        expBar.draw(batch);
        bitmapFont.drawWrapped(batch, character.getName(), getX() + (getWidth() / 2) - (textwidth / 2), expBar.getY() - 5, getWidth());
    }
}
