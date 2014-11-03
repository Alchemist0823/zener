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

package com.n8lm.zener.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;
import java.util.logging.SimpleFormatter;

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
