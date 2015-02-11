package com.levicore.silvermoon.inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 1/17/2015.
 */
public class ItemStorage {

    private List<ItemPage> itemPageList = new ArrayList<>();

    public ItemPage getItemPage(int pageNumber) {
        return itemPageList.get(pageNumber);
    }

    public void addItemPage(ItemPage itemPage) {
        itemPageList.add(itemPage);
    }

}
