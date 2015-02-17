package com.levicore.silvermoon.entities.ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.levicore.silvermoon.entities.Entity;

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
    private float faceImageDistanceFromDialogBoxX;
    private float faceImageDistanceFromDialogBoxY;
    private float textWrapWidth;

    NinePatch patch;

    public DialogBox(Texture texture) {
        super(texture);

        text = "";
        bitmapFont = new BitmapFont();
        visible = false;

        patch = new NinePatch(texture, 8, 8, 8, 5);
        patch.setColor(Color.RED);

        textWrapWidth = 100;
        textInsetX = 5;
        textInsetY = -3;
        textInsetBottomX = 10;
        textInsetBottomY = 10;
    }

    public DialogBox(TextureRegion region) {
        super(region);

        text = "";
        bitmapFont = new BitmapFont();
        visible = false;

        patch = new NinePatch(region, 8, 8, 8, 5);
        patch.setColor(Color.RED);

        textWrapWidth = 100;
        textInsetX = 5;
        textInsetY = -3;
        textInsetBottomX = 10;
        textInsetBottomY = 10;
    }

    public DialogBox(Animation animation) {
        super(animation);
        text = "";
        bitmapFont = new BitmapFont();
        visible = false;
    }

    @Override
    public void draw(Batch batch) {
        if(visible) {
//            super.draw(batch);
            patch.draw(batch, getX(), getY(), getWidth(), getHeight());
            if(faceImage != null) {
                faceImage.draw(batch);
            }
            bitmapFont.drawWrapped(batch, text, getX() + textInsetX, getY() + getHeight() + textInsetY, getWidth() - textInsetBottomX);
        }
    }

    public void setFaceImageDistance(float faceImageDistanceFromDialogBoxX, float faceImageDistanceFromDialogBoxY) {
        this.faceImageDistanceFromDialogBoxX = faceImageDistanceFromDialogBoxX;
        this.faceImageDistanceFromDialogBoxY = faceImageDistanceFromDialogBoxY;
    }

    /**
     * Control methods
     */
    public Tween show(final String message, final Entity entityTalking) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                text = message;
                visible = true;
                faceImage = null;

                setSize(bitmapFont.getWrappedBounds(text, textWrapWidth).width + textInsetBottomX, bitmapFont.getWrappedBounds(text, textWrapWidth).height + textInsetBottomY);
                setX((entityTalking.getX() + (entityTalking.getBoundingRectangle().width) / 2) - (getWidth() / 2));
                setY(entityTalking.getY() + (entityTalking.getBoundingRectangle().height));
            }
        });
    }

    public Tween show(final String message, final Entity entityTalking, final Entity newFaceImage) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                text = message;
                visible = true;
                faceImage = newFaceImage;

                setSize(bitmapFont.getWrappedBounds(text, textWrapWidth).width + textInsetBottomX, bitmapFont.getWrappedBounds(text, textWrapWidth).height + textInsetBottomY);
                setX((entityTalking.getX() + (entityTalking.getBoundingRectangle().width) / 2) - (getWidth() / 2));
                setY(entityTalking.getY() + (entityTalking.getBoundingRectangle().height));

                faceImage.setX(getX() + faceImageDistanceFromDialogBoxX);
                faceImage.setY(getY() + faceImageDistanceFromDialogBoxY);
            }
        });
    }

    public Tween hide() {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                visible = false;
            }
        });
    }

    public boolean isVisible() {
        return visible;
    }
}