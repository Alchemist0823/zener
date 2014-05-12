package com.n8lm.zener.log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ShortLogFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		return record.getLevel() + ": " + record.getMessage() + "\n";
	}

}
