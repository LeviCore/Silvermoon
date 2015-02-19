package com.levicore.silvermoon.entities.ui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.levicore.silvermoon.entities.Entity;

/**
 * Created by Leonard on 1/13/2015.
 */
public class PercentageBar extends Entity {

    private float maxValue;
    private float currentValue;
    private boolean visible = true;
    private float maxWidth = 32;

    public PercentageBar(String texture, float maxValue, float currentValue) {
        super(texture);
        this.maxValue = maxValue;
        this.currentValue = currentValue;

        init();
    }

    public PercentageBar(Animation animation, float maxValue, float currentValue) {
        super(animation);
        this.maxValue = maxValue;
        this.currentValue = currentValue;

        init();
    }

    public PercentageBar(TextureRegion region) {
        super(region);
        init();
    }

    public PercentageBar(TextureRegion region, float maxValue, float currentValue) {
        super(region);

        this.maxValue = maxValue;
        this.currentValue = currentValue;

        init();
    }

    public PercentageBar(String texture) {
        super(texture);
        init();
    }

    public PercentageBar(Animation animation) {
        super(animation);
        init();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setSize(maxWidth * (currentValue / maxValue) > maxWidth ? maxWidth : maxWidth * (currentValue / maxValue), getHeight());
    }

    @Override
    public void draw(Batch batch) {
        if(currentValue > 0 && visible) {
            super.draw(batch);
        }
    }

    public float getMaxValue() {
        return maxValue;
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public void init() {
        setSize(64, 5);
    }

    public float getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
