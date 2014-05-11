package com.n8lm.zener.components;

import com.artemis.Component;
import com.n8lm.zener.collision.Collidable;

public class CollidableComponent extends Component {

	private Collidable collidable;
	private boolean active;
	
	public CollidableComponent(Collidable collidable) {
		this(collidable, true);
	}

	public CollidableComponent(Collidable collidable, boolean active) {
		this.collidable = collidable;
		this.active = active;
	}

	public Collidable getCollidable() {
		return collidable;
	}

	public void setCollidable(Collidable collidable) {
		this.collidable = collidable;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
