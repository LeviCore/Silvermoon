package com.levicore.silvermoon.core;

import com.levicore.silvermoon.entities.Entity;

/**
 * Created by user on 2/12/2015.
 */
public class Item {

    private String description;
    private Entity icon;

    private int cost;

    public Item(String description, Entity icon) {
        this.description = description;
        this.icon = icon;

        cost = 0;
    }

    public Item(Entity icon, String description, int cost) {
        this.icon = icon;
        this.description = description;
        this.cost = cost;
    }

    public Entity getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

}
