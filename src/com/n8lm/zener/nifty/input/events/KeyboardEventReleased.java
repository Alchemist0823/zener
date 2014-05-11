package com.n8lm.zener.nifty.input.events;

import de.lessvoid.nifty.NiftyInputConsumer;
import com.n8lm.zener.input.InputListener;

/**
 * This class stores the data generated when releasing a key.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class KeyboardEventReleased extends AbstractKeyboardEvent {
  /**
   * Create this new event key released event.
   *
   * @param keyId       the ID of the key that was used
   * @param keyChar     the character assigned to the used key
   * @param shiftDown   {@code true} in case shift is pressed down at the same time
   * @param controlDown {@code true} in case control is pressed down at the same time
   */
  public KeyboardEventReleased(
      final int keyId, final char keyChar, final boolean shiftDown, final boolean controlDown) {
    super(keyId, keyChar, false, shiftDown, controlDown);
  }

  /**
   * Send the event to a Nifty input consumer.
   */
  @Override
  public boolean sendToNifty( final NiftyInputConsumer consumer) {
    return consumer.processKeyboardEvent(this);
  }

  /**
   * Send the event to a Slick input listener.
   */
  @Override
  public boolean sendToZener( final InputListener listener) {
    listener.keyReleased(getKey(), getCharacter());
    return true;
  }

}
