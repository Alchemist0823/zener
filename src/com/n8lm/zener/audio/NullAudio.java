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

/**
 * A null implementation used to provide an object reference when sound
 * has failed.
 * 
 * @author kevin
 */
public class NullAudio implements Audio {
	/**
	 * @see com.n8lm.zener.audio.Audio#getBufferID()
	 */
	public int getBufferID() {
		return 0;
	}

	/**
	 * @see com.n8lm.zener.audio.Audio#getPosition()
	 */
	public float getPosition() {
		return 0;
	}

	/**
	 * @see com.n8lm.zener.audio.Audio#isPlaying()
	 */
	public boolean isPlaying() {
		return false;
	}

	/**
	 * @see com.n8lm.zener.audio.Audio#playAsMusic(float, float, boolean)
	 */
	public int playAsMusic(float pitch, float gain, boolean loop) {
		return 0;
	}

	/**
	 * @see com.n8lm.zener.audio.Audio#playAsSoundEffect(float, float, boolean)
	 */
	public int playAsSoundEffect(float pitch, float gain, boolean loop) {
		return 0;
	}

	/**
	 * @see com.n8lm.zener.audio.Audio#playAsSoundEffect(float, float, boolean, float, float, float)
	 */
	public int playAsSoundEffect(float pitch, float gain, boolean loop,
			float x, float y, float z) {
		return 0;
	}

	/**
	 * @see com.n8lm.zener.audio.Audio#setPosition(float)
	 */
	public boolean setPosition(float position) {
		return false;
	}

	/**
	 * @see com.n8lm.zener.audio.Audio#stop()
	 */
	public void stop() {
	}

}
