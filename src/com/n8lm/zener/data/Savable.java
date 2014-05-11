package com.n8lm.zener.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Savable {
	public void read(InputStream input) throws IOException;
	
	public void write(OutputStream output) throws IOException;
}
