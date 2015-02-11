package com.levicore.silvermoon.states.chapter1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.GSM;
import com.levicore.silvermoon.entities.MapEntity;
import com.levicore.silvermoon.entities.battle.KadukiBattler;
import com.levicore.silvermoon.states.BattleState;
import com.levicore.silvermoon.utils.map.MapState;
import com.levicore.silvermoon.utils.menu.OptionCallback;

/**
* Created by Leonard on 12/30/2014.
*/
public class BasicRPGMap extends MapState {

    MapEntity vince;

    /** Test */
    ShapeRenderer sr = new ShapeRenderer();

    Rectangle test = new Rectangle(0, 0, 50, 50);

    public BasicRPGMap(final GSM gsm) {
        super(gsm, "Introduction", new int[]{ 0, 1 }, new int[]{ 2 }, new int[] { 3, 4 });


        // Test =========================================================
        KadukiBattler s = new KadukiBattler("$Actor63", 23, 23, 32, 32);
        KadukiBattler k = new KadukiBattler("$Actor31", 23, 23, 32, 32);
        // ==============================================================


        vince = new MapEntity(s.IDLE, 100);
        vince.setPosition(0, 0);
        vince.initKadukiDefaultAnimations("$Actor63", 32, 32);

        final MapEntity x = new MapEntity(k.IDLE, 100);

        final BasicRPGMap basicRPGMap = this;

        x.setPosition(50, 100);
        x.setCallback(new OptionCallback() {
            @Override
            public void execute() {
                fadeOutAll(1).start(tweenManager);
                gsm.getGame().getInputMultiplexer().removeProcessor(basicRPGMap);
                gsm.push(new BattleState(gsm, basicRPGMap, Assets.getMusic("battle"), BattleState.PHASE.TURN_START));
            }
        });

        mapEntities.add(vince);
        mapEntities.add(x);

        mainControlsEnabled = true;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        focusWithGrid(vince);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);
    }

    @Override
    public void resize() {
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //UI controls
        if(mainControlsEnabled) {
            if(up.contains(uiCoordinates.x, uiCoordinates.y)) {
                vince.setVelocity(0, 1);
            } else if(down.contains(uiCoordinates.x, uiCoordinates.y)) {
                vince.setVelocity(0, -1);
            } else if(left.contains(uiCoordinates.x, uiCoordinates.y)) {
                vince.setVelocity(-1, 0);
            } else if(right.contains(uiCoordinates.x, uiCoordinates.y)) {
                vince.setVelocity(1, 0);
            }
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(mainControlsEnabled) {
            vince.setVelocity(0, 0);

            if(buttonA.contains(uiCoordinates.x, uiCoordinates.y)) {
                /** Loop for entity Interaction */
                for(MapEntity mapEntity : mapEntities) {
                    if(mapEntity.contains(vince.getForwardX(), vince.getForwardY())) {
                         mapEntity.getCallback().execute();
                    }
                }

            } else if(buttonB.contains(uiCoordinates.x, uiCoordinates.y)) {
                System.out.print("B");
            }
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(mainControlsEnabled) {
            if(up.contains(uiCoordinates.x, uiCoordinates.y)) {
                vince.setVelocity(0, 1);
            } else if(down.contains(uiCoordinates.x, uiCoordinates.y)) {
                vince.setVelocity(0, -1);
            } else if(left.contains(uiCoordinates.x, uiCoordinates.y)) {
                vince.setVelocity(-1, 0);
            } else if(right.contains(uiCoordinates.x, uiCoordinates.y)) {
                vince.setVelocity(1, 0);
            }
        }
        return super.touchDragged(screenX, screenY, pointer);
    }
}