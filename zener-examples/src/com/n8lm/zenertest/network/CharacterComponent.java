package com.n8lm.zenertest.network;

import com.artemis.Component;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public class CharacterComponent extends Component {
    private String name;
    private int id;
    private float x, y;

    public CharacterComponent(String name, int id) {
        this(name, id, 0f, 0f);
    }

    public CharacterComponent(String name, int id, float x, float y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
