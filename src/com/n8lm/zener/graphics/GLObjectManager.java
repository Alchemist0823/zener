package com.n8lm.zener.graphics;

import java.util.ArrayList;
import java.util.List;

public class GLObjectManager {
	public static List<GLObject> globjects;
	
	static {
		globjects = new ArrayList<GLObject>();
	}
	
	public static void register(GLObject glObject) {
		globjects.add(glObject);
	}

}
