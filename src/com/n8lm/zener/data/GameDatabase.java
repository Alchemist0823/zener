package com.n8lm.zener.data;

import java.io.IOException;
import java.io.InputStream;

public abstract class GameDatabase {
	public abstract void load(InputStream input) throws IOException;
}
