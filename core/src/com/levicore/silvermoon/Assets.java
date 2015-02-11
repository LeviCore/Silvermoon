package com.levicore.silvermoon;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leonard on 1/20/2015.
 */
public class Assets {

    public static TextureAtlas SYSTEM_ATLAS = new TextureAtlas("data/System.pack");
    public static TextureAtlas TITLE_ATLAS = new TextureAtlas("data/title/Title.pack");
    public static TextureAtlas FACES_ATLAS = new TextureAtlas("data/Faces.pack");

    public static List<Animation> TITLE_OPTION_ANIMATIONS = new ArrayList<>();


    private static AssetManager manager = new AssetManager();

    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Sound> soundEffects = new HashMap<>();
    private static Map<String, Music> music = new HashMap<>();

    /** Animations */
    public static TextureAtlas SLASH_ANIMATION = new TextureAtlas("data/animations/Slash.pack");

    /**
     * Loads required assets for the game
     */
    public static void queueLoading() {
        // Load textures
        manager.load("data/badlogic.jpg", Texture.class);

        // Load sound effects
        manager.load("data/sound/Slash1.ogg", Sound.class);
        manager.load("data/sound/Cursor1.ogg", Sound.class);

        // Load musics
        manager.load("data/music/Battle.ogg", Music.class);
    }

    /**
     * Initialize resources must be called after assets are loaded
     */
    public static void initResources() {
        // Textures
        textures.put("splash", manager.get("data/badlogic.jpg", Texture.class));

        // Sounds Effects
        soundEffects.put("slash_1", manager.get("data/sound/Slash1.ogg", Sound.class));
        soundEffects.put("cursor_1", manager.get("data/sound/Cursor1.ogg", Sound.class));

        // Music
        music.put("battle", manager.get("data/music/Battle.ogg", Music.class));

        TITLE_OPTION_ANIMATIONS.add(new Animation(0.05f,
                TITLE_ATLAS.findRegion("Title-New01"),
                TITLE_ATLAS.findRegion("Title-New02"),
                TITLE_ATLAS.findRegion("Title-New03"),
                TITLE_ATLAS.findRegion("Title-New04"),
                TITLE_ATLAS.findRegion("Title-New05"),
                TITLE_ATLAS.findRegion("Title-NewGlow")));

        TITLE_OPTION_ANIMATIONS.add(new Animation(0.05f,
                TITLE_ATLAS.findRegion("Title-Continue01"),
                TITLE_ATLAS.findRegion("Title-Continue02"),
                TITLE_ATLAS.findRegion("Title-Continue03"),
                TITLE_ATLAS.findRegion("Title-Continue04"),
                TITLE_ATLAS.findRegion("Title-Continue05"),
                TITLE_ATLAS.findRegion("Title-ContinueGlow")));

        TITLE_OPTION_ANIMATIONS.add(new Animation(0.05f,
                TITLE_ATLAS.findRegion("Title-Exit01"),
                TITLE_ATLAS.findRegion("Title-Exit02"),
                TITLE_ATLAS.findRegion("Title-Exit03"),
                TITLE_ATLAS.findRegion("Title-Exit04"),
                TITLE_ATLAS.findRegion("Title-Exit05"),
                TITLE_ATLAS.findRegion("Title-ExitGlow")));

        for(Animation animation : TITLE_OPTION_ANIMATIONS) {
            animation.setPlayMode(Animation.PlayMode.REVERSED);
        }
    }

    public static boolean update() {
        return manager.update();
    }

    public static void dispose() {
        manager.dispose();
    }

    public static Texture getTexture(String name) {
        return textures.get(name);
    }

    public static Sound getSound(String name) {
        return soundEffects.get(name);
    }

    public static Music getMusic(String name) {
        return music.get(name);
    }

}
