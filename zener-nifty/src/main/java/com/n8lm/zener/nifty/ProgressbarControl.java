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

package com.n8lm.zener.nifty;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.Parameters;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;

public class ProgressbarControl implements Controller {
	private Element progressBarElement;
	private Element progressTextElement;

	public void onStartScreen() {
	}

	public void onFocus(final boolean getFocus) {
	}

	public boolean inputEvent(final NiftyInputEvent inputEvent) {
		return false;
	}

	public void setProgress(final float progressValue) {
		float progress = progressValue;
		if (progress < 0.0f) {
			progress = 0.0f;
		} else if (progress > 1.0f) {
			progress = 1.0f;
		}
		final int MIN_WIDTH = 32;
		int pixelWidth = (int) (MIN_WIDTH + (progressBarElement.getParent()
				.getWidth() - MIN_WIDTH) * progress);
		progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
		progressBarElement.getParent().layoutElements();

		String progressText = String.format("%3.0f%%", progress * 100);
		progressTextElement.getRenderer(TextRenderer.class).setText(
				progressText);
	}

	@Override
	public void bind(Nifty nifty, Screen screen, Element element,
			Parameters parameter) {
		progressBarElement = element.findElementById("#progress");
		progressTextElement = element.findElementById("#progress-text");

	}

	@Override
	public void init(Parameters parameter) {

	}

}