package com.n8lm.zener.ranger;

import com.artemis.Entity;
import com.artemis.World;
import com.n8lm.zener.animation.*;
import com.n8lm.zener.assets.Model;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.graphics.GeometryComponent;
import com.n8lm.zener.graphics.PerspectiveProjection;
import com.n8lm.zener.graphics.ViewComponent;
import com.n8lm.zener.input.Input;
import com.n8lm.zener.input.InputAdapter;
import com.n8lm.zener.math.MathUtil;
import com.n8lm.zener.math.Quaternion;
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
    private final Entity cam;
    private final Entity mapEntity;
    private final World world;

    private final float PI4 = MathUtil.PI / 4;
    private final float[] DIRS = {0.0f, - PI4, - PI4 * 2, - PI4 * 3, - PI4 * 4, - PI4 * 5, - PI4 * 6, - PI4 * 7};

    public CharacterInputAdapter(World world, Entity character, Entity cam, Entity mapEntity) {
        this.world = world;
        this.character = character;
        this.cam = cam;
        this.mapEntity = mapEntity;
    }

    float[] seeAngles = new float[3];
    float[] moveAngles = new float[3];
    private Vector3f seeDir = new Vector3f();
    //private Vector3f characterDir = new Vector3f();
    //private float characterX, characterZ;


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

    private int button1Frame = 0;
    private int button2Frame = 0;

    @Override
    public void mousePressed(int button, int x, int y) {
        if (button == 0) {
            button1Frame = 1;
        }
        if (button == 1) {
            button2Frame = 1;
            seeAngles[0] = character.getComponent(CharacterComponent.class).getHeadAngles()[0];
            seeAngles[1] = character.getComponent(CharacterComponent.class).getHeadAngles()[1];
            seeAngles[2] = character.getComponent(CharacterComponent.class).getHeadAngles()[2];

            character.getComponent(GeometryComponent.class).setVisible(false);
        }

    }

    @Override
    public void mouseReleased(int button, int x, int y) {

        if (button == 0) {

            CharacterSystem cs = world.getSystem(CharacterSystem.class);
            cs.shoot(character);
            //Model model = ResourceManager.getInstance().getModel("human");
            //character.getComponent(AnimationComponent.class).add(new SkeletonAnimationController(model.getAnimation("Attack_bow"), false, 1.0f, 20.0f));

            button1Frame = 0;
        }

        if (button == 1) {

            character.getComponent(GeometryComponent.class).setVisible(true);

            button2Frame = 0;
        }
    }

    private Vector3f tempdir = new Vector3f();
    private Quaternion temprot = new Quaternion();

    @Override
    public void run(World world, Event event) {
        move = keya | keyd | keys | keyw;

        seeAngles[2] -= Mouse.getDX() / 100.0f;
        seeAngles[1] = 0;
        seeAngles[0] -= Mouse.getDY() / 100.0f;

        if (seeAngles[0] < 0.6f)
            seeAngles[0] = 0.6f;
        if (seeAngles[0] > 2.8)
            seeAngles[0] = 2.8f;

        Helper.angleToVector(seeAngles, seeDir);
        //seeRot.fromAngles(seeAngles);
        //Vector3f[] v = new Vector3f[3];
        //seeRot.toAxis(v);
        //seeDir = v[2];

        cam.getComponent(TransformComponent.class).getLocalTransform().getTranslation().set(seeDir.negate());

        Helper.angleToQuaternion(seeAngles, temprot);
        cam.getComponent(TransformComponent.class).getLocalTransform().getRotation().set(temprot);

        Model model = ResourceManager.getInstance().getModel("human");

        float camHeight = 2.0f;
        float camLength = 3.0f;
        float camFov = 90f;

        CharacterComponent cc = character.getComponent(CharacterComponent.class);

        if (button2Frame > 0) {
            camHeight = 1.5f;
            camLength = - 0.2f;

            cc.getHeadAngles()[0] = seeAngles[0];
            cc.getHeadAngles()[1] = seeAngles[1];
            cc.getHeadAngles()[2] = seeAngles[2];

            camFov = 60f;
            button2Frame ++;
        }

        if (button1Frame > 0) {
            if (button1Frame == 1) {
                cc.setAction(CharacterComponent.Action.Bow);
                cc.setActionTime(0);
            }
            //character.getComponent(SkeletonComponent.class).setCurrentPosesMatrices(model.getAnimation("Attack_bow").getFrame(40).getPoseMatrices());
            seeDir.z = 0;
            //character.getComponent(TransformComponent.class).getLocalTransform().getRotation().lookAt(Vector3f.UNIT_Z, seeDir.negate());
            button1Frame ++;
        }

        if (move && cc.getAction() != CharacterComponent.Action.Bow) {
            Vector3f moveDir = new Vector3f();

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

            moveAngles[2] = seeAngles[2] + angle;
            moveAngles[0] = MathUtil.PI / 2;//seeAngles[0];
            moveAngles[1] = seeAngles[1];
            Helper.angleToVector(moveAngles, moveDir);
            moveDir.z = 0;
            moveDir.normalizeLocal();

            if (button2Frame == 0) {
                cc.getHeadAngles()[0] = moveAngles[0];
                cc.getHeadAngles()[1] = moveAngles[1];
                cc.getHeadAngles()[2] = moveAngles[2];
            }

            float speed;
            if (character.getComponent(AnimationComponent.class).getAnimationControllerByName("Attack_bow") == null) {
                speed = 0.005f;
            } else {
                speed = 0.002f;
            }

            float runConstant = 1.0f;
            if (leftShift) {
                runConstant *= 2;
            }

            cc.getMovement().addLocal(moveDir.mult(speed * runConstant).mult((0.10f * runConstant - cc.getMovement().length()) * 20f));
        }
        /*
        Vector3f f = cc.getMovement().normalize().multLocal(0.003f);
        if (cc.getMovement().lengthSquared() > f.lengthSquared())
            cc.getMovement().subtractLocal(f);
        else
            cc.getMovement().set(Vector3f.ZERO);
        Vector3f charTrans = character.getComponent(TransformComponent.class).getLocalTransform().getTranslation();
        charTrans.addLocal(cc.getMovement());
        */
        //Helper.angleToVector(cc.getHeadAngles(), tempdir);
        cam.getComponent(TransformComponent.class).getLocalTransform().getTranslation().multLocal(camLength).addLocal(0, 0, camHeight);
        cam.getComponent(TransformComponent.class).getLocalTransform().getTranslation().addLocal(character.getComponent(TransformComponent.class).getLocalTransform().getTranslation());

        ((PerspectiveProjection)cam.getComponent(ViewComponent.class).getProjection()).setFov(camFov);
    }

}
