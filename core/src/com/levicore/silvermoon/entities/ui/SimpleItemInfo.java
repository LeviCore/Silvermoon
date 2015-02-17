package com.levicore.silvermoon.entities.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.core.Item;
import com.levicore.silvermoon.entities.TextEntity;
import com.sun.istack.internal.Nullable;

/**
 * Created by user on 2/11/2015.
 */
public class SimpleItemInfo extends Entity {

    // TODO : FINISH QUANTITY FEATURE
    private Item item;
    private BitmapFont bitmapFont;
    private TextEntity itemName;

    private float insetX = 16;

    public SimpleItemInfo(Item item, @Nullable BitmapFont bitmapFont) {
        super(item.getIcon());
        this.item = item;
        this.bitmapFont = bitmapFont != null ? bitmapFont : new BitmapFont();

        // Set to default size upon creation
        setSize(32, 32);

        float textHeight = this.bitmapFont.getBounds(item.getName()).height;
        itemName = new TextEntity(this.bitmapFont, item.getName(), null, getX() + getWidth() + insetX, getY() + (textHeight / 2) + (getHeight() / 2));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        itemName.setX(getX() + getWidth() + insetX);
        itemName.setY(getY() + (itemName.getHeight() / 2) + (getHeight() / 2));
        itemName.setColor(getColor());
        itemName.update(delta);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        itemName.draw(batch);
    }


}
