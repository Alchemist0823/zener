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

package com.n8lm.zener.audio;

import java.nio.ByteBuffer;

/**
 * Data describing the sounds in a OGG file
 * 
 * @author Kevin Glass
 */
public class OggData {
	/** The data that has been read from the OGG file */
	public ByteBuffer data;
	/** The sampling rate */
	public int rate;
	/** The number of channels in the sound file */
	public int channels;
}
