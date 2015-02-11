package com.levicore.silvermoon.utils.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 1/24/2015.
 */
public class Selection {

    protected List<OptionCallback> optionCallbacks;
    protected int current = 0;

    protected Selection() {
        optionCallbacks = new ArrayList<>();
    }
    
    public void next() {
        if(current == optionCallbacks.size() - 1) current = 0; else current++;
    }

    public void previous() {
        if(current == 0) current = optionCallbacks.size() - 1; else current--;
    }

    public void executeSelected() {
        optionCallbacks.get(current).execute();
    }

    public void addOption(OptionCallback optionCallback) {
        optionCallbacks.add(optionCallback);
    }

}
