package com.levicore.silvermoon.entities.ui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.levicore.silvermoon.entities.Entity;

/**
 * Created by Leonard on 1/16/2015.
 */
public class InfoBar extends Entity {

    private BitmapFont font = new BitmapFont();
    private Entity icon;

    float marginLeft = 15;
    float iconInset = 5;

    float fontWidth;
    float fontHeight;

    String text;

    public InfoBar(TextureRegion region, Entity icon) {
        super(region);
        this.icon = icon;

        setAlpha(0);
    }

    public InfoBar(String background, Entity icon) {
        super(background);
        this.icon = icon;

        setAlpha(0);
    }

    public InfoBar(Animation background, Entity icon) {
        super(background);
        this.icon = icon;

        setAlpha(0);
    }

    @Override
    public void update(float delta) {
        icon.setAlpha(getColor().a);
        icon.update(delta);
        super.update(delta);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        icon.draw(batch);
        font.draw(batch, text, icon.getX() + icon.getWidth() + iconInset, getY() + getHeight() - (fontHeight / 2) - 2);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(Entity icon) {
        this.icon = icon;
    }

    public void updateChildrenPosition() {
        icon.setPosition(getX() + marginLeft, getY() + ((getHeight() / 2) - (icon.getHeight() / 2)));
        fontWidth = font.getBounds(text).width;
        fontHeight = font.getBounds(text).height;
        setSize(icon.getWidth() + fontWidth + (marginLeft * 2) + (iconInset * 2), icon.getHeight() + 6);
    }

}
