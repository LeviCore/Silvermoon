package com.levicore.silvermoon.entities.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.levicore.silvermoon.entities.Entity;

/**
 * Created by Leonard on 1/9/2015.
 */
public class Option extends Entity {

    protected Entity icon;

    public Option(String texture, Entity icon) {
        super(texture);
        this.icon = icon;
    }

    public Option(Texture texture, Entity icon) {
        super(texture);
        this.icon = icon;
    }

    public Option(TextureRegion region, Entity icon) {
        super(region);
        this.icon = icon;
    }

    public Option(Animation animation, Entity icon) {
        super(animation);
        this.icon = icon;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(icon != null) {
            icon.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
            icon.setPosition((getX() + getWidth() / 2) - (icon.getWidth() / 2), (getY() + getHeight() / 2) - (icon.getHeight() / 2));
            icon.update(delta);
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if(icon != null) {
            icon.draw(batch);
        }
    }

    public void setIcon(Entity icon) {
        this.icon = icon;
    }

}
