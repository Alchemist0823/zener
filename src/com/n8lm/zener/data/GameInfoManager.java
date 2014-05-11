package com.n8lm.zener.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.util.Log;

public class GameInfoManager {

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
	    	Log.info("game saved successfully");
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
	    		
	    		Log.info(str);
	    	}
	    	input.close();
	    	Log.info("game loaded successfully");
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
