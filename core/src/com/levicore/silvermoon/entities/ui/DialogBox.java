package com.levicore.silvermoon.entities.ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.TextEntity;

/**
 * Created by Leonard on 1/1/2015.
 */
public class DialogBox extends Entity {

    private BitmapFont bitmapFont;

    private boolean visible;
    private String text;

    private float textInsetX;
    private float textInsetY;
    private float textInsetBottomX;
    private float textInsetBottomY;

    private Entity faceImage;
    private float textWrapWidth;

    public DialogBox(Texture texture) {
        super(texture);

        text = "";
        bitmapFont = new BitmapFont();
        visible = false;

        textWrapWidth = 100;
        textInsetX = 5;
        textInsetY = -3;
        textInsetBottomX = 10;
        textInsetBottomY = 10;

        setAlpha(0);
    }

    public DialogBox(TextureRegion region) {
        super(region);

        text = "";
        bitmapFont = new BitmapFont();
        visible = false;

        textWrapWidth = 100;
        textInsetX = 5;
        textInsetY = -3;
        textInsetBottomX = 10;
        textInsetBottomY = 10;

        setAlpha(0);
    }

    public DialogBox(Animation animation) {
        super(animation);
        text = "";
        bitmapFont = new BitmapFont();
        visible = false;

        setAlpha(0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        bitmapFont.setColor(getColor());


        if(faceImage != null) {
            faceImage.setColor(getColor());
            faceImage.update(delta);
        }
    }

    @Override
    public void draw(Batch batch) {
        if(visible) {
            super.draw(batch);
            if(faceImage != null) {
                faceImage.draw(batch);
            }
            bitmapFont.drawWrapped(batch, text, getX() + textInsetX, getY() + getHeight() + textInsetY, getWidth() - textInsetBottomX);
        }
    }

    /**
     * Control methods
     */
    public Timeline show(final String message, final Entity entityTalking, float pauseDuration) {
        return  Timeline.createSequence()
                .push(fadeIn(0.25f))
                .push(Tween.call(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        text = message;
                        visible = true;
                        faceImage = null;

                        setSize(bitmapFont.getWrappedBounds(text, textWrapWidth).width + textInsetBottomX, bitmapFont.getWrappedBounds(text, textWrapWidth).height + textInsetBottomY);
                        setX((entityTalking.getX() + (entityTalking.getBoundingRectangle().width) / 2) - (getWidth() / 2));
                        setY(entityTalking.getY() + (entityTalking.getBoundingRectangle().height));
                    }
                }))
                .pushPause(pauseDuration)
                .push(fadeOut(0.25f));
    }

    public Timeline show(final String message, final Entity entityTalking, final Entity newFaceImage, final boolean flipX, float pauseDuration) {
        return  Timeline.createSequence()
                .push(fadeIn(0.25f))
                .push(Tween.call(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        text = message;
                        visible = true;
                        faceImage = newFaceImage;

                        setSize(bitmapFont.getWrappedBounds(text, textWrapWidth).width + textInsetBottomX, bitmapFont.getWrappedBounds(text, textWrapWidth).height + textInsetBottomY);
                        setX((entityTalking.getX() + (entityTalking.getBoundingRectangle().width) / 2) - (getWidth() / 2));
                        setY(entityTalking.getY() + (entityTalking.getBoundingRectangle().height));

                        if(flipX) {
                            faceImage.flip(true, false);
                            faceImage.setX(getX() + getWidth());
                        } else {
                            faceImage.setX(getX() - faceImage.getWidth());
                        }

                        faceImage.setY(getY());
                    }
                }))
                .pushPause(pauseDuration)
                .push(fadeOut(0.25f));
    }

    public boolean isVisible() {
        return visible;
    }

    public void setFaceImage(Entity faceImage) {
        this.faceImage = faceImage;
    }
}