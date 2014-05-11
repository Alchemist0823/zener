package com.n8lm.zener.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class GLShader extends GLObject{

    public static HashMap<String, GLShader> shaders = new HashMap<String, GLShader>();
    
    public static enum ShaderType {
        /**
         * Control fragment rasterization. (e.g color of pixel).
         */
        Fragment,

        /**
         * Control vertex processing. (e.g transform of model to clip space)
         */
        Vertex,

        /**
         * Control geometry assembly. (e.g compile a triangle list from input data)
         */
        Geometry;
    }
    
    private ShaderType type;

    //private int geometricShader = -1;

	public GLShader(ShaderType type) {
		super();
		this.type = type;
	}
	
    /**
     * Create Vertex shader and compile the code contained in ByteBuffer b.
     * Attached the compiled shader to the program.
     */
    public boolean compile(ByteBuffer b) {
    	
    	if (type == ShaderType.Vertex)
    		id = glCreateShader(GL_VERTEX_SHADER);
    	else if (type == ShaderType.Fragment)
    		id = glCreateShader(GL_FRAGMENT_SHADER);
    	
        glShaderSource(id, b);
        glCompileShader(id);

        return (glGetShaderi(id, GL_COMPILE_STATUS) == GL_TRUE);
    }

    /**
     * Link the Vertex and Fragment shaders
     * A printout to the console lists any errors with the linkage.
     */
    public void printInformationLog() {
    	int loglength = glGetShaderi(id, GL_INFO_LOG_LENGTH);
        System.out.println(glGetShaderInfoLog(id, loglength));
    }

	@Override
	public void deleteObject() {
        glDeleteShader(getID());
	}
    
}

