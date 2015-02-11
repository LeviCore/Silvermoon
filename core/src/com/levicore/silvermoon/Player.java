package com.levicore.silvermoon;

import com.levicore.silvermoon.inventory.ItemPage;
import com.levicore.silvermoon.inventory.ItemStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 1/17/2015.
 */
public class Player {

    // TODO Inventory

    private Silvermoon game;

    /** List of all current party members **/
    private List<Character> party = new ArrayList<>();

    /** Item storage **/
    private ItemStorage itemStorage = new ItemStorage();

    public Player(Silvermoon game) {
        this.game = game;
        itemStorage.addItemPage(new ItemPage(9));
    }

    public void engageBattle() {

    }

    public static Player createPlayer(Silvermoon game) {
        return new Player(game);
    }

}
