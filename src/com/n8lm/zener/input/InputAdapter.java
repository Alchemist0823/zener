package com.n8lm.zener.input;

/**
 * An implement implementation of the InputListener interface
 *
 * @author kevin
 */
public class InputAdapter implements InputListener {
	/** A flag to indicate if we're accepting input here */
	private boolean acceptingInput = true;
	
	/**
	 * @see com.n8lm.zener.input.InputListener#controllerButtonPressed(int, int)
	 */
	public void controllerButtonPressed(int controller, int button) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#controllerButtonReleased(int, int)
	 */
	public void controllerButtonReleased(int controller, int button) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#controllerDownPressed(int)
	 */
	public void controllerDownPressed(int controller) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#controllerDownReleased(int)
	 */
	public void controllerDownReleased(int controller) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#controllerLeftPressed(int)
	 */
	public void controllerLeftPressed(int controller) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#controllerLeftReleased(int)
	 */
	public void controllerLeftReleased(int controller) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#controllerRightPressed(int)
	 */
	public void controllerRightPressed(int controller) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#controllerRightReleased(int)
	 */
	public void controllerRightReleased(int controller) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#controllerUpPressed(int)
	 */
	public void controllerUpPressed(int controller) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#controllerUpReleased(int)
	 */
	public void controllerUpReleased(int controller) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#inputEnded()
	 */
	public void inputEnded() {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#isAcceptingInput()
	 */
	public boolean isAcceptingInput() {
		return acceptingInput;
	}

	/**
	 * Indicate if we should be accepting input of any sort
	 * 
	 * @param acceptingInput True if we should accept input
	 */
	public void setAcceptingInput(boolean acceptingInput) {
		this.acceptingInput = acceptingInput;
	}
	
	/**
	 * @see com.n8lm.zener.input.InputListener#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#mouseMoved(int, int, int, int)
	 */
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#mousePressed(int, int, int)
	 */
	public void mousePressed(int button, int x, int y) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#mouseReleased(int, int, int)
	 */
	public void mouseReleased(int button, int x, int y) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#mouseWheelMoved(int)
	 */
	public void mouseWheelMoved(int change) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#setInput(org.newdawn.slick.Input)
	 */
	public void setInput(Input input) {
	}

	/**
	 * @see com.n8lm.zener.input.InputListener#mouseClicked(int, int, int, int)
	 */
	public void mouseClicked(int button, int x, int y, int clickCount) {
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
	}

	/**
	 * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
	 */
	public void inputStarted() {
		
	}
}