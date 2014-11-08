/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.n8lm.zener.general;

import java.util.List;

import com.artemis.utils.ArrayBag;
import com.artemis.utils.Bag;
import com.n8lm.zener.animation.Joint;
import com.n8lm.zener.animation.SkeletonComponent;
import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Transform;
import com.n8lm.zener.math.Vector3f;
import com.artemis.Component;
import com.artemis.Entity;
import com.n8lm.zener.utils.ZenerException;

public class TransformComponent extends Component {
	
	private Entity parent;
    private Bag<Entity> children;
	private int joint;
	final private Transform worldTransform;
	final private Transform localTransform;
	/*
	public TransformComponent(Vector3f trans, Quaternion rot, Vector3f scale) {
		this(null, trans, rot, scale);
	}

	public TransformComponent(Transform transform) {
		this(null, transform);
	}

	public TransformComponent(Entity parent, Transform transform) {
		this(parent, null, transform);
	}
	
	public TransformComponent(Entity parent, Vector3f trans, Quaternion rot, Vector3f scale) {
		this(parent, null, trans, rot, scale);
	}*/
    public TransformComponent(){
        this(Transform.UNIT);
    }
	
	public TransformComponent(Transform transform) {
        joint = -1;
        parent = null;
        worldTransform = new Transform();
        localTransform = new Transform(transform);
        children = new ArrayBag<>(4);
	}
	
	public TransformComponent(Vector3f trans, Quaternion rot, Vector3f scale) {
		joint = -1;
        parent = null;
		worldTransform = new Transform();
		localTransform = new Transform(trans, rot, scale);
        children = new ArrayBag<>(4);
	}
	
	public Transform getWorldTransform() {
		return worldTransform;
	}

	public Transform getLocalTransform() {
		return localTransform;
	}

	public Entity getParent() {
		return parent;
	}

    public Bag<Entity> getChildren() {
        return children;
    }

    public int getJoint() {
        return joint;
    }

    void setParent(Entity parent) {
		if (parent != null && parent.getComponent(TransformComponent.class) == null)
			throw new IllegalArgumentException("parent entity has no Tranform component");
		this.parent = parent;
	}
	

	void setParent(Entity parent, String bone) {
		if (bone != null)
			if (parent != null && parent.getComponent(SkeletonComponent.class) == null)
				throw new IllegalArgumentException("parent entity has no Skeleton component");
			else {
				List<Joint> joints = parent.getComponent(SkeletonComponent.class).getBaseSkeleton().getJoints();
				int i;
				for (i = 0; i < joints.size(); i ++)
					if (joints.get(i).name.equals(bone)) {
						joint = i;
						break;
					}
				if (i == joints.size())	{
					joint = -1;
					throw new IllegalArgumentException("parent entity has no this bone in Skeleton component");
				}
			}
		setParent(parent);
	}

    void removeChild(Entity child) {
        children.remove(child);
    }

    void addChild(Entity child) {
        if (!children.contains(child))
            children.add(child);
    }
}
