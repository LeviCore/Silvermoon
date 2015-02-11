package com.levicore.silvermoon.entities.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.levicore.silvermoon.battle.Skill;

/**
 * Created by Leonard on 1/10/2015.
 */
public class SkillOption extends Option {
    
    private Skill skill;

    public SkillOption(String texture, Skill skill) {
        super(texture, skill.getIcon());
        this.skill = skill;
    }

    public SkillOption(Texture texture, Skill skill) {
        super(texture, skill.getIcon());
        this.skill = skill;
    }

    public SkillOption(TextureRegion region, Skill skill) {
        super(region, skill == null ? null : skill.getIcon());
        this.skill = skill;
    }

    public SkillOption(Animation animation, Skill skill) {
        super(animation, skill.getIcon());
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
        setIcon(skill == null ? null : skill.getIcon());
    }

}