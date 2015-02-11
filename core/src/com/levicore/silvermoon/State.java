package com.levicore.silvermoon;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.TemporaryAnimation;
import com.levicore.silvermoon.entities.ui.Option;
import com.levicore.silvermoon.utils.tween.EntityAccessor;
import com.levicore.silvermoon.utils.tween.OrthographicCameraAccessor;
import com.levicore.silvermoon.utils.tween.SpriteBatchAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 12/9/2014.
 *
 * entites : base list for entities to draw
 *
 * temporaryAnimations : list for temporary animation,
 * temporary animations disposes itself upon end of animation
 * temporaryAnimations draws above normal entities
 *
 * temporaryEntities : list for seperate entites, draws above
 * all other entities -> Not drawn by default. Call it in render method.
 *
 */
public abstract class State extends InputAdapter {

    protected GSM gsm;

    protected OrthographicCamera camera;
    protected OrthographicCamera uiCamera;

    protected TweenManager tweenManager;

    protected Vector3 worldCoordinates;
    protected Vector3 uiCoordinates;

    protected ShapeRenderer sr;
    protected BitmapFont font;

    protected List<Entity> entities;
    protected List<TemporaryAnimation> temporaryAnimations;
    protected List<Entity> temporaryEntities;

    protected State(GSM gsm) {
        camera = new OrthographicCamera(Silvermoon.SCREEN_WIDTH, Silvermoon.SCREEN_HEIGHT);
        uiCamera = new OrthographicCamera(Silvermoon.SCREEN_WIDTH, Silvermoon.SCREEN_HEIGHT);

        entities = new ArrayList<>();
        temporaryAnimations = new ArrayList<>();
        temporaryEntities = new ArrayList<>();
        tweenManager = new TweenManager();

        worldCoordinates = new Vector3();
        uiCoordinates = new Vector3();

        sr = new ShapeRenderer();
        font = new BitmapFont();

        Tween.registerAccessor(Entity.class, new EntityAccessor());
        Tween.registerAccessor(SpriteBatch.class, new SpriteBatchAccessor());
        Tween.registerAccessor(OrthographicCamera.class, new OrthographicCameraAccessor());

        this.gsm = gsm;

        initialize();
    }

    /**
     * Initialization methods for entities
     */
    protected void initialize() {
        Timeline timeline = Timeline.createSequence();

        timeline.push(fadeOutAll(0))
                .pushPause(1)
                .push(fadeInAll(1))
                .start(tweenManager);

        tweenManager.update(Float.MIN_VALUE);
    }

    public void update(float delta) {
    }

    public void render(SpriteBatch batch, float delta) {
        worldCoordinates.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        worldCoordinates = camera.unproject(worldCoordinates);

        uiCoordinates.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        uiCoordinates = uiCamera.unproject(uiCoordinates);

        tweenManager.update(delta);

        for(Entity entity : entities) {
            if(entity != null) entity.update(delta);
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for(Entity entity : entities) {
            if(entity != null) entity.draw(batch);
        }

        batch.end();
    }

    /**
     * Override if you dispose entities not in AssetManager,
     * Also to dispose custom Batches
     */
    public void dispose() {
//        for(Entity entity : entities) {
//            if(entity != null) entity.getTexture().dispose();
//        }
//
//        for(Entity entity : temporaryEntities) {
//            if(entity != null) entity.getTexture().dispose();
//        }
//
//        for(TemporaryAnimation animation : temporaryAnimations) {
//            if(animation != null) animation.getTexture().dispose();
//        }
    }

    public abstract void resize();

    /**
     * Temporary Animation
     */
    public void createTemporaryAnimation(float x, float y, Animation animation) {
        temporaryAnimations.add(new TemporaryAnimation(x, y, animation));
    }

    public Tween createTemporaryTweenAnimation(final float x, final float y, final Animation animation) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                temporaryAnimations.add(new TemporaryAnimation(x, y, animation));
            }
        });
    }

    public Tween createTemporaryTweenAnimation(final float width, final float height, final float x, final float y, final Animation animation) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                TemporaryAnimation temporaryAnimation = new TemporaryAnimation(x, y, animation);
                temporaryAnimation.setSize(32, 23);
                temporaryAnimations.add(temporaryAnimation);
            }
        });
    }

    public void renderTemporaryAnimations(SpriteBatch batch) {
        for (int i = 0; i < temporaryAnimations.size(); i++) {
            TemporaryAnimation temporaryAnimation = temporaryAnimations.get(i);
            temporaryAnimation.draw(batch);

            if (temporaryAnimation.getAnimation().isAnimationFinished(temporaryAnimation.getStateTime())) {
                temporaryAnimations.remove(temporaryAnimation);
            }
        }
    }

    /**
     * Temporary Entity.
     */
    public void drawAndUpdateTemporaryEntities(float delta, SpriteBatch batch) {
        for(Entity temporaryEntity : temporaryEntities) {
            if (temporaryEntity != null) {
                temporaryEntity.update(delta);
                temporaryEntity.draw(batch);
            }
        }
    }

    public void addTemporaryEntity(Texture texture, float x, float y) {
        Entity temporaryEntity = new Entity(texture);
        temporaryEntity.setPosition(x, y);
        temporaryEntities.add(temporaryEntity);
    }

    public void addTemporaryEntity(Animation animation, float x, float y) {
        Entity temporaryEntity = new Entity(animation);
        temporaryEntity.setPosition(x, y);
        temporaryEntities.add(temporaryEntity);
    }

    public List<Entity> getTemporaryEntities() {
        return temporaryEntities;
    }

    /**
     * Camera control
     */
    public Tween setZoom(float zoom, float duration) {
        return Tween.to(camera, OrthographicCameraAccessor.ZOOM, duration).target(zoom);
    }

    public Tween toCameraPosition(float x, float y, float duration) {
        return Tween.to(camera, OrthographicCameraAccessor.POSXY, duration).target(x, y);
    }

    public Timeline zoomToEntity(Entity entity, float zoom, float duration) {
        Timeline timeline = Timeline.createParallel();
        timeline.push(toCameraPosition(entity.getX() + (entity.getWidth() / 2), entity.getY() + (entity.getHeight() / 2), duration));
        timeline.push(setZoom(zoom, duration));

        return timeline;
    }

    public Timeline zoomTo(float x, float y, float zoom, float duration) {
        Timeline timeline = Timeline.createParallel();

        timeline.push(toCameraPosition(x, y, duration));
        timeline.push(setZoom(zoom, duration));

        return timeline;
    }

    /**
     * Animation Macros
     */
    public Timeline fadeInAll(float duration) {
        Timeline timeline = Timeline.createParallel();

        for(Entity entity : entities) {
            timeline.push(entity.fadeIn(duration));
        }

        return timeline;
    }

    public Timeline fadeOutAll(float duration) {
        Timeline timeline = Timeline.createParallel();

        for(Entity entity : entities) {
            timeline.push(entity.fadeOut(duration));
        }

        for(Entity entity : temporaryEntities) {
            timeline.push(entity.fadeOut(duration));
        }

        return timeline;
    }

    public Timeline slideInEntities(Entity[] entities, float x, float xChange, float y, float yChange) {

        for(Entity entity : entities) {
            entity.setPosition(x, y);
        }

        Timeline timeline = Timeline.createParallel();

        for (int i = entities.length - 1; i >= 0; i--) {
            Entity entity = entities[i];
            timeline.push(entity.moveTo(x, y, 1));
            x += xChange;
            y += yChange;
        }

        return timeline;
    }

    /**
     * Kills tween on list.
     * @param entities
     */
    public void killAll(List<? extends Entity> entities) {
        for(Entity entity : entities) {
            tweenManager.killTarget(entity);
        }
    }

    public void killAll(Option[] entities) {
        for(Entity entity : entities) {
            tweenManager.killTarget(entity);
        }
    }

    public void restoreAlpha(List<? extends Entity> entities) {
        for(Entity entity : entities) {
            entity.fadeIn(1).start(tweenManager);
        }
    }

    public Timeline slideInEntities(List<Entity> entities, float x, float xChange, float y, float yChange) {

        for(Entity entity : entities) {
            entity.setPosition(x, y);
        }

        Timeline timeline = Timeline.createParallel();

        for (int i = entities.size() - 1; i >= 0; i--) {
            Entity entity = entities.get(i);
            timeline.push(entity.moveTo(x, y, 1));
            x += xChange;
            y += yChange;
        }

        return timeline;
    }

    /**
     * Placeholder
     */
    public void renderPlaceholder(SpriteBatch batch, float x, float y, float width, float height) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.rect(x, y, width, height);
        sr.end();
    }

    public TweenManager getTweenManager() {
        return tweenManager;
    }
}