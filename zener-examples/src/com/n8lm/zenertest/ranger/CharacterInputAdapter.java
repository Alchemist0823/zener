package com.n8lm.zenertest.ranger;

import com.artemis.Entity;
import com.artemis.World;
import com.n8lm.zener.animation.*;
import com.n8lm.zener.assets.Model;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.input.Input;
import com.n8lm.zener.input.InputAdapter;
import com.n8lm.zener.math.MathUtil;
import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Transform;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.script.Event;
import com.n8lm.zener.script.NativeScript;
import org.lwjgl.input.Mouse;

/**
 * Created on 2014/7/11.
 *
 * @author Alchemist
 */
public class CharacterInputAdapter extends InputAdapter implements NativeScript{
    private final Entity character;
    private final Entity control;
    private final Entity cam;

    private final float PI4 = MathUtil.PI / 4;
    private final float[] DIRS = {0.0f, - PI4, - PI4 * 2, - PI4 * 3, - PI4 * 4, - PI4 * 5, - PI4 * 6, - PI4 * 7};

    public CharacterInputAdapter(Entity character, Entity control, Entity cam) {
        this.character = character;
        this.control = control;
        this.cam = cam;
    }

    private float z = 0, x = 2;
    private Vector3f seeDir = new Vector3f();

    boolean move = false;
    boolean keyw = false;
    boolean keya = false;
    boolean keyd = false;
    boolean keys = false;
    boolean leftShift = false;

    @Override
    public void keyPressed(int key, char c) {

        if (key == Input.KEY_W) {
            keyw = true;
        }
        if (key == Input.KEY_S) {
            keys = true;
        }
        if (key == Input.KEY_A) {
            keya = true;
        }
        if (key == Input.KEY_D) {
            keyd = true;
        }
        if (key == Input.KEY_LSHIFT)
            leftShift = true;
    }

    @Override
    public void keyReleased(int key, char c) {

        if (key == Input.KEY_W) {
            keyw = false;
        }
        if (key == Input.KEY_S) {
            keys = false;
        }
        if (key == Input.KEY_A) {
            keya = false;
        }
        if (key == Input.KEY_D) {
            keyd = false;
        }
        if (key == Input.KEY_LSHIFT)
            leftShift = false;
    }

    @Override
    public void run(World world, Event event) {

        z -= Mouse.getDX() / 100.0f;
        x -= Mouse.getDY() / 100.0f;
        float[] angles = new float[3];
        angles[0] = x; //(newy - oldy) / 100.0f;
        angles[1] = 0;
        angles[2] = z;//(newx - oldx) / 100.0f;

        if (x < 0.6f)
            x = 0.6f;
        if (x > 2.8)
            x = 2.8f;

        move = keya | keyd | keys | keyw;

        cam.getComponent(TransformComponent.class).getLocalTransform().getRotation().fromAngles(angles);
        Vector3f[] v = new Vector3f[3];
        cam.getComponent(TransformComponent.class).getLocalTransform().getRotation().toAxis(v);
        seeDir = v[2];

        cam.getComponent(TransformComponent.class).getLocalTransform().getTranslation().set(seeDir.negate()).multLocal(3.0f).addLocal(0, 0, 2);
        //System.out.println(x + " " + v[2]);

        Model model = ResourceManager.getInstance().getModel("human");
        if (move) {
            Vector3f moveDir;

            Quaternion q = new Quaternion();

            float angle = 0;
            if (keyw) {
                angle = DIRS[0];
            }
            if (keyd) {
                angle = DIRS[2];
            }
            if (keys) {
                angle = DIRS[4];
            }
            if (keya) {
                angle = DIRS[6];
            }
            if (keyw && keyd) {
                angle = DIRS[1];
            }
            if (keyd && keys) {
                angle = DIRS[3];
            }
            if (keys && keya) {
                angle = DIRS[5];
            }
            if (keya && keyw) {
                angle = DIRS[7];
            }

            angles[2] = z + angle;
            q.fromAngles(angles);
            q.toAxis(v);
            moveDir = v[2];
            moveDir.z = 0;
            moveDir.normalizeLocal();

            float speed = 0.05f;
            float runConstant = 1.0f;
            if (leftShift) {
                runConstant *= 2;
            }
            control.getComponent(TransformComponent.class).getLocalTransform().getTranslation().addLocal(
                    moveDir.mult(speed * runConstant)
            );

            character.getComponent(TransformComponent.class).getLocalTransform().getRotation().lookAt(Vector3f.UNIT_Z, moveDir.negate());
            if (character.getComponent(AnimationComponent.class).getAnimationControllerByName("Run") == null)
                character.getComponent(AnimationComponent.class).add(new SkeletonAnimationController(model.getAnimation("Run"), true, runConstant));
            else
                character.getComponent(AnimationComponent.class).getAnimationControllerByName("Run").setSpeed(runConstant);
        } else {
            if (character.getComponent(AnimationComponent.class).getAnimationControllerByName("Run") != null) {

                character.getComponent(AnimationComponent.class).removeAnimationControllerByName("Run");
                character.getComponent(SkeletonComponent.class).setCurrentPosesMatrices(model.getAnimation("Idle").getFrame(0).getPoseMatrices());
            }

        }


    }
}
