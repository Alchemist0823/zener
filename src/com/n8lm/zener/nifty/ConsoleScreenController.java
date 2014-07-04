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

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.n8lm.zener.log.LogUtil;

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Console;
import de.lessvoid.nifty.controls.ConsoleExecuteCommandEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyStandardInputEvent;
import de.lessvoid.nifty.input.mapping.DefaultInputMapping;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;

public class ConsoleScreenController implements ScreenController, KeyInputHandler {

	protected Nifty nifty;
	protected Screen screen;
	
	protected Console console;
	protected Element consolePopup;
	protected boolean consoleVisible = false;
	private boolean allowConsoleToggle = true;
	private boolean firstConsoleShow = true;
	
	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
		screen.addKeyboardInputHandler(new DefaultInputMapping(), this);

	    consolePopup = nifty.createPopup("consolePopup");

	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyEvent(NiftyInputEvent inputEvent) {
		if (inputEvent == NiftyStandardInputEvent.ConsoleToggle) {
			toggleConsole();
			return true;
		} else {
			return false;
		}
	}

	private void toggleConsole() {
		if (allowConsoleToggle) {
			allowConsoleToggle = false;
			if (consoleVisible) {
				closeConsole();
			} else {
				openConsole();
			}
		}
	}

	private void openConsole() {
		nifty.showPopup(screen, consolePopup.getId(),
				consolePopup.findElementById("console#textInput"));
		screen.processAddAndRemoveLayerElements();

		if (firstConsoleShow) {
			firstConsoleShow = false;
			console = screen.findNiftyControl("console", Console.class);
			LogUtil.setupNiftyLogConsole(console);
		}

		consoleVisible = true;
		allowConsoleToggle = true;
	}

	private void closeConsole() {
		nifty.closePopup(consolePopup.getId(), new EndNotify() {
			@Override
			public void perform() {
				consoleVisible = false;
				allowConsoleToggle = true;
			}
		});
	}

	@NiftyEventSubscriber(id = "console")
	public void onConsoleCommand(final String id,
			final ConsoleExecuteCommandEvent command) {
		console = screen.findNiftyControl("console", Console.class);
		console.output("your input was: " + command.getCommandLine() + " ["
				+ command.getArgumentCount() + " parameter(s)]");
		/*if ("exit".equals(command.getCommand())) {
			back();
		}*/
	}
}
