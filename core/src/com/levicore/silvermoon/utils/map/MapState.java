package com.levicore.silvermoon.utils.map;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.GSM;
import com.levicore.silvermoon.Silvermoon;
import com.levicore.silvermoon.State;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.MapEntity;
import com.levicore.silvermoon.entities.ui.DialogBox;
import com.levicore.silvermoon.utils.tween.SpriteBatchAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 12/30/2014.
 */
public class MapState extends State {

    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;

    protected int[] groundLayers;
    protected int[] collisionLayers;
    protected int[] foregroundLayers;

    protected int tilePixelHeight;
    protected int tilePixelWidth;
    protected int mapPixelWidth;
    protected int mapPixelHeight;
    protected int mapHeight;
    protected int mapWidth;

    protected List<Rectangle> bounds;
    protected List<MapEntity> mapEntities;

    //TODO : put mapstate ui all inside a pack
    protected DialogBox dialogBox;
    protected Entity arrow_buttons;
    protected Entity buttonA;
    protected Entity buttonB;

    protected Rectangle up;
    protected Rectangle down;
    protected Rectangle left;
    protected Rectangle right;

    protected float mapAlpha;

    protected boolean mainControlsEnabled = false;

    protected MapState(GSM gsm, String mapName, int[] groundLayers, int[] collisionLayers, int[] foregroundLayers) {
        super(gsm);

        this.groundLayers = groundLayers;
        this.collisionLayers = collisionLayers;
        this.foregroundLayers = foregroundLayers;

        map = new TmxMapLoader().load("data/maps/"+mapName+".tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        MapProperties properties = map.getProperties();

        mapWidth = properties.get("width", Integer.class);
        mapHeight = properties.get("height", Integer.class);
        tilePixelWidth = properties.get("tilewidth", Integer.class);
        tilePixelHeight = properties.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        bounds = new ArrayList<>();

        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                for(int k = 0; k < collisionLayers.length; k++) {
                    TiledMapTileLayer cur = (TiledMapTileLayer) map.getLayers().get(collisionLayers[k]);

                    if (cur.getCell(i, j) != null) {
                        bounds.add(new Rectangle(i * tilePixelWidth, j * tilePixelHeight, tilePixelWidth, tilePixelHeight));
                    }
                }
            }
        }

        Timeline.createSequence()
                .push(Tween.to(renderer.getBatch(), SpriteBatchAccessor.ALPHA, 0).target(0).start(tweenManager))
                .push(Tween.to(renderer.getBatch(), SpriteBatchAccessor.ALPHA, 1).target(1).start(tweenManager));
    }

    @Override
    protected void initialize() {
        mapEntities = new ArrayList<>();
        mapAlpha = 1;

        arrow_buttons = new Entity("Arrow_Buttons.png");
        arrow_buttons.setPosition(-Silvermoon.SCREEN_WIDTH / 2 + 50, -Silvermoon.SCREEN_HEIGHT / 2 + 50);
        buttonA = new Entity("data/buttonA.png");
        buttonA.setPosition(Silvermoon.SCREEN_WIDTH / 2 - 200, -Silvermoon.SCREEN_HEIGHT / 2 + 50);
        buttonB = new Entity("data/buttonB.png");
        buttonB.setPosition(Silvermoon.SCREEN_WIDTH / 2 - 100, -Silvermoon.SCREEN_HEIGHT / 2 + 135);

        up = new Rectangle(arrow_buttons.getX() + 50, arrow_buttons.getY() + 110, 60, 50);
        down = new Rectangle(arrow_buttons.getX() + 50, arrow_buttons.getY() + 0, 60, 50);
        left = new Rectangle(arrow_buttons.getX() + 0, arrow_buttons.getY() + 50, 50, 60);
        right = new Rectangle(arrow_buttons.getX() + 110, arrow_buttons.getY() + 50, 50, 60);
        dialogBox = new DialogBox(Assets.getTexture("splash"));

        super.initialize();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        /** Scroll through mapEntities entities */
        for(MapEntity mapEntity : mapEntities) {

            mapEntity.update(delta);

            /** Collision detection of entities */
            for (MapEntity comparedEntity : mapEntities) {
                if (mapEntity != comparedEntity) {
                    if (mapEntity.getBoundingRectangle().overlaps(comparedEntity.getBoundingRectangle())) {
                        mapEntity.moveBack();
                        comparedEntity.moveBack();
                    }
                }
            }

            /** Prevent entities from going out of bounds */
            if (mapEntity.getX() > mapPixelWidth - mapEntity.getWidth() || mapEntity.getX() < 0 || mapEntity.getY() > mapPixelHeight - mapEntity.getHeight() || mapEntity.getY() < 0) {
                mapEntity.moveBack();
            }

            /** Tile detection */
            for(Rectangle rectangle : bounds) {
                if(mapEntity.getBoundingRectangle().overlaps(rectangle)) {
                    mapEntity.moveBack();
                }
            }
        }
        
        renderer.setView(camera);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);

        renderer.getBatch().setProjectionMatrix(camera.combined);
        renderer.render(groundLayers);
        renderer.render(collisionLayers);

        renderer.getBatch().begin();

        drawMapEntities((SpriteBatch) renderer.getBatch());

        renderer.getBatch().end();

        renderer.render(foregroundLayers);

        // Uses ui matrix
        renderer.getBatch().setProjectionMatrix(uiCamera.combined);
        renderer.getBatch().begin();
        drawUI((SpriteBatch) renderer.getBatch());

        renderer.getBatch().setProjectionMatrix(camera.combined);
        dialogBox.draw(renderer.getBatch());

        renderer.getBatch().end();
    }

    @Override
    public void resize() {
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    /**
     * Control methods
     */
    public void focus(MapEntity sprite) {
        camera.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
        camera.update();
    }

    public void focusWithGrid(MapEntity sprite) {
        camera.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);

        if(camera.position.x < camera.viewportWidth / 2) {
            camera.position.x = camera.viewportWidth / 2;
        } else if(camera.position.x > mapPixelWidth - camera.viewportWidth / 2) {
            camera.position.x = mapPixelWidth - camera.viewportWidth / 2;
        }

        if(camera.position.y < camera.viewportHeight / 2) {
            camera.position.y = camera.viewportHeight / 2;
        } else if(camera.position.y > mapPixelHeight - camera.viewportHeight / 2) {
            camera.position.y = mapPixelHeight - camera.viewportHeight / 2;
        }

        camera.update();
    }

    /**
     * Ui
     */
    public void drawUI(SpriteBatch batch) {
        arrow_buttons.draw(batch);
        buttonA.draw(batch);
        buttonB.draw(batch);
    }

    /**
     * Macros
     */
    public void drawMapEntities(SpriteBatch batch) {
        for(MapEntity mapEntity : mapEntities) {
            if(mapEntity != null) mapEntity.draw(batch);
        }
    }

    @Override
    public Timeline fadeInAll(float duration) {
        Timeline timeline = super.fadeInAll(duration);

        if(renderer != null) {
            timeline.push(Tween.to(renderer.getBatch(), SpriteBatchAccessor.ALPHA, duration).target(1));
        }

        timeline.push(arrow_buttons.fadeIn(duration))
                .push(buttonA.fadeIn(duration))
                .push(buttonB.fadeIn(duration));

        for(MapEntity mapEntity : mapEntities) {
            timeline.push(mapEntity.fadeIn(duration));
        }

        return timeline;
    }

    @Override
    public Timeline fadeOutAll(float duration) {
        Timeline timeline = super.fadeOutAll(duration);

        if(renderer != null) {
            timeline.push(Tween.to(renderer.getBatch(), SpriteBatchAccessor.ALPHA, duration).target(0));
        }

        timeline.push(arrow_buttons.fadeOut(duration))
                .push(buttonA.fadeOut(duration))
                .push(buttonB.fadeOut(duration));

        for(MapEntity mapEntity : mapEntities) {
            timeline.push(mapEntity.fadeOut(duration));
        }

        return timeline;
    }
}
