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
	 * @see com.n8lm.zener..util.ResourceLocation#getResource(java.lang.String)
	 */
	public URL getResource(String ref) {
		String cpRef = root + ref.replace('\\', '/');
		return ResourceManager.class.getClassLoader().getResource(cpRef);
	}

	/**
	 * @see com.n8lm.zener..util.ResourceLocation#getResourceAsStream(java.lang.String)
	 */
	public InputStream getResourceAsStream(String ref) {
		String cpRef = root + ref.replace('\\', '/');
		return ResourceManager.class.getClassLoader().getResourceAsStream(cpRef);	
	}

}