package com.n8lm.zener.log;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;
import java.util.logging.SimpleFormatter;

import com.n8lm.zener.nifty.ConsoleScreenController;

import de.lessvoid.nifty.controls.Console;

public class LogUtil {
	
	private static MemoryHandler memoryLogHandler;
	private static NiftyLogHandler niftyLogHandler;
	
	public static void setup(boolean isNiftyConsoleLog) {

	    Logger logger = Logger.getLogger("com.n8lm");
	    Logger globalLogger = Logger.getLogger("");
	    //logger.setLevel(Level.WARNING);
	   
	    //globalLogger.removeHandler(globalLogger.getHandlers()[0]);
	    
	    globalLogger.getHandlers()[0].setFormatter(new ShortLogFormatter());
	    
		try {
			FileHandler fileTxt = new FileHandler("Logging.txt");
		    SimpleFormatter formatterTxt = new SimpleFormatter();
		    fileTxt.setFormatter(formatterTxt);
		    logger.addHandler(fileTxt);
		    
		    //for (Handler handle : logger.getHandlers()
		    
		    if (isNiftyConsoleLog) {
			    niftyLogHandler = new NiftyLogHandler();
			    niftyLogHandler.setFormatter(new ShortLogFormatter());
			    
			    memoryLogHandler = new MemoryHandler(niftyLogHandler, 10000, Level.OFF);
			    logger.addHandler(memoryLogHandler);
		    }
		    
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setupNiftyLogConsole(Console console) {
		niftyLogHandler.setController(console);
		memoryLogHandler.push();
		memoryLogHandler.setPushLevel(Level.ALL);
	}

}
