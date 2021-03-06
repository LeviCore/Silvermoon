package com.levicore.silvermoon.states.chapter1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.GSM;
import com.levicore.silvermoon.entities.MapEntity;
import com.levicore.silvermoon.presets.parties.Enemies_1;
import com.levicore.silvermoon.states.BattleState;
import com.levicore.silvermoon.utils.map.MapState;
import com.levicore.silvermoon.utils.menu.OptionCallback;

/**
* Created by Leonard on 12/30/2014.
*/
public class TestMap extends MapState {

    /** Test */
    public TestMap(final GSM gsm) {
        super(gsm, "Introduction", gsm.getGame().player.getParty().get(0).getMapEntity(), new int[]{ 0, 1 }, new int[]{ 2 }, new int[] { 3, 4 });

        final MapEntity x = new MapEntity("$Actor63", 32, 32, 300);
        final TestMap testMap = this;

        x.setPosition(50, 300);
        x.setCallback(new OptionCallback() {
            @Override
            public void execute() {
                fadeOutAll(1).start(tweenManager);
                gsm.getGame().getInputMultiplexer().removeProcessor(testMap);
                gsm.push(new BattleState(gsm, testMap, Assets.getMusic("battle"), BattleState.PHASE.TURN_START, gsm.getGame().player.getParty(), Enemies_1.createDummies()));
            }
        });

        mapEntities.add(x);
    }

    @Override
    public void update(float delta) {
        focusWithGrid(controlledCharacter);
        super.update(delta);
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
        return super.touchDown(screenX, screenY, pointer, button);
    }

}