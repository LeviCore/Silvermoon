package com.levicore.silvermoon.entities.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.core.Item;

/**
 * Created by user on 2/11/2015.
 */
public class SimpleItemInfo extends Entity {

    // TODO : FINISH THIS 256x32 -> [icon] + [name] + x[quantity]
    private BitmapFont bitmapFont;

    public SimpleItemInfo(Item item, BitmapFont bitmapFont) {
//        super(item);
        this.bitmapFont = bitmapFont != null ? bitmapFont : new BitmapFont();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
