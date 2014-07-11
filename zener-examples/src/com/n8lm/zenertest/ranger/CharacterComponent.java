package com.n8lm.zenertest.ranger;

import com.artemis.Component;
import com.artemis.Entity;

/**
 * Created on 2014/7/11.
 *
 * @author Alchemist
 */
public class CharacterComponent extends Component {
    private int level;
    private int exp;
    private float hp;
    private float maxhp;
    //private Weapon weapon;
    transient private Entity weaponEntity;
    private AbilityData abilityData;

    public CharacterComponent(float maxhp, AbilityData abilityData) {
        this.level = 1;
        this.exp = 0;
        this.hp = this.maxhp = maxhp;
        this.abilityData = abilityData;
    }

    @Override
    public String toString() {
        return String
                .format("[Level = %d, EXP = %d, Weapon = %s, AbilityData = %s, HP = %f/%f]",
                        level, exp,
                        weaponEntity.toString(),
                        abilityData.toString(),
                        hp, maxhp);
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
}
