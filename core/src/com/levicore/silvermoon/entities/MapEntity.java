package com.levicore.silvermoon.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.levicore.silvermoon.utils.AnimationUtils;
import com.levicore.silvermoon.utils.menu.OptionCallback;

/**
 * Created by Leonard on 12/30/2014.
 */
public class MapEntity extends Entity {

    private float speed;
    private Vector2 velocity;

    private float forwardX;
    private float forwardY;

    private Rectangle collisionRectangle;
    private float oldX;
    private float oldY;

    private OptionCallback callback;

    private Animation idle;
    private Animation up;
    private Animation down;
    private Animation left;
    private Animation right;

    private DIRECTION direction = DIRECTION.DOWN;

    public MapEntity(Texture texture, float speed) {
        super(texture);

        collisionRectangle = new Rectangle();
        collisionRectangle.width = getBoundingRectangle().width;
        collisionRectangle.height = getBoundingRectangle().height / 2;

        velocity = new Vector2();
        this.speed = speed;
    }

    public MapEntity(Texture texture, Rectangle collisionRectangle, float speed) {
        super(texture);
        this.collisionRectangle = collisionRectangle;

        velocity = new Vector2();
        this.speed = speed;
    }

    public MapEntity(Animation animation, float speed) {
        super(animation);

        collisionRectangle = new Rectangle();
        collisionRectangle.width = getBoundingRectangle().width;
        collisionRectangle.height = getBoundingRectangle().height / 2;

        velocity = new Vector2();
        this.speed = speed;
    }

    public MapEntity(Animation animation, Rectangle collisionRectangle, float speed) {
        super(animation);
        this.collisionRectangle = collisionRectangle;

        velocity = new Vector2();
        this.speed = speed;
    }

    /**
     * Constructor for preset Kaduki/3x3 spritesheets
     */
    public MapEntity(String charsetName, int regionWidth, int regionHeight, float speed) {
        super(AnimationUtils.createAnimationFromRegions(split(new Texture("data/characters/"+charsetName+".png"), 32, 32), 0, 0.15f, Animation.PlayMode.LOOP_PINGPONG));
        initKadukiDefaultAnimations(charsetName, regionWidth, regionHeight);

        collisionRectangle = new Rectangle();
        collisionRectangle.width = getBoundingRectangle().width;
        collisionRectangle.height = getBoundingRectangle().height / 2;

        velocity = new Vector2();
        this.speed = speed;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(animation != null) {
            if (velocity.x > 0) {
                setAnimation_nonReset_void(right);
                direction = DIRECTION.RIGHT;

            } else if (velocity.x < 0) {
                setAnimation_nonReset_void(left);
                direction = DIRECTION.LEFT;

            } else if (velocity.y > 0) {
                setAnimation_nonReset_void(up);
                direction = DIRECTION.UP;

            } else if (velocity.y < 0) {
                setAnimation_nonReset_void(down);
                direction = DIRECTION.DOWN;

            } else if (velocity.x == 0 && velocity.y == 0) {
                // TODO add option for idle animations
                Animation newAnimation = new Animation(0.15f, animation.getKeyFrame(0));
                setAnimation_nonReset_void(newAnimation);
            }
        }

        oldX = getX();
        oldY = getY();

        setX(getX() + (velocity.x) * delta);
        setY(getY() + (velocity.y) * delta);
    }

    /**
     * Control methods
     */
    public void moveBack() {
        setPosition(oldX, oldY);
    }

    public void initKadukiDefaultAnimations(String charsetName, int regionWidth, int regionHeight) {
        TextureRegion[][] char_1 = split(new Texture("data/characters/"+charsetName+".png"), regionWidth, regionHeight);

        down = AnimationUtils.createAnimationFromRegions(char_1, 0, 0.15f, Animation.PlayMode.LOOP_PINGPONG);
        left = AnimationUtils.createAnimationFromRegions(char_1, 1, 0.15f, Animation.PlayMode.LOOP_PINGPONG);
        right = AnimationUtils.createAnimationFromRegions(char_1, 2, 0.15f, Animation.PlayMode.LOOP_PINGPONG);
        up = AnimationUtils.createAnimationFromRegions(char_1, 3, 0.15f, Animation.PlayMode.LOOP_PINGPONG);
    }

    /**
     * Getters and setters
     */
    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(float x, float y) {
        velocity.x = x * speed;
        velocity.y = y * speed;
    }

    public Animation getCurrentAnimation() {
        return animation;
    }

    public Animation getIdleAnimation() {
        return idle;
    }


    public float getForwardX() {
        switch (direction) {
            case UP:
                forwardX = getX() + (getWidth() / 2);
                break;
            case DOWN:
                forwardX = getX() + (getWidth() / 2);
                break;
            case LEFT:
                forwardX = getX() - 5;
                break;
            case RIGHT:
                forwardX = getX() + (getWidth()) + 5;
                break;
        }

        return forwardX;
    }

    public float getForwardY() {
        switch (direction) {
            case UP:
                forwardY = getY() + getWidth() + 5;
                break;
            case DOWN:
                forwardY = getY() - 5;
                break;
            case LEFT:
                forwardY = getY() + (getHeight() / 2);
                break;
            case RIGHT:
                forwardY = getY() + (getHeight() / 2);
                break;
        }

        return forwardY;
    }

    public static enum DIRECTION {
        UP, DOWN, LEFT, RIGHT
    }


    /**
     * Getters and setters
     */
    public void setCallback(OptionCallback callback) {
        this.callback = callback;
    }

    public OptionCallback getCallback() {
        return callback;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setForwardX(float forwardX) {
        this.forwardX = forwardX;
    }

    public void setForwardY(float forwardY) {
        this.forwardY = forwardY;
    }

    public void setCollisionRectangle(Rectangle collisionRectangle) {
        this.collisionRectangle = collisionRectangle;
    }

    public float getOldX() {
        return oldX;
    }

    public void setOldX(float oldX) {
        this.oldX = oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public void setOldY(float oldY) {
        this.oldY = oldY;
    }

    public Animation getIdle() {
        return idle;
    }

    public void setIdle(Animation idle) {
        this.idle = idle;
    }

    public Animation getUp() {
        return up;
    }

    public void setUp(Animation up) {
        this.up = up;
    }

    public Animation getDown() {
        return down;
    }

    public void setDown(Animation down) {
        this.down = down;
    }

    public Animation getLeft() {
        return left;
    }

    public void setLeft(Animation left) {
        this.left = left;
    }

    public Animation getRight() {
        return right;
    }

    public void setRight(Animation right) {
        this.right = right;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }
}
