package com.levicore.silvermoon;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Leonard on 7/5/2014.
 */
public class SoundManager {
//    public static final Sound COIN = Gdx.audio.newSound(Gdx.files.internal("data/audio/sound effects/Coin.wav"));
//    public static final Music THEME_1 = Gdx.audio.newMusic(Gdx.files.internal("data/audio/music/Theme_1.mp3"));

    private static Music currentlyPlaying;

    public static Tween playBackgroundMusic(final Music music, final float volume, final Boolean looping) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                music.setVolume(volume);
                music.setLooping(looping);
                music.play();

                currentlyPlaying = music;
            }
        });

    }

    public static Tween stopBackgroundMusic() {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                if(currentlyPlaying != null) currentlyPlaying.stop();
            }
        });
    }

    public static Tween playSound(final Sound sound) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                sound.play();
            }
        });
    }

}