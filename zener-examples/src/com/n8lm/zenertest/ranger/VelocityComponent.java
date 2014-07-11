package com.n8lm.zenertest.ranger;

import com.artemis.Component;
import com.n8lm.zener.math.Vector3f;

/**
 * Created on 2014/7/11.
 *
 * @author Alchemist
 */
public class VelocityComponent extends Component {

    private Vector3f velocity;

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }
}
