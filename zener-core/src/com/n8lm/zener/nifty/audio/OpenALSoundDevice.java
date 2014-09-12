package com.n8lm.zener.nifty.audio;

import com.n8lm.zener.audio.Sound;
import com.n8lm.zener.data.ResourceManager;
import de.lessvoid.nifty.sound.SoundSystem;
import de.lessvoid.nifty.spi.sound.SoundDevice;
import de.lessvoid.nifty.spi.sound.SoundHandle;
import de.lessvoid.nifty.tools.resourceloader.NiftyResourceLoader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Logger;

/**
 * Slick Implementation of the SoundLoader.
 *
 * @author void
 */
public class OpenALSoundDevice implements SoundDevice {

  /**
   * The logger.
   */
  private static final Logger log = Logger.getLogger(SoundSystem.class.getName());

  private NiftyResourceLoader resourceLoader;

  @Override
  public void setResourceLoader(@Nonnull final NiftyResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  /**
   * Load a sound.
   *
   * @param soundSystem soundSystem
   * @param filename    filename of sound
   * @return handle to sound
   */
  @Override
  @Nullable
  public SoundHandle loadSound(@Nonnull final SoundSystem soundSystem, @Nonnull final String filename) {
    try {
      return new OpenALSoundHandle(soundSystem, new Sound(filename, ResourceManager.getInstance()));
    } catch (Exception e) {
      log.warning("loading of '" + filename + "' failed" + e);
    }
    return null;
  }

  /**
   * Load a music piece.
   *
   * @param soundSystem soundSystem
   * @param filename    file to load
   * @return the music piece
   */
  @Override
  public SoundHandle loadMusic(@Nonnull final SoundSystem soundSystem, @Nonnull final String filename) {
    try {
      return new OpenALMusicHandle(soundSystem, new com.n8lm.zener.audio.Music(filename, true, ResourceManager.getInstance()));
    } catch (Exception e) {
      log.warning("loading of '" + filename + "' failed" + e);
    }
    return null;
  }

  @Override
  public void update(final int delta) {
    //com.n8lm.zener.audio.Music.poll(delta);
  }
}
