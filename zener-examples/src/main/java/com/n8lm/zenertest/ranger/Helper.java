package com.n8lm.zenertest.ranger;

import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Vector3f;

/**
 * Created on 2014/7/26.
 *
 * @author Alchemist
 */
public class Helper {

    private static Vector3f[] tempv = new Vector3f[3];
    public static void angleToVector(float[] angles, Vector3f vector) {
        Quaternion q = new Quaternion();
        q.fromAngles(angles);
        q.toAxis(tempv);
        vector.set(tempv[2]);
    }

    public static void angleToQuaternion(float[] angles, Quaternion q) {
        q.fromAngles(angles);
    }
}
