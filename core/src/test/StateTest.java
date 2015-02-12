package test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.levicore.silvermoon.*;
import com.levicore.silvermoon.Character;
import com.levicore.silvermoon.core.Item;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.ui.SimpleItemInfo;
import com.levicore.silvermoon.entities.ui.VictoryWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/9/2015.
 */
public class StateTest extends State {

    VictoryWindow victoryWindow;

    public StateTest(GSM gsm) {
        super(gsm);

        List<Character> characters = new ArrayList<>();

        characters.add(new Character(null, null, new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Inchi"));
        characters.add(new Character(null, null, new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Bong"));
        characters.add(new Character(null, null, new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Jason"));
        characters.add(new Character(null, null, new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Mark"));

        List<Item> itemsGained = new ArrayList<>();
        itemsGained.add(new Item(new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "axe", ""));

        victoryWindow = new VictoryWindow(this, gsm.getBitmapFont(), characters, itemsGained);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        victoryWindow.updateAndRender(delta, batch);
        batch.end();

        super.render(batch, delta);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        victoryWindow.next();
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public void resize() {
    }

}
