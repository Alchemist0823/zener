package com.n8lm.zener.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import com.n8lm.zener.math.*;

public class GLProgram extends GLObject{

    public static GLProgram current = null;
    /*
    private String vertexShaderName = null;
    private int vertexShaderIndex = -1;
    private String fragmentShaderName = null;
    private int fragmentShaderIndex = -1;*/

    public FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    private Map<String, UniformVariable> uniforms;
    private int textureGroup;
    /**
	* On constructing the Shader object create a new shader Program.
	*/
    public GLProgram() {
    	super(glCreateProgram());
        uniforms = new HashMap<String, UniformVariable>();
        textureGroup = 0;
        updateNeeded = true;
    }
    

    /**
     * This adds and compiles the shader program contain in the file to the current shader.
     *
     * @param filename Relative file path of the shader program.
     *
     */
    public void addShader(String name, String content) {
        ByteBuffer bb = null;
        GLShader shader;
        if (GLShader.shaders.containsKey(name)) {
            shader = GLShader.shaders.get(name);
            GL20.glAttachShader(id, shader.getID());
            return;
        } else {
            try {
                String[] groups = name.split("\\.");

                String type = groups[groups.length - 1];

                bb = ByteBuffer.allocateDirect(content.length());
                bb.put(content.getBytes());
                bb.flip();

                if (type.equals("vert") || type.equals("frag")) {
                
	                if (type.equals("vert")) {
	                	shader = new GLShader(GLShader.ShaderType.Vertex);
	                } else if (type.equals("frag")) {
	                	shader = new GLShader(GLShader.ShaderType.Fragment);
	                } else 
	                	throw new IOException();
	                
                    if (shader.compile(bb)) {
                        GLShader.shaders.put(name, shader);
                        GL20.glAttachShader(id, shader.getID());
                        
                        //createUniformVariable(content);
                        
                        System.out.println(name + ":" + shader.getID() + " compiled");
                    } else {
                        System.out.println("******** start shader info log*********");
                        shader.printInformationLog();
                        System.out.println("********end shader info log*********");
                    }
                }
            } catch (Exception ex) {
                System.out.println("error:" + name);
            }
        }
    }

    // Never used
    private VarType getVarTypeFromDef(String type, String varname) {
    	if (type.equals("mat4")) {
        	if (varname.charAt(varname.length() - 1) == ']')
        		return VarType.Matrix4Array;
        	else
        		return VarType.Matrix4;
        } else if (type.equals("mat3")) {
        	if (varname.charAt(varname.length() - 1) == ']')
        		return VarType.Matrix3Array;
        	else
        		return VarType.Matrix3;
        } else if (type.equals("vec4")) {
        	if (varname.charAt(varname.length() - 1) == ']')
        		return VarType.Vector4Array;
        	else
        		return VarType.Vector4f;
        } else if (type.equals("vec3")) {
        	if (varname.charAt(varname.length() - 1) == ']')
        		return VarType.Vector3Array;
        	else
        		return VarType.Vector3f;
        } else if (type.equals("vec2")) {
        	if (varname.charAt(varname.length() - 1) == ']')
        		return VarType.Vector2Array;
        	else
        		return VarType.Vector2f;
        } else if (type.equals("Sample2D")) {
        	if (varname.charAt(varname.length() - 1) == ']')
        		return VarType.TextureArray;
        	else
        		return VarType.Texture2D;
        } else if (type.equals("float")) {
        	if (varname.charAt(varname.length() - 1) == ']')
        		return VarType.FloatArray;
        	else
        		return VarType.Float;
        }
        else
        	throw new IllegalArgumentException();
    }
    
    
    // Never used
    private void createUniformVariable(String content) {
    	content.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)","");
		Pattern structPattern = Pattern.compile("struct\\s+\\w+\\s+\\{\\n(\\s*\\w+\\s+\\w+(\\[\\d+\\])?;\\s*\\n){0,}\\}");
		Pattern varPattern = Pattern.compile("(mat4|vec4|mat3|vec3|vec2|float|int|sampler2D)\\s+\\w+(\\[\\d+\\])?");
		Matcher structMatcher = structPattern.matcher(content);

		Map<String, List<String>> structs = new HashMap<String, List<String>>();
		
        while (structMatcher.find()) {
        	String struct = structMatcher.group();
        	Matcher varMatcher = varPattern.matcher(struct);
            while (varMatcher.find()) {
            	String def = varMatcher.group();
            	String[] names = def.split(" +");
            	VarType type = getVarTypeFromDef(names[0], names[1]);
            	String varname = names[1].substring(0, names[1].indexOf(']'));
            }
        }
		
	}


	public void linkProgram() {

        System.out.println(getVersion());

        glLinkProgram(id);

        int status = glGetProgrami(id, GL_LINK_STATUS);

        System.out.println("id link status " + status);

        if (status == 0) {
            System.out.println("Link unsuccessful!");
            System.out.println("******** start Link info log*********");
        	int loglength = glGetProgrami(id, GL_INFO_LOG_LENGTH);
            System.out.println(glGetProgramInfoLog(id, loglength));
            System.out.println("********end Link info log*********");
        }
    }
    

    /**
     * Make the current shader program current.
     * The method name "bind" is used to use similar terminology to other OpenGL objects.
     */
    public void bind() {
    	if(current != this) {
    		glUseProgram(id);
    		updateNeeded = true;
    		GLProgram.current = this;
    	}
    	else
    		updateNeeded = false;
    }

    /**
     * Disable the shader program.
     *
     */
    public void unbind() {
        glUseProgram(0);
        GLProgram.current = null;
		updateNeeded = true;
    }
    

    /**
     * Define the uniform variable name to be used in the shader.
     * @param names
     */
    public void initUniforms(String[] names) {
    	for (String name : names) {
    		UniformVariable uniform = new UniformVariable(name);
    		uniform.setLocation(glGetUniformLocation(id, name));
    		//Log.info(name);
    		uniforms.put(name, uniform);
    	}
    }
    
    private boolean hasUniform(String name) {
    	return uniforms.containsKey(name);
    }
    
    
    public void initTextureGroup() {
    	textureGroup = 0;
    }

    /**
	 * Set the value associated with the uniform variable name.
	 *
	 * @param name of the uniform variable.
	 * @param The value to set the uniform variable.
	 */
    public void setUniform(UniformVariable var) {
    	if (hasUniform(var.getName())) {
    		
    		int location = uniforms.get(var.getName()).getLocation();
    		//if (var.getValue() == uniforms.get(var.getName()).getValue() && !needUpdate)
    		//	return;
    		
    		uniforms.get(var.getName()).setValue(var.getValue());
    		
    		switch(var.varType) {
    		case Int:
    			glUniform1i(location, (int) var.getValue());
    			break;
    		case Vector2i:
    			int[] vars = (int[]) var.getValue();
    			glUniform2i(location, vars[0], vars[1]);
    			break;
    		case Vector3i:
    			vars = (int[]) var.getValue();
    			glUniform3i(location, vars[0], vars[1], vars[2]);
    			break;
    		case Vector4i:
    			vars = (int[]) var.getValue();
    			glUniform4i(location, vars[0], vars[1], vars[2], vars[3]);
    			break;
    		case Float:
    			glUniform1f(location, (float) var.getValue());
    			break;
    		case Vector2f:
    			Vector2f v2f = (Vector2f) var.getValue();
    			glUniform2f(location, v2f.x, v2f.y); 	
    			break;
    		case Vector3f:
    			Vector3f v3f = (Vector3f) var.getValue();
    			glUniform3f(location, v3f.x, v3f.y, v3f.z); 	
    			break;
    		case Vector4f:
    			Vector4f v4f = (Vector4f) var.getValue();
    			glUniform4f(location, v4f.x, v4f.y, v4f.z, v4f.w); 	
    			break;
    		case Matrix4:
    			Matrix4f matrix4 = (Matrix4f) var.getValue();
    	        buffer.position(0);
    	        buffer.limit(16);
    	        matrix4.fillFloatBuffer(buffer, true);
    	        buffer.position(0);
    	        
    	        //System.out.println(matrix4);
    	        //System.out.println(var.name);
    	        //System.out.println(location);
    	        glUniformMatrix4(location, false, buffer);

    	        break;
    		case Matrix3:
    			Matrix3f matrix3 = (Matrix3f) var.getValue();
    	        buffer.position(0);
    	        buffer.limit(9);
    	        matrix3.fillFloatBuffer(buffer, true);
    	        buffer.position(0);
    	        glUniformMatrix3(location, false, buffer);
    			
    	        break;
    			
    		case Matrix4Array:
            	Matrix4f[] m4a = (Matrix4f[]) var.getValue();
            	
    	        FloatBuffer multiData = BufferUtils.createFloatBuffer(m4a.length * 16);
    	        
    	        for (int i = 0; i < m4a.length; i++) {
    	            m4a[i].fillFloatBuffer(multiData, true);
    	        }
    	        multiData.flip();
    	        
    	        //multiData.clear();
    	        glUniformMatrix4(location, false, multiData);
    			break;
    		case Texture2D:
    			glActiveTexture(GL_TEXTURE0 + textureGroup);
    			glBindTexture(GL_TEXTURE_2D, ((Texture) var.getValue()).getID());
    			
    			glUniform1i(location, textureGroup);
    			
    			textureGroup ++;
    			
    			break;
			default:
				break;
    		}
    	}
    }


   public void defineFragOut(int index, String variableName) {
       glBindFragDataLocation(id, index, variableName);
   }

   /**
	 *  Detach the shader from the program and delete it.
	 */

   @Override
   public void deleteObject() {
	   IntBuffer shaderCount = BufferUtils.createIntBuffer(16);

       glGetProgram(id, GL_ATTACHED_SHADERS, shaderCount);

       System.out.println("delete " + shaderCount.get(0) + " shaders");

       IntBuffer shaders = BufferUtils.createIntBuffer(shaderCount.get(0));

       glGetAttachedShaders(id, shaderCount, shaders);

       for (int i = 0; i < shaderCount.get(0); i++) {
    	   
           glDetachShader(id, shaders.get(i));
           glDeleteShader(shaders.get(i));
       }

       glUseProgram(0);
       glDeleteProgram(id);

   }
   /**
	 *  Returns the shader version.
	 * @return
	 */
   public static String getVersion() {
       return "shader language version : " + glGetString(GL_SHADING_LANGUAGE_VERSION);
   }

   public static int getAttributeCapacity() {
       return glGetInteger(GL_MAX_VERTEX_ATTRIBS);
   }

}
