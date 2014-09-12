package com.n8lm.zener.nifty.audio;


import com.n8lm.zener.audio.Sound;
import de.lessvoid.nifty.sound.SoundSystem;
import de.lessvoid.nifty.spi.sound.SoundHandle;

/**
 * A Slick handle to sound.
 *
 * @author void
 */
public class OpenALSoundHandle implements SoundHandle {

  /**
   * openal sound.
   */
  private final Sound slickSound;

  /**
   * SoundSystem.
   */
  private final SoundSystem soundSystem;

  /**
   * Construct a new Sound.
   *
   * @param newSoundSystem the SoundSystem we're connected too
   * @param newSlickSound  the slickSound to set
   */
  public OpenALSoundHandle(final SoundSystem newSoundSystem, final Sound newSlickSound) {
    this.soundSystem = newSoundSystem;
    this.slickSound = newSlickSound;
  }

  @Override
  public void play() {
    slickSound.play(1.0f, soundSystem.getSoundVolume());
  }

  @Override
  public void stop() {
    slickSound.stop();
  }

  @Override
  public void setVolume(final float volume) {
    // can't change volume of sound that is currently playing with openal
  }

  @Override
  public float getVolume() {
    return soundSystem.getSoundVolume();
  }

  @Override
  public boolean isPlaying() {
    return slickSound.playing();
  }

  @Override
  public void dispose() {

  }
}
