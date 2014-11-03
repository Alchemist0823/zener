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

import java.io.InputStream;
import java.net.URL;

/**
 * A location from which resources can be loaded
 * 
 * @author kevin
 */
public interface ResourceLocation {

	/**
	 * Get a resource as an input stream
	 * 
	 * @param ref The reference to the resource to retrieve
	 * @return A stream from which the resource can be read or
	 * null if the resource can't be found in this location
	 */
	public InputStream getResourceAsStream(String ref);

	/**
	 * Get a resource as a URL
	 * 
	 * @param ref The reference to the resource to retrieve
	 * @return A URL from which the resource can be read
	 */
	public URL getResource(String ref);
}