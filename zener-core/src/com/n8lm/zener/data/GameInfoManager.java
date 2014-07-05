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

package com.n8lm.zener.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.n8lm.zener.assets.PNGLoader;

public class GameInfoManager {


	private final static Logger LOGGER = Logger.getLogger(GameInfoManager.class
		      .getName());
	
	static private GameInfoManager instance;
	
	private Map<Class<? extends UniqueGameInfo>, UniqueGameInfo> gamedata;
	
	static public GameInfoManager getInstance() {
		return instance;
	}
	
	public GameInfoManager() {
		instance = this;
		gamedata = new HashMap<Class<? extends UniqueGameInfo>, UniqueGameInfo>();
	}

    public <T extends UniqueGameInfo> T setGameInfo(T data) {
    	gamedata.put(data.getClass(), data);
    	data.setGameInfoManager(this);
    	return data;
    }
    
    public <T extends UniqueGameInfo> T getGameInfo(Class<T> gamedataType) {
        return gamedataType.cast(gamedata.get(gamedataType));
    }
    
    public void init() {
    	for (UniqueGameInfo data : gamedata.values()) {
    		data.reset();
    	}
    }
    /*
    public void saveGame() {
		try {
			OutputStream output = new FileOutputStream("save.data");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
	    	for (UniqueGameInfo data : gamedata.values()) {
	    		writer.write(data.getClass().getName() + "{");
	    		writer.flush();
				data.write(output);
	    	}
	    	output.close();
	    	LOGGER.info("game saved successfully");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }*/
    

    public OutputStream getSaveGameStream(String filename) {
		OutputStream output = null;
		try {
			output = new FileOutputStream("save/" + filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return output;
    }
    

    public InputStream getLoadGameStream(String filename) {
    	InputStream input = null;
		try {
			input = new FileInputStream("save/" + filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return input;
    }
    
    /*public void loadGame() {
		try {
			FileInputStream input = new FileInputStream("save.data");
			//DataInputStream reader = new DataInputStream(input);
			String str = "";
			int ch;
			while ((ch = input.read()) != -1) {
	    		
				char c = (char) ch;
				
				if (c != '{') {
					if (!Character.isSpaceChar(ch))
						str += c;
					else
						str = "";
				} else {
					
	    			@SuppressWarnings("unchecked")
					Class<? extends UniqueGameInfo> cls = (Class<? extends UniqueGameInfo>) Class.forName(str);
	    			getGameInfo(cls).read(input);
	    			
	    			str = "";
				}
	    		
	    		LOGGER.info(str);
	    	}
	    	input.close();
	    	LOGGER.info("game loaded successfully");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }*/

}
