package com.levicore.silvermoon.core;

import com.levicore.silvermoon.entities.Entity;

/**
 * Created by user on 2/12/2015.
 */
public class Item {

    private String name;
    private String description;
    private Entity icon;

    private int cost;

    public Item(Entity icon, String name, String description) {
        this.icon = icon;
        this.name = name;
        this.description = description;

        cost = 0;
    }

    public Item(Entity icon, String name, String description, int cost) {
        this.icon = icon;
        this.name = name;
        this.description = description;

        this.cost = cost;
    }

    /**
     * Getters and setters
     */
    public Entity getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
