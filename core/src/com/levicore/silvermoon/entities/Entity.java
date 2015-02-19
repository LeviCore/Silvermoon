package com.levicore.silvermoon.entities;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.levicore.silvermoon.utils.tween.EntityAccessor;

/**
 * Created by Leonard on 12/27/2014.
 */
public class Entity extends Sprite {

    protected float stateTime;
    protected Animation animation;

    private boolean visible = true;

    protected Entity() {
        super();
    }

    public Entity(String texture) {
        super(new Texture(texture));
    }

    public Entity(Texture texture) {
        super(texture);
    }

    public Entity(TextureRegion region) {
        super(region);
    }

    public Entity(Animation animation) {
        super(animation.getKeyFrame(0));
        this.animation = animation;
        setSize(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());

        stateTime = 0;
    }

    public void update(float delta) {
        if(animation != null) {
            setRegion(animation.getKeyFrame(stateTime));
            stateTime += delta;
        }
    }

    @Override
    public void draw(Batch batch) {
        if(visible) super.draw(batch);
    }

    /**
     * Convinience methods
     */
    public boolean contains(float x, float y) {
        if(getBoundingRectangle().contains(x, y)) {
            return true;
        }

        return false;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Tweens
     */
    public Tween setGraphic(final String image) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                stateTime = 0;
                animation = null;
                getTexture().dispose();
                setTexture(new Texture(image));
                setSize(getTexture().getWidth(), getTexture().getHeight());
            }
        });
    }

    /**
     * A void method does not return a Timeline
     * Note : does not restart state Time
     */
    public void setAnimation_nonReset_void(Animation newAnimation) {
        animation = newAnimation;
        setSize(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());
    }

    public Tween setAnimation(final Animation newAnimation) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                if(animation != null) {
                    stateTime = 0;
                    animation = newAnimation;
                    setSize(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());
                }
            }
        });
    }

    public Tween setAnimation(final Animation newAnimation, final boolean resize) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                stateTime = 0;
                animation = newAnimation;
                if(resize) {
                    setSize(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());
                }
            }
        });
    }

    public Tween setAnimation(final Animation newAnimation, final Animation.PlayMode playMode) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                stateTime = 0;
                animation = newAnimation;
                animation.setPlayMode(playMode);
                setSize(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());
            }
        });
    }

    public Tween fadeIn(float duration) {
        return Tween.to(this, EntityAccessor.ALPHA, duration).target(1);
    }

    public Tween fadeOut(float duration) {
        return Tween.to(this, EntityAccessor.ALPHA, duration).target(0);
    }

    public Tween fadeTo(float target, float duration) {
        return Tween.to(this, EntityAccessor.ALPHA, duration).target(target);
    }

    public Tween moveTo(float x, float y, float duration) {
        return Tween.to(this, EntityAccessor.POSXY, duration).target(x, y);
    }

    public Tween rotateTo(float angle, float duration) {
        return Tween.to(this, EntityAccessor.ROTATION, duration).target(angle);
    }

    public Tween scaleTo(float scale, float duration) {
        return Tween.to(this, EntityAccessor.SCALE, duration).target(scale);
    }

}