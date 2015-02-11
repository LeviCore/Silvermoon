package com.levicore.silvermoon.entities.ui;

import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.entities.Entity;

/**
 * Created by Leonard on 1/16/2015.
 */
public class SkillInfoBar extends InfoBar {

    public SkillInfoBar() {
        super(Assets.SYSTEM_ATLAS.findRegion("Skill_Info_Background"), new Entity("data/images/icons/skill_book.png"));
    }

}
