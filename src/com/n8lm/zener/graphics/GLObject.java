package com.n8lm.zener.graphics;

public abstract class GLObject {

	public static final int INVALID_ID = -1;
	
	protected int id = INVALID_ID;
	
	protected boolean updateNeeded = true;
	
	protected GLObject() {
		GLObjectManager.register(this);
	}
	
	protected GLObject(int id) {
		super();
		this.id = id;
	}
	
	public boolean isCreatedGL() {
		if (id == INVALID_ID)
			return false;
		else
			return true;
	}
	
	public int getID() {
		return id;
	}
	
	protected void setID(int id) {
		this.id = id;
	}
	
	public abstract void deleteObject();

}
