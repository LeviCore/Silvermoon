package com.levicore.silvermoon.states;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.levicore.silvermoon.*;
import com.levicore.silvermoon.Character;
import com.levicore.silvermoon.battle.Behavior;
import com.levicore.silvermoon.battle.Buff;
import com.levicore.silvermoon.battle.Skill;
import com.levicore.silvermoon.core.Equipment;
import com.levicore.silvermoon.core.Item;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.entities.ui.*;
import com.levicore.silvermoon.utils.comparators.StatComparator;
import com.levicore.silvermoon.utils.map.MapState;
import com.levicore.silvermoon.utils.tween.EntityAccessor;
import com.sun.istack.internal.Nullable;

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

    protected VictoryWindow victoryWindow;

    protected List<Character> characters;
    protected List<BattleEntity> enemies;
    protected List<Item> itemDrops;

    private boolean done;

    public BattleState(GSM gsm, MapState mapState, Music music, PHASE phase, List<Character> characters, List<BattleEntity> enemies) {
        super(gsm);
        this.mapState = mapState;
        this.phase = phase;

        this.characters = characters;
        this.enemies = enemies;

        initBattlers();

        // TODO : move in a Timline method if necessary to be overridden for custom cutscenes.
        Timeline.createSequence()
                .push(fadeInBattlers(1))
                .push(SoundManager.playBackgroundMusic(music, 1, true))
                .start(tweenManager);

        execute();
    }

    public void initBattlers() {
        // Set item drops
        itemDrops = new ArrayList<>();
        for(BattleEntity enemy : enemies) {
            itemDrops.addAll(enemy.getItemDrops());
        }

        // Set first 4 as active battlers, TODO position them.
        float x = -Silvermoon.SCREEN_WIDTH / 3;
        float xSkew = 0;

        float y = 0;



        for (int i1 = 0; i1 < characters.size(); i1++) {
            Character character = characters.get(i1);

            if (character.getBattleEntity().getCurHP() <= 0) {
                characters.remove(character);
            }
        }

        float totalExp = 0;

        for (BattleEntity enemy : enemies) {
            totalExp += enemy.getExpReward();
        }


        victoryWindow = new VictoryWindow(this, characters, itemDrops, totalExp);

        for (int i = 0; i < 4; i++) {
            if(i <= characters.size() - 1) {
                Character character = characters.get(i);

                if (character != null) {
                    character.getBattleEntity().setPosition(x + xSkew, y);
                    y -= character.getBattleEntity().getHeight() + 15;
                    xSkew -= 15;
                    partyA.add(character.getBattleEntity());
                }
            }

        }

        x = Silvermoon.SCREEN_WIDTH / 3;
        xSkew = 0;
        y = 0;

        for(BattleEntity battleEntity : enemies) {

            for(Behavior behavior : battleEntity.getBehaviors()) {
                behavior.setBattleState(this);
            }

            battleEntity.setPosition(x + xSkew, y);
            y -= battleEntity.getHeight() + 15;
            xSkew += 15;
        }

        partyB.addAll(enemies);

        battlers.addAll(partyA);
        battlers.addAll(partyB);

        Collections.sort(battlers, new StatComparator(StatComparator.Attribute.SPEED));
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

        victoryWindow.updateAndRender(delta, batch);

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
        db = new DialogBox(Assets.SYSTEM_ATLAS.findRegion("Skill_Info_Background"));
        skillInfoBar = new SkillInfoBar();

        background = new Entity("data/battlebacks/back_1.png");
        selector_1 = new Entity(Assets.SYSTEM_ATLAS.findRegion("Selector", 1));
        selector_2 = new Entity(Assets.SYSTEM_ATLAS.findRegion("Selector", 2));

        background.setSize(Silvermoon.SCREEN_WIDTH * 1.3f, Silvermoon.SCREEN_HEIGHT * 1.3f);
        background.setPosition(-Silvermoon.SCREEN_WIDTH * 1.3f / 2, -Silvermoon.SCREEN_HEIGHT * 1.3f / 2);
        entities.add(background);

        selector_1.setSize(32 * 1.3f, selector_1.getHeight());
        selector_2.setSize(32 * 2, selector_2.getHeight());

        gsm.getBitmapFont().setScale(0.75f, 0.75f);

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
            if(skillSelected == getActiveBattler().getSkills().get(0)) {
                processSkill();
            } else {
                circularMenu.transitionOutExtendedOptions(0.25f).start(tweenManager);
                setSkillSelected(getActiveBattler().getSkills().get(0), circularMenu.basicActions[0]);
            }

        } else if(circularMenu.basicActions[2].contains(worldCoordinates.x, worldCoordinates.y)) {
            /** Skill 1, default is Defend */
            if(skillSelected == getActiveBattler().getSkills().get(1)) {
                processSkill();
            } else {
                circularMenu.transitionOutExtendedOptions(0.25f).start(tweenManager);
                setSkillSelected(getActiveBattler().getSkills().get(1), circularMenu.basicActions[2]);
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
                    if(getActiveBattler().getCurHP() <= 0) {
                        phase = PHASE.TURN_END;
                        execute();
                    } else {
                        executeBuffEffect().start(tweenManager);

                        fadeInHealthBars(1)
                                .push(circularMenu.resetScale(1))
                                .start(tweenManager);

                        if (!getActiveBattler().getBehaviors().isEmpty()) {
                            executeAI();
                        } else {
                            phase = PHASE.ACTION_SELECTION;
                            execute();
                        }
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

                    for (int i = 0; i < battlers.size(); i++) {
                        BattleEntity battleEntity = battlers.get(i);
                        if (battleEntity.getCurHP() <= 0) {
                            battlers.remove(battleEntity);
                            getParty(battleEntity).remove(battleEntity);
                            active--;
                        }
                    }

                    if (active == battlers.size() - 1) {
                        active = 0;
                    } else {
                        active++;
                    }

                    phase = PHASE.TURN_START;

                    execute();
                    break;

            }
        } else {
            if(getWinner().equals(partyA)) {
                victoryWindow.next().start(getTweenManager());

                if (done) {
                    executeWin();
                    done = false;
                }
            } else {
                // TODO PUT GAMEOVER SCREEN HERE
            }
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
            if (battleEntity.getCurHP() < 1) availableTargets.remove(battleEntity);
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
        List<Behavior> behaviors = getActiveBattler().getBehaviors();
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
        for (Buff buff : getActiveBattler().getBuffs()) {
            timeline.push(buff.executeTurnEffect(getActiveBattler()));
            buff.duration--;

            if(buff.duration == 0) {
                getActiveBattler().getBuffs().remove(buff);
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
            if(i >= battlers.get(active).getSkills().size()) {
                break;
            }

            skillOption.setSkill(battlers.get(active).getSkills().get(i));
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
                        finalValue = target.getCurHP() + value;

                        if(target.getCurHP() > target.getMaxHP()) {
                            finalValue = target.getMaxHP();
                        }

                        tweenManager.killTarget(target, EntityAccessor.HP);
                        timeline_1.push(target.setHP(finalValue, 1));

                        if (finalValue < 1) {
                            target.setAnimation(target.getDeadPose()).start(tweenManager);

                            Timeline.createSequence()
                                    .push(target.fadeOutBars(1)
                                    .push(target.fadeOut(1)))
                                    .start(tweenManager);

                        } else {

                            Timeline timeline = Timeline.createSequence();

                            float origX = target.getX();

                            timeline.push(target.setAnimation(target.getHurtPose()));
                            timeline.push(target.moveTo(target.getX() + (10 * -getSideFacing(target)), target.getY(), 0.15f));
                            timeline.push(target.setAnimation(target.getWalkingPose()));
                            timeline.push(target.moveTo(origX, target.getY(), 0.30f));
                            timeline.push(target.setAnimation(target.getIdlePose()));
                            timeline.start(tweenManager);
                        }

                        break;
                    case MP:
                        finalValue = target.getCurMP() + value;

                        if( target.getCurMP() > target.getMaxMP()) {
                            finalValue = target.getMaxMP();
                        }

                        tweenManager.killTarget(target, EntityAccessor.MP);
                        timeline_1.push(target.setMP(finalValue, 0.25f));

                        break;
                    case TP:
                        finalValue = target.getCurTP() + value;

                        if(target.getCurTP() > target.getMaxTP()) {
                            finalValue = target.getMaxTP();
                        }

                        tweenManager.killTarget(target, EntityAccessor.TP);
                        timeline_1.push(target.setTP(finalValue, 0.25f));

                        break;
                }

                timeline_1.push(target.fadeOutBars(0.25f));
                timeline_1.start(tweenManager);

            }
        });

    }

    /**
     * Battle action timelines
     */
    public Timeline normalWeaponSwing(BattleEntity battleEntity, @Nullable final Entity icon) {
        // TODO HOTFIX : fix allowance for side b

        final Entity iconUsed = icon != null ? icon : battleEntity.getEquipment(Equipment.EQUIPMENT_TYPE.WEAPON_SLOT) != null ? battleEntity.getEquipment(Equipment.EQUIPMENT_TYPE.WEAPON_SLOT).getIcon() : null;

        Timeline timeline = Timeline.createSequence();

        if(iconUsed != null) {
            temporaryEntities.add(iconUsed);

            int allowance = getSideFacing(battleEntity) == -1 ? 6 : 0;

            iconUsed.setOrigin(32, 0);
            iconUsed.setVisible(false);

            timeline.push(iconUsed.moveTo(targetsSelected.get(0).getX() - (targetsSelected.get(0).getWidth() * getSideFacing(battleEntity)) + ((-battleEntity.getWidth()) / 2) + allowance, targetsSelected.get(0).getY() + allowance + 4, 0))
            .push(Tween.call(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    iconUsed.setVisible(true);
                }
            }))
            .push(iconUsed.rotateTo(-45, 0))
            .push(iconUsed.rotateTo(getSideFacing(battleEntity) == -1 ? 45 : -135, 0.09f * 3))
            .push(Tween.call(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    iconUsed.setVisible(false);
                    temporaryEntities.remove(iconUsed);
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
        return getActiveBattler().getSkills().size();
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

                String text = "Lvl. " + battleEntity.getLevel() + " " + battleEntity.getName();

                float textWidth = battleEntity.getBitmapFont() == null ? gsm.getBitmapFont().getWrappedBounds(text, 200).width : battleEntity.getBitmapFont().getWrappedBounds(text, 200).width;
                float textHeight = battleEntity.getBitmapFont() == null ? gsm.getBitmapFont().getWrappedBounds(text, 200).height : battleEntity.getBitmapFont().getWrappedBounds(text, 200).height;

                if (battleEntity.getBitmapFont() != null) {
                    battleEntity.getBitmapFont().drawWrapped(batch, text, battleEntity.getX() + (battleEntity.getWidth() / 2) - (textWidth / 2), battleEntity.getY() + battleEntity.getHeight() + textHeight, 200);
                } else {
                    gsm.getBitmapFont().drawWrapped(batch, text, battleEntity.getX() + (battleEntity.getWidth() / 2) - (textWidth / 2), battleEntity.getY() + battleEntity.getHeight() + textHeight, 200);
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
        if(!partyA.isEmpty() && partyA.contains(caster)) {
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

        if(getWinner() != null) {
            switch (victoryWindow.getPhase()) {
                case CHARACTER_INFO:
                    victoryWindow.next().start(tweenManager);
                    break;
                case ITEMS_INFO:
                    Timeline.createSequence()
                            .push(victoryWindow.next())
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    done = true;
                                    execute();
                                }
                            })
                            .start(tweenManager);
                    break;
            }
        }

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
