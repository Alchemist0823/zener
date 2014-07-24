package com.n8lm.zenertest.ranger;

import com.artemis.Component;
import com.artemis.Entity;
import com.n8lm.zener.math.Vector3f;

/**
 * Created on 2014/7/11.
 *
 * @author Alchemist
 */
public class CharacterComponent extends Component {
    private int level;
    private int exp;
    private float maxhp;
    //private Weapon weapon;
    transient private Entity weaponEntity;
    private AbilityData abilityData;

    private float[] headAngles;
    private float health;

    public CharacterComponent(float maxhp, AbilityData abilityData) {
        this.level = 1;
        this.exp = 0;
        this.health = this.maxhp = maxhp;
        this.abilityData = abilityData;
        this.headAngles = new float[3];
    }

    @Override
    public String toString() {
        return String
                .format("[Level = %d, EXP = %d, Weapon = %s, AbilityData = %s, HP = %f/%f]",
                        level, exp,
                        weaponEntity.toString(),
                        abilityData.toString(),
                        health, maxhp);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public AbilityData getAbilityData() {
        return abilityData;
    }

    public int getExp() {
        return exp;
    }

    public void obtainExp(int exp) {
        this.exp += exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevelExp() {
        return level * 10 + 90;
    }

    public Entity getWeaponEntity() {
        return weaponEntity;
    }

    public void setWeaponEntity(Entity weaponEntity) {
        this.weaponEntity = weaponEntity;
    }

    public float[] getHeadAngles() {
        return headAngles;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}
