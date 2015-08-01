package com.n8lm.zener.particle;

import com.n8lm.zener.graphics.ViewRenderSystem;
import com.n8lm.zener.math.Matrix4f;
import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Vector3f;

/**
 * Created by Alchemist0823 on 7/23/2015.
 */
public class RenderModifer implements ParticleModifer {

    public enum RenderMode {
        Billboard,
        VerticalBillboard,
        HorizonBillboard,
        Normal,
    }

    public enum SortMode {
        Distance,
        NoSorting
    }

    private ViewRenderSystem subRenderSystem;

    private RenderMode renderMode;
    private SortMode sortMode;

    //temp
    private Matrix4f mv = new Matrix4f();
    private Matrix4f mvTrans = new Matrix4f();
    private Matrix4f mvp = new Matrix4f();
    private Matrix4f mvpTrans = new Matrix4f();
    private Quaternion tempQuat = new Quaternion();
    private Vector3f tvec = new Vector3f();

    public RenderModifer(SortMode sortMode, RenderMode renderMode, ViewRenderSystem subRenderSystem) {
        this.sortMode = sortMode;
        this.renderMode = renderMode;
        this.subRenderSystem = subRenderSystem;
    }

    @Override
    public void apply(Particle p, float delta) {

        if (renderMode == RenderMode.Billboard) {
            tempQuat.fromAngleAxis(p.rotateAngle, Vector3f.UNIT_Z);
            mvpTrans.mult(tempQuat, p.rotation);
        } else if (renderMode == RenderMode.VerticalBillboard) {
            tempQuat.fromAngleAxis(p.rotateAngle, Vector3f.UNIT_Y);
            mvTrans.mult(tempQuat, p.rotation);
        } else if (renderMode == RenderMode.HorizonBillboard) {
            tempQuat.fromAngleAxis(p.rotateAngle, Vector3f.UNIT_Z);
            mvTrans.mult(tempQuat, p.rotation);
        }

        switch (sortMode) {
            case Distance:
                mvp.mult(p.position, tvec);
                p.order = tvec.z;
                break;
            case NoSorting:
            default:
                break;
        }
    }

    @Override
    public void frameStarted() {
        subRenderSystem.getModelViewProjectionMatrix(mvp);
        mvpTrans.set(mvp);
        mvpTrans.transposeLocal();

        subRenderSystem.getModelViewMatrix(mv);
        mvTrans.set(mv);
        mvTrans.transposeLocal();
    }
}
