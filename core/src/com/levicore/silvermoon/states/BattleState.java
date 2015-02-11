package com.levicore.silvermoon.states;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.levicore.silvermoon.*;
import com.levicore.silvermoon.battle.Behavior;
import com.levicore.silvermoon.battle.Buff;
import com.levicore.silvermoon.battle.Skill;
import com.levicore.silvermoon.battle.behaviors.Normal;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.entities.battle.KadukiBattler;
import com.levicore.silvermoon.entities.ui.*;
import com.levicore.silvermoon.utils.comparators.StatComparator;
import com.levicore.silvermoon.utils.map.MapState;
import com.levicore.silvermoon.utils.tween.EntityAccessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Leonard on 1/15/2015.
 */
public class BattleState extends State {

    // TODO VICTORY SCREEN : note incase you forgot. You decided it to be placed in a inner State within battle state :D

    // TODO Menu
    // TODO Items

    // TODO Icon as weapon : create a shortcut method for it(void or timeline)
    // TODO add a skill then retest menu

    // TODO Cancel button in TargetSelection phase
    // TODO Victory Screen

    // TODO ASAP REDIRECT TO MAP AFTER FINISH

    // TODO FIX EVERYTHING!!!!!!!!!!!!!!!!!!!!!!!!!!

    // TODO Connect VictoryWindow

    private MapState mapState;

    protected List<BattleEntity> battlers;
    protected List<BattleEntity> partyA;
    protected List<BattleEntity> partyB;

    protected List<BattleEntity> availableTargets;
    protected List<BattleEntity> targetsSelected;

    protected CircularMenu circularMenu;
    protected DialogBox db;
    protected SkillInfoBar skillInfoBar;
    protected Entity background;

    protected PHASE phase;
    protected Skill skillSelected;

    private Entity selector_1;
    private Entity selector_2;

    private int active = 0;
    private float finalValue;
    private boolean isSkillMenuOpen = false;

    public BattleState(GSM gsm, MapState mapState, Music music, PHASE phase) {
        super(gsm);
        this.mapState = mapState;
        this.phase = phase;

        // TODO : move in a Timline method if necessary to be overridden for custom cutscenes.
        Timeline.createSequence()
                .push(SoundManager.playBackgroundMusic(music, 1, true))
                .start(tweenManager);

        execute();
    }

    public void initTest() {

        KadukiBattler vince2 = new KadukiBattler("$Actor63", -240, -50, 32, 32);
        vince2.name = "Vincent";

        vince2.maxHP = 1000;
        vince2.curHP = 1000;

        vince2.maxMP = 100;
        vince2.curMP = 100;

        vince2.maxTP = 100;
        vince2.curTP = 100;

        vince2.flipBattler(true, false).start(tweenManager);
        vince2.speed = 99;

        partyA.add(vince2);

        KadukiBattler rhea = new KadukiBattler("$Actor33", 240 - 32, 0, 32, 32);
        rhea.name = "Rheajoy The Beautiful";

        rhea.behaviors.add(new Normal(this, rhea));

        rhea.maxHP = 10;
        rhea.curHP = 10;

        rhea.maxMP = 100;
        rhea.curMP = 100;

        rhea.maxTP = 100;
        rhea.curTP = 100;

        rhea.speed = 1;

        partyB.add(rhea);

        KadukiBattler rhea2 = new KadukiBattler("$Actor132a", 240 - 32, -50, 32, 32);
        rhea2.name = "Rheajoy The Pretty";

        rhea2.behaviors.add(new Normal(this, rhea2));

        rhea2.maxHP = 10;
        rhea2.curHP = 10;

        rhea2.maxMP = 100;
        rhea2.curMP = 100;

        rhea2.maxTP = 100;
        rhea2.curTP = 100;

        rhea2.speed = 20;

        partyB.add(rhea2);

    }

    @Override
    public void update(float delta) {
        super.update(delta);

        for(BattleEntity battler : battlers) {
            battler.update(delta);
        }

        db.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if(phase == PHASE.TARGET_SELECTION) {
            selector_2.draw(batch);
        }

        for(BattleEntity battler : battlers) {
            if(battler != null) battler.draw(batch);
        }

        renderTemporaryAnimations(batch);

        circularMenu.updateAndDraw(batch, delta);

        switch (phase) {
            case TARGET_SELECTION:
                selector_1.draw(batch);
                break;
            case ACTION_EXECUTION:
                skillInfoBar.setText(skillSelected.getName());
                skillInfoBar.setIcon(skillSelected.getIcon());
                skillInfoBar.updateChildrenPosition();

                skillInfoBar.update(delta);
                skillInfoBar.setPosition(skillInfoBar.getWidth() / -2, 200);
                skillInfoBar.draw(batch);
                break;
        }


        drawBattlerNames(batch);
        drawAndUpdateTemporaryEntities(delta, batch);

        db.draw(batch);

        batch.end();
    }

    @Override
    public void resize() {
    }

    /**
     * Initialize
     */
    @Override
    public void initialize() {
        availableTargets = new ArrayList<>();
        targetsSelected = new ArrayList<>();
        partyB = new ArrayList<>();
        partyA = new ArrayList<>();
        battlers = new ArrayList<>();
        circularMenu = new CircularMenu();
        db = new DialogBox(new Texture("data/images/dialog-box.9.png"));
        skillInfoBar = new SkillInfoBar();
        background = new Entity("data/battlebacks/back_1.png");
        selector_1 = new Entity(Assets.SYSTEM_ATLAS.findRegion("Selector", 1));
        selector_2 = new Entity(Assets.SYSTEM_ATLAS.findRegion("Selector", 2));

        initTest();

        background.setSize(Silvermoon.SCREEN_WIDTH * 1.3f, Silvermoon.SCREEN_HEIGHT * 1.3f);
        background.setPosition(-Silvermoon.SCREEN_WIDTH * 1.3f / 2, -Silvermoon.SCREEN_HEIGHT * 1.3f / 2);
        entities.add(background);

        selector_1.setSize(32 * 1.3f, selector_1.getHeight());
        selector_2.setSize(32 * 2, selector_2.getHeight());

        gsm.getBitmapFont().setScale(0.75f, 0.75f);

        battlers.addAll(partyA);
        battlers.addAll(partyB);

        Collections.sort(battlers, new StatComparator(StatComparator.Attribute.SPEED));

        super.initialize();
    }

    /**
     * Action Selection
     */
    public void handleActionSelectionTouchDown() {
    }

    // TODO indicate what skill is selected
    private void handleActionSelectionTouchUp() {
        if(circularMenu.basicActions[0].contains(worldCoordinates.x, worldCoordinates.y)) {
            /** Skill 0, default is Attack */
            if(skillSelected == getActiveBattler().skills.get(0)) {
                processSkill();
            } else {
                circularMenu.transitionOutExtendedOptions(0.25f).start(tweenManager);
                setSkillSelected(getActiveBattler().skills.get(0), circularMenu.basicActions[0]);
            }

        } else if(circularMenu.basicActions[2].contains(worldCoordinates.x, worldCoordinates.y)) {
            /** Skill 1, default is Defend */
            if(skillSelected == getActiveBattler().skills.get(1)) {
                processSkill();
            } else {
                circularMenu.transitionOutExtendedOptions(0.25f).start(tweenManager);
                setSkillSelected(getActiveBattler().skills.get(1), circularMenu.basicActions[2]);
            }
        }

        /** Skills Menu visibility toggle */
        if (circularMenu.basicActions[1].contains(worldCoordinates.x, worldCoordinates.y)) {
            transitionInSkillOptions();
            // TODO Tell player to select a skill
        }

        /** Skill */
        if(isSkillMenuOpen) {
            for (SkillOption skillOption : circularMenu.options) {
                if (skillOption.contains(worldCoordinates.x, worldCoordinates.y)) {
                    if(skillSelected == skillOption.getSkill()) {
                        processSkill();
                    } else {
                        setSkillSelected(skillOption.getSkill(), skillOption);
                    }
                }
            }
        }

    }

    private void handleActionSelectionTouchDragged() {
    }

    /**
     * Target Selection
     */
    public void handleTargetSelectionTouchDown() {
        for(BattleEntity battleEntity : availableTargets) {
            if(battleEntity.contains(worldCoordinates.x, worldCoordinates.y)) {
                selector_1.setPosition(battleEntity.getX() + (battleEntity.getWidth() / 2) - (selector_1.getWidth() / 2), battleEntity.getY() + battleEntity.getHeight());
                selector_2.setPosition(battleEntity.getX() + (battleEntity.getWidth() / 2) - (selector_2.getWidth() / 2), battleEntity.getY() - (selector_2.getHeight() / 2));
            }
        }
    }

    private void handleTargetSelectionTouchUp() {
        for(BattleEntity battleEntity : availableTargets) {
            if(battleEntity.contains(worldCoordinates.x, worldCoordinates.y)) {
                targetsSelected.add(battleEntity);

                phase = PHASE.ACTION_EXECUTION;
                execute();
                break;
            }
        }
    }

    private void handleTargetSelectionTouchDragged() {
        for(BattleEntity battleEntity : availableTargets) {
            if(battleEntity.contains(worldCoordinates.x, worldCoordinates.y)) {
                selector_1.setPosition(battleEntity.getX() + (battleEntity.getWidth() / 2) - (selector_1.getWidth() / 2), battleEntity.getY() + battleEntity.getHeight());
                selector_2.setPosition(battleEntity.getX() + (battleEntity.getWidth() / 2) - (selector_2.getWidth() / 2), battleEntity.getY() - (selector_2.getHeight() / 2));
            }
        }
    }

    /**
     * Battle Control
     */
    public void execute() {
        if(getWinner() == null) {
            switch (phase) {
                case TURN_START:
                    fadeInHealthBars(1)
                    .push(circularMenu.resetScale(1))
                    .start(tweenManager);

                    if (!getActiveBattler().behaviors.isEmpty()) {
                        executeAI();
                    } else {
                        phase = PHASE.ACTION_SELECTION;
                        execute();
                    }

                    break;
                case ACTION_SELECTION:
                    executeActionSelection(getActiveBattler());
                    break;
                case TARGET_SELECTION:
                    finalValue = 0;

                    moveSelectorsToTarget();
                    blinkAvailableTargets();
                    break;
                case ACTION_EXECUTION:

                    fadeOutHealthBars(1).start(tweenManager);
                    killAll(availableTargets);
                    restoreAlpha(availableTargets);

                    Timeline.createSequence()
                            .push(skillInfoBar.fadeIn(0.5f))
                            .push(skillSelected.execute(this, getActiveBattler(), targetsSelected))
                            .push(skillInfoBar.fadeOut(0.5f))
                            .push(setPhase(PHASE.TURN_END))
                            .pushPause(1)
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    execute();
                                }
                            })
                            .start(tweenManager);

                    break;
                case TURN_END:
                    resetSkillsOnCircularMenu();
                    resetSkillsAndTargets();

                    if (active == battlers.size() - 1) {
                        active = 0;
                    } else {
                        executeBuffEffect().start(tweenManager);
                        active++;
                    }

                    phase = PHASE.TURN_START;
                    execute();
                    break;

            }
        } else {
            executeWin();
        }
    }

    public void executeWin() {
        fadeOutAll(1).push(mapState.fadeInAll(1)).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                gsm.pop();
                gsm.getGame().getInputMultiplexer().addProcessor(mapState);
            }
        }).start(mapState.getTweenManager());
    }

    public void setTargets() {
        switch (skillSelected.getTargetType()) {
            case SINGLE_ENEMY:
                availableTargets.addAll(getOppositeParty(getActiveBattler()));
                break;
            case SINGLE_ALLY:
                availableTargets.addAll(getParty(getActiveBattler()));
                break;
            case SELF:
                targetsSelected.add(getActiveBattler());
                break;
            case ALL_ENEMIES:
                targetsSelected.addAll(getOppositeParty(getActiveBattler()));
                break;
            case ALL_ALLIES:
                targetsSelected.addAll(getParty(getActiveBattler()));
                break;
            case ALL:
                targetsSelected.addAll(battlers);
                break;
        }

        // TODO HOTFIX
        for (int i = 0; i < availableTargets.size(); i++) {
            BattleEntity battleEntity = availableTargets.get(i);
            if (battleEntity.curHP < 1) availableTargets.remove(battleEntity);
        }
    }

    public void setPhaseBasedOnTargetsAvailable() {
        switch (skillSelected.getTargetType()) {
            case SINGLE_ENEMY:
            case SINGLE_ALLY:
                phase = PHASE.TARGET_SELECTION;
                break;
            default :
                phase = PHASE.ACTION_EXECUTION;
                break;
        }

        execute();
    }

    public void resetSkillsAndTargets() {
        targetsSelected.clear();
        availableTargets.clear();
        skillSelected = null;
    }

    /**
     * AI
     */
    public void executeAI() {
        List<Behavior> behaviors = getActiveBattler().behaviors;
        for (int i = behaviors.size() - 1; i >= 0; i--) {
            Behavior behavior = behaviors.get(i);
            skillSelected = behavior.selectSkill();
            setTargets();

            if (behavior.selectSkill() != null) {
                behavior.setTargets();

                phase = PHASE.ACTION_EXECUTION;
                execute();
                break;
            }
        }
    }

    /**
     * Battle Entities Macros
     */
    public Timeline fadeInHealthBars(float duration) {
        Timeline timeline = Timeline.createParallel();

        for(BattleEntity battleEntity : battlers) {
            timeline.push(battleEntity.fadeInBars(duration));
        }

        return timeline;
    }

    public Timeline fadeOutHealthBars(float duration) {
        Timeline timeline = Timeline.createParallel();

        for(BattleEntity battleEntity : battlers) {
            timeline.push(battleEntity.fadeOutBars(duration));
        }

        return timeline;
    }

    public void blinkAvailableTargets() {
        for (BattleEntity battleEntityW : battlers) {
            for (BattleEntity battleEntityX : availableTargets) {
                if (battleEntityW.equals(battleEntityX)) {
                    battleEntityX.fadeTo(0.5f, 0.25f).repeatYoyo(Tween.INFINITY, 0).start(tweenManager);
                }
            }
        }
    }

    public Timeline fadeInBattlers(float duration) {
        Timeline timeline = Timeline.createParallel();

        for(BattleEntity battleEntity : battlers) {
            timeline.push(battleEntity.fadeIn(duration));
        }

        return timeline;
    }

    public Timeline fadeOutBattlers(float duration) {
        Timeline timeline = Timeline.createParallel();

        for(BattleEntity battleEntity : battlers) {
            timeline.push(battleEntity.fadeOut(duration));
        }

        return timeline;
    }

    /**
     * Buffs
     */
    public Timeline executeBuffEffect() {
        Timeline timeline = Timeline.createSequence();
        for (Buff buff : getActiveBattler().buffs) {
            timeline.push(buff.executeTurnEffect(getActiveBattler()));
            buff.duration--;

            if(buff.duration == 0) {
                getActiveBattler().buffs.remove(buff);
            }
        }
        return timeline;
    }

    /**
     * Circularmenu controls
     */
    public void initSkillsOnCircularMenu() {
        int i = 2;
        for(SkillOption skillOption : circularMenu.options) {
            if(i >= battlers.get(active).skills.size()) {
                break;
            }

            skillOption.setSkill(battlers.get(active).skills.get(i));
            i++;
        }
    }

    public void executeActionSelection(Entity entity) {
        circularMenu.setPosition(entity);

        Timeline.createSequence()
                .push(zoomTo(entity.getX() + (entity.getWidth() / 2), entity.getY() + (entity.getHeight() / 2), 0.50f, 1))
                .push(circularMenu.fadeInBasicActions(0.5f))
                .start(tweenManager);
    }

    public void resetSkillsOnCircularMenu() {
        for(SkillOption skillOption : circularMenu.options) {
            skillOption.setSkill(null);
        }
    }

    private void transitionInSkillOptions() {
        initSkillsOnCircularMenu();

        if(skillCount() > 2) {
            Timeline.createSequence()
                    .push(circularMenu.transitionInExtendedOptions(1)
                    .push(Tween.call(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            isSkillMenuOpen = true;
                        }
                    }))
                    .start(tweenManager));
        }
    }

    /**
     * Selector Control
     */
    public void moveSelectorsToTarget() {
        selector_1.setPosition(getOppositeParty(getActiveBattler()).get(0).getX() + (getOppositeParty(getActiveBattler()).get(0).getWidth() / 2) - (selector_1.getWidth() / 2), getOppositeParty(getActiveBattler()).get(0).getY() + getOppositeParty(getActiveBattler()).get(0).getHeight());
        selector_2.setPosition(getOppositeParty(getActiveBattler()).get(0).getX() + (getOppositeParty(getActiveBattler()).get(0).getWidth() / 2) - (selector_2.getWidth() / 2), getOppositeParty(getActiveBattler()).get(0).getY() - (selector_2.getHeight() / 2));
    }

    /**
     * Tweens
     */
    public Tween setPhase(final PHASE phase1) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                phase = phase1;
            }
        });
    }

    public Tween setAttribute(final BattleEntity.Attribute attribute, final BattleEntity target, final float value) {

        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {

                Timeline timeline_1 = Timeline.createSequence();
                timeline_1.push(target.fadeInBars(0));

                switch (attribute) {
                    case HP:
                        finalValue = target.curHP + value;

                        if(target.curHP > target.maxHP) {
                            finalValue = target.maxHP;
                        }

                        tweenManager.killTarget(target, EntityAccessor.HP);
                        timeline_1.push(target.setHP(finalValue, 0.25f));

                        break;
                    case MP:
                        finalValue = target.curMP + value;

                        if( target.curMP > target.maxMP) {
                            finalValue = target.maxMP;
                        }

                        tweenManager.killTarget(target, EntityAccessor.MP);
                        timeline_1.push(target.setMP(finalValue, 0.25f));

                        break;
                    case TP:
                        finalValue = target.curTP + value;

                        if(target.curTP > target.maxTP) {
                            finalValue = target.maxTP;
                        }

                        tweenManager.killTarget(target, EntityAccessor.TP);
                        timeline_1.push(target.setTP(finalValue, 0.25f));

                        break;
                }


                timeline_1.push(target.fadeOutBars(0.25f));
                timeline_1.start(tweenManager);

                if (finalValue < 1) {
                    target.setAnimation(target.DEAD).start(tweenManager);

                    Timeline.createSequence()
                            .push(target.fadeOutBars(1)
                            .push(target.fadeOut(1)))
                            .push(removeFromBattle(target))
                            .start(tweenManager);

                } else {

                    Timeline timeline = Timeline.createSequence();

                    float origX = target.getX();

                    timeline.push(target.setAnimation(target.HURT));
                    timeline.push(target.moveTo(target.getX() + (10 * -getSideFacing(target)), target.getY(), 0.15f));
                    timeline.push(target.setAnimation(target.WALKING));
                    timeline.push(target.moveTo(origX, target.getY(), 0.30f));
                    timeline.push(target.setAnimation(target.IDLE));
                    timeline.start(tweenManager);
                }

            }
        });

    }

    /**
     * Battle action timelines
     */
    public Timeline normalWeaponSwing(BattleEntity battleEntity, BattleEntity target, final Entity icon) {
        // TODO HOTFIX : fix allowance for side b

        Timeline timeline = Timeline.createSequence();

        if(icon != null) {
            temporaryEntities.add(icon);

            int allowance = getSideFacing(battleEntity) == -1 ? 6 : 0;

            icon.setOrigin(32, 0);
            icon.setVisible(false);

            timeline.push(icon.moveTo(targetsSelected.get(0).getX() - (targetsSelected.get(0).getWidth() * getSideFacing(battleEntity)) + ((-battleEntity.getWidth()) / 2) + allowance, targetsSelected.get(0).getY() + allowance + 4, 0))
            .push(Tween.call(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    icon.setVisible(true);
                }
            }))
            .push(icon.rotateTo(-45, 0))
            .push(icon.rotateTo(getSideFacing(battleEntity) == -1 ? 45 : -135, 0.09f * 3))
            .push(Tween.call(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    icon.setVisible(false);
                    temporaryEntities.remove(icon);
                }
            }));

        }

        return timeline;
    }

    /**
     * Convinience methods
     */
    public BattleEntity getActiveBattler() {
        return battlers.get(active);
    }

    public int skillCount() {
        return getActiveBattler().skills.size();
    }

    public void processSkill() {
        setTargets();
        setPhaseBasedOnTargetsAvailable();

        zoomTo(0, 0, 1, 1)
        .push(circularMenu.hide(0.25f))
        .start(tweenManager);
    }

    @Override
    public Timeline fadeInAll(float duration) {
        Timeline timeline = super.fadeInAll(duration);

        for(BattleEntity battler : battlers) {
            timeline.push(battler.fadeIn(duration));
        }

        return timeline;
    }

    @Override
    public Timeline fadeOutAll(float duration) {
        Timeline timeline = super.fadeOutAll(duration);

        timeline.push(background.fadeOut(duration));

        for(BattleEntity battler : battlers) {
            timeline.push(battler.fadeOut(duration));
        }

        return timeline;
    }

    /**
     * Draws battler names, uses custom font if it is existing.
     */
    public void drawBattlerNames(SpriteBatch batch) {
        if(phase == PHASE.ACTION_SELECTION || phase == PHASE.TARGET_SELECTION) {
            for (BattleEntity battleEntity : battlers) {
                float textWidth = battleEntity.getBitmapFont() == null ? gsm.getBitmapFont().getWrappedBounds(battleEntity.name, 200).width : battleEntity.getBitmapFont().getWrappedBounds(battleEntity.name, 200).width;
                float textHeight = battleEntity.getBitmapFont() == null ? gsm.getBitmapFont().getWrappedBounds(battleEntity.name, 200).height : battleEntity.getBitmapFont().getWrappedBounds(battleEntity.name, 200).height;

                if (battleEntity.getBitmapFont() != null) {
                    battleEntity.getBitmapFont().drawWrapped(batch, battleEntity.name, battleEntity.getX() + (battleEntity.getWidth() / 2) - (textWidth / 2), battleEntity.getY() + battleEntity.getHeight() + textHeight, 200);
                } else {
                    gsm.getBitmapFont().drawWrapped(batch, battleEntity.name, battleEntity.getX() + (battleEntity.getWidth() / 2) - (textWidth / 2), battleEntity.getY() + battleEntity.getHeight() + textHeight, 200);
                }
            }
        }
    }

    public List<BattleEntity> getOppositeParty(BattleEntity caster) {
        if(partyA.contains(caster)) {
            return partyB;
        } else {
            return partyA;
        }
    }

    public List<BattleEntity> getParty(BattleEntity caster) {
        if(partyA.contains(caster)) {
            return partyA;
        } else {
            return partyB;
        }
    }

    public int getSideFacing(BattleEntity caster) {
        if(partyA.contains(caster)) {
            return 1;
        } else {
            return -1;
        }
    }

    public List<BattleEntity> getWinner() {
        if(partyA.isEmpty()) {
            return partyB;
        } else if(partyB.isEmpty()) {
            return partyA;
        }

        return null;
    }

    public Tween removeFromBattle(final BattleEntity target) {
        return Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                getParty(target).remove(target);
                battlers.remove(target);
            }
        });
    }

    /**
     * Accessors
     */
    public List<BattleEntity> getAvailableTargets() {
        return availableTargets;
    }

    public Skill getSkillSelected() {
        return skillSelected;
    }

    public void setSkillSelected(Skill skillSelected, Option option) {
        Timeline.createSequence()
                .push(SoundManager.playSound(Assets.getSound("cursor_1")))
                .push(circularMenu.resetScale(0.05f))
                .push(option.scaleTo(1.2f, 0.05f))
                .start(tweenManager);

        this.skillSelected = skillSelected;
    }

    public List<BattleEntity> getTargetsSelected() {
        return targetsSelected;
    }

    /**
     * Overriden methods
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (phase) {
            case ACTION_SELECTION:
                handleActionSelectionTouchDown();
                break;
            case TARGET_SELECTION:
                handleTargetSelectionTouchDown();
                break;
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        switch (phase) {
            case ACTION_SELECTION:
                handleActionSelectionTouchUp();
                break;
            case TARGET_SELECTION:
                handleTargetSelectionTouchUp();
                break;
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        switch (phase) {
            case ACTION_SELECTION:
                handleActionSelectionTouchDragged();
                break;
            case TARGET_SELECTION:
                handleTargetSelectionTouchDragged();
                break;
        }

        return super.touchDragged(screenX, screenY, pointer);
    }

    /**
     * Phase
     */
    public enum PHASE {
        TURN_START, ACTION_SELECTION, TARGET_SELECTION, ACTION_EXECUTION, TURN_END
    }

    /**
     * Test
     */
    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }
}
