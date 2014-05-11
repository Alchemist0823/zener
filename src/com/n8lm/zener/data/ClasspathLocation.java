package com.n8lm.zener.data;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * A resource location that searches the classpath
 * 
 * @author kevin
 */
public class ClasspathLocation implements ResourceLocation {
	
	private String root;

	public ClasspathLocation(String root) {
		this.root = root.replace('\\', '/');
	}
	
	/**
	 * @see org.newdawn.slick.util.ResourceLocation#getResource(java.lang.String)
	 */
	public URL getResource(String ref) {
		String cpRef = root + ref.replace('\\', '/');
		return ResourceManager.class.getClassLoader().getResource(cpRef);
	}

	/**
	 * @see org.newdawn.slick.util.ResourceLocation#getResourceAsStream(java.lang.String)
	 */
	public InputStream getResourceAsStream(String ref) {
		String cpRef = root + ref.replace('\\', '/');
		return ResourceManager.class.getClassLoader().getResourceAsStream(cpRef);	
	}

}