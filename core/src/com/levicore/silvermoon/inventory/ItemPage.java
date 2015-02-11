package com.levicore.silvermoon.inventory;

import com.levicore.silvermoon.core.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 1/17/2015.
 */
public class ItemPage {

    private List<Item> items = new ArrayList<>();
    private int maxItems;

    public ItemPage(int maxItems) {
        this.maxItems = maxItems;
    }

    public void addItem(Item item) {
        if(items.size() < maxItems) {
            items.add(item);
        }
    }

}
