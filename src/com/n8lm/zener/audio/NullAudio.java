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
