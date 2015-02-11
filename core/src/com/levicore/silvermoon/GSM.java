package com.levicore.silvermoon;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Leonard on 12/9/2014.
 */
public class GSM {

    private Silvermoon game;
    private Stack<State> states;

    public GSM(Silvermoon game) {
        this.game = game;
        states = new Stack<>();
    }

    public void update(float delta) {
        for(State state : states) {
            state.update(delta);
        }
    }

    public void render(SpriteBatch batch, float delta) {
        for (int i = 0; i < states.size(); i++) {
            State state = states.get(i);
            state.render(batch, delta);
        }
    }

    public void resizeStates() {
        for(State state : states) {
            state.resize();
        }
    }

    /**
     *  State control methods
     */
    public void push(State state) {
        states.push(state);
        game.getInputMultiplexer().addProcessor(state);
    }

    public void pop() {
        game.getInputMultiplexer().removeProcessor(states.peek());
        states.peek().dispose();
        states.pop();
    }

    public void setPeek(State state) {
        pop();
        states.push(state);
        game.getInputMultiplexer().addProcessor(state);
    }

    /**
     *  Getters and setters
     */
    public Stack<State> getStates() {
        return states;
    }

    public BitmapFont getBitmapFont() {
        return game.getBitmapFont();
    }

    public Silvermoon getGame() {
        return game;
    }
}
