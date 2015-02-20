package com.levicore.silvermoon;

import com.levicore.silvermoon.presets.characters.MainCharacters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 1/17/2015.
 */
public class Player {

    /** List of all current party members **/
    private List<Character> party;

    /** Currencies **/
    private int gold;

    public Player() {
        party = new ArrayList<>();

        party.add(MainCharacters.VINCE());
        party.add(MainCharacters.RHEAJOY());

        gold = 1000;
    }

    public List<Character> getParty() {
        return party;
    }

    public void setParty(List<Character> party) {
        this.party = party;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

}
