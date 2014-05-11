package com.n8lm.zener.collision;


public interface Collidable {
	public int collideWith(Collidable other, CollisionResults results);
}
