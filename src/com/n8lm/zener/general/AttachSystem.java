package com.n8lm.zener.general;


import java.util.BitSet;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.n8lm.zener.animation.SkeletonComponent;
import com.n8lm.zener.graphics.GeometryComponent;
import com.n8lm.zener.math.Matrix4f;
import com.n8lm.zener.math.Transform;

public class AttachSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<TransformComponent> tm;
	//@Mapper ComponentMapper<GeometryComponent> gm;
	
	private BitSet isUpdated;
	private Entity[] stack;
	
	public AttachSystem() {
		super(Aspect.getAspectForAll(TransformComponent.class));
		isUpdated = new BitSet();
		stack = new Entity[128];
	}
	
	@Override
	protected void begin() {
		isUpdated.clear();
	}

	@Override
	protected void process(Entity e) {
		if (!isUpdated.get(e.getId())) {
				
			int stackSize = 0;
			TransformComponent t;
			stack[stackSize] = e;
			t = tm.get(stack[stackSize ++]);
			//push stack
			while (t.getAttached() != null && !isUpdated.get(t.getAttached().getId())) {
				if (tm.get(t.getAttached()) != null) {
					stack[stackSize] = t.getAttached();
					t = tm.get(stack[stackSize ++]);
				} else {
					t.setAttached(null);
				}
			}
			
			if (t.getAttached() == null) {
				stackSize --;
				if (!isUpdated.get(stack[stackSize].getId()))
				{
					t.getWorldTransform().set(t.getLocalTransform());
					isUpdated.set(stack[stackSize].getId());
				}
			}
			// pop stack
			while (stackSize > 0) {
				stackSize --;
				isUpdated.set(stack[stackSize].getId());
				t = tm.get(stack[stackSize]);
				t.getWorldTransform().set(t.getLocalTransform());
				t.getWorldTransform().combineFromParent(tm.get(t.getAttached()).getWorldTransform());
				
				//set visible
				//if (gm.has(stack[stackSize]) && gm.has(t.getAttached()))
				//	gm.get(stack[stackSize]).setVisible(gm.get(t.getAttached()).isVisible());
				
				// bone transform
				if (t.getJoint() != -1)	{
					SkeletonComponent skl = t.getAttached().getComponent(SkeletonComponent.class);
					
					Matrix4f boneMat = 
							skl.getCurrentPoseMatrices()[t.getJoint()].mult(
									skl.getBaseSkeleton().getJoints().get(t.getJoint()).base);
					Transform boneTransform = new Transform(boneMat.toTranslationVector(), boneMat.toRotationQuat(), boneMat.toScaleVector());
					boneTransform.combineFromParent(t.getWorldTransform());
					t.getWorldTransform().set(boneTransform);
				}
			}
		}
	}
}
