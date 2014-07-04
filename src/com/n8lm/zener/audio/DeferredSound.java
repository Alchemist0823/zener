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
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A sound implementation that can load the actual sound file at a later 
 * point.
 *
 * @author kevin
 */
public class DeferredSound extends AudioImpl /*implements DeferredResource*/ {

	private final static Logger LOGGER = Logger.getLogger(DeferredSound.class
		      .getName());
	
	
	/** Indicate a OGG to be loaded */
	public static final int OGG = 1;
	/** Indicate a WAV to be loaded */
	public static final int WAV = 2;
	/** Indicate a MOD/XM to be loaded */
	public static final int MOD = 3;
	/** Indicate a AIF to be loaded */
	public static final int AIF = 4;
	
	/** The type of sound to be loader */
	private int type;
	/** The location of the sound this proxy wraps */
	private String ref;
	/** The loaded sound if it's already been brought up */
	private Audio target;
	/** The input stream to load the sound this proxy wraps from (can be null) */
	private InputStream in;
	
	/**
	 * Create a new sound on request to load
	 * 
	 * @param ref The location of the sound to load
	 * @param type The type of sound to load
	 * @param in The input stream to load from
	 */
	public DeferredSound(String ref, InputStream in, int type) {
		this.ref = ref;
		this.type = type;
		
		// nasty hack to detect when we're loading from a stream
		if (ref.equals(in.toString())) {
			this.in = in;
		}
		
		//LoadingList.get().add(this);
	}

	/**
	 * Check if the target has already been loaded
	 */
	private void checkTarget() {
		if (target == null) {
			throw new RuntimeException("Attempt to use deferred sound before loading");
		}
	}
	
	/**
	 * @see com.n8lm.zener.loading.DeferredResource#load()
	 */
	public void load() throws IOException {
		boolean before = SoundStore.get().isDeferredLoading();
		SoundStore.get().setDeferredLoading(false);
		if (in != null) {
			switch (type) {
			case OGG:
				target = SoundStore.get().getOgg(in);
				break;
			case WAV:
				target = SoundStore.get().getWAV(in);
				break;
			case MOD:
				target = SoundStore.get().getMOD(in);
				break;
			case AIF:
				target = SoundStore.get().getAIF(in);
				break;
			default:
				LOGGER.log(Level.SEVERE, "Unrecognised sound type: "+type);
				break;
			}
		} else {
			switch (type) {
			case OGG:
				target = SoundStore.get().getOgg(ref);
				break;
			case WAV:
				target = SoundStore.get().getWAV(ref);
				break;
			case MOD:
				target = SoundStore.get().getMOD(ref);
				break;
			case AIF:
				target = SoundStore.get().getAIF(ref);
				break;
			default:
				LOGGER.log(Level.SEVERE, "Unrecognised sound type: "+type);
				break;
			}
		}
		SoundStore.get().setDeferredLoading(before);
	}

	/**
	 * @see com.n8lm.zener.audio.AudioImpl#isPlaying()
	 */
	public boolean isPlaying() {
		checkTarget();
		
		return target.isPlaying();
	}

	/**
	 * @see com.n8lm.zener.audio.AudioImpl#playAsMusic(float, float, boolean)
	 */
	public int playAsMusic(float pitch, float gain, boolean loop) {
		checkTarget();
		return target.playAsMusic(pitch, gain, loop);
	}

	/**
	 * @see com.n8lm.zener.audio.AudioImpl#playAsSoundEffect(float, float, boolean)
	 */
	public int playAsSoundEffect(float pitch, float gain, boolean loop) {
		checkTarget();
		return target.playAsSoundEffect(pitch, gain, loop);
	}

	/**
	 * Play this sound as a sound effect
	 * 
	 * @param pitch The pitch of the play back
	 * @param gain The gain of the play back
	 * @param loop True if we should loop
	 * @param x The x position of the sound
	 * @param y The y position of the sound
	 * @param z The z position of the sound
	 */
	public int playAsSoundEffect(float pitch, float gain, boolean loop, float x, float y, float z) {
		checkTarget();
		return target.playAsSoundEffect(pitch, gain, loop, x, y, z);
	}
	
	/**
	 * @see com.n8lm.zener.audio.AudioImpl#stop()
	 */
	public void stop() {
		checkTarget();
		target.stop();
	}

	/**
	 * @see com.n8lm.zener.loading.DeferredResource#getDescription()
	 */
	public String getDescription() {
		return ref;
	}

}
