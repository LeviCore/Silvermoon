package com.levicore.silvermoon.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sun.istack.internal.Nullable;

/**
 * Created by user on 2/12/2015.
 */
public class TextEntity {

    private BitmapFont bitmapFont;

    private String text;
    private float wrapWidth;

    private float width;
    private float height;

    private float x;
    private float y;

    private Color color;

    public TextEntity(@Nullable BitmapFont bitmapFont, String text, Color color, float x, float y) {
        this.bitmapFont = bitmapFont != null ? bitmapFont : new BitmapFont();
        this.text = text;

        this.x = x;
        this.y = y;

        this.color = color;

        update(0);

        wrapWidth = 0;
    }

    public TextEntity(@Nullable BitmapFont bitmapFont, String text, Color color, float x, float y, float wrapWidth) {
        this.bitmapFont = bitmapFont != null ? bitmapFont : new BitmapFont();
        this.text = text;

        this.x = x;
        this.y = y;

        this.color = color;

        update(0);

        this.wrapWidth = wrapWidth;
    }

    public void update(float delta) {
        BitmapFont.TextBounds textBounds = wrapWidth != 0 ? bitmapFont.getWrappedBounds(text, wrapWidth) : bitmapFont.getBounds(text);
        width = textBounds.width;
        height = textBounds.height;
    }

    public void draw(Batch batch) {
        if(wrapWidth != 0) {
            bitmapFont.drawWrapped(batch, text, x, y, wrapWidth);
        } else {
            bitmapFont.draw(batch, text, x, y);
        }
    }

    /**
     * Setters
     */
    public void setText(String text) {
        this.text = text;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWrapWidth(float wrapWidth) {
        this.wrapWidth = wrapWidth;
    }

    public void setColor(Color color) {
        bitmapFont.setColor(color);
    }
}
