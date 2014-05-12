package com.n8lm.zener.log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.n8lm.zener.nifty.ConsoleScreenController;

import de.lessvoid.nifty.controls.Console;
import de.lessvoid.nifty.tools.Color;

public class NiftyLogHandler extends Handler {
	
	private Console console;

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void publish(LogRecord record) {
		// ensure that this log record should be logged by this Handler
		if (!isLoggable(record))
			return;
		
		Color color = new Color("#fff");
		
		if (record.getLevel() == Level.WARNING)
			color.fromString("#ff0");
		else if (record.getLevel() == Level.SEVERE)
			color.fromString("#f00");
		else
			color.fromString("#fff");
		
		console.output(this.getFormatter().format(record), color);
	}

	public void setController(Console console) {
		this.console = console;
	}

}
