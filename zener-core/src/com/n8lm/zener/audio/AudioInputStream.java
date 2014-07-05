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

import java.io.IOException;

/**
 * The description of an input stream that supplied audio data suitable for
 * use in OpenAL buffers
 *
 * @author kevin
 */
interface AudioInputStream {
	/**
	 * Get the number of channels used by the audio 
	 * 
	 * @return The number of channels used by the audio
	 */
	public int getChannels();
	
	/**
	 * The play back rate described in the underling audio file
	 * 
	 * @return The playback rate 
	 */
	public int getRate();

	/**
	 * Read a single byte from the stream
	 * 
	 * @return The single byte read
	 * @throws IOException Indicates a failure to read the underlying media
	 * @see java.io.InputStream#read()
	 */
	public int read() throws IOException;

	/**
	 * Read up to data.length bytes from the stream
	 * 
	 * @param data The array to read into
	 * @return The number of bytes read or -1 to indicate no more bytes are available
	 * @throws IOException Indicates a failure to read the underlying media
	 * @see java.io.InputStream#read(byte[])
	 */
	public int read(byte[] data) throws IOException;

	/**
	 * Read up to len bytes from the stream
	 * 
	 * @param data The array to read into
	 * @param ofs The offset into the array at which to start writing
	 * @param len The maximum number of bytes to read
	 * @return The number of bytes read or -1 to indicate no more bytes are available
	 * @throws IOException Indicates a failure to read the underlying media
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	public int read(byte[] data, int ofs, int len) throws IOException;

	/**
	 * Check if the stream is at the end, i.e. end of file or URL 
	 * 
	 * @return True if the stream has no more data available
	 */
	public boolean atEnd();
	
	/**
	 * Close the stream
	 * 
	 * @see java.io.InputStream#close()
	 * @throws IOException Indicates a failure to access the resource
	 */
	public void close() throws IOException;
}
