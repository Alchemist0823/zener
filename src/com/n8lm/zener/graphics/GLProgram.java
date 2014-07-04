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

package com.n8lm.zener.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.n8lm.zener.glsl.VariableDef;
import org.lwjgl.BufferUtils;

import com.n8lm.zener.math.*;

public class GLProgram extends GLObject {


    private final static Logger LOGGER = Logger.getLogger(GLProgram.class
            .getName());

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
     * This links a vertex shader and a fragment shader to the program and adds uniforms to the program
     *
     * @param vertexShader vertex shader.
     * @param fragmentShader fragment shader.
     */
    // Never used
    public int linkProgram(GLShader vertexShader, GLShader fragmentShader) {

        LOGGER.info(getVersion());

        glAttachShader(id, vertexShader.getID());
        glAttachShader(id, fragmentShader.getID());
        glLinkProgram(id);

        int status = glGetProgrami(id, GL_LINK_STATUS);

        if (status == 0) {
            LOGGER.severe("Link unsuccessfully!");
            LOGGER.severe("******** start Link info log*********");
            int loglength = glGetProgrami(id, GL_INFO_LOG_LENGTH);
            LOGGER.severe(glGetProgramInfoLog(id, loglength));
            LOGGER.severe("********end Link info log*********");
        } else {

            for (VariableDef uniform : vertexShader.getUniforms())
                addUniform(uniform);

            for (VariableDef uniform : fragmentShader.getUniforms())
                addUniform(uniform);

            /*for (UniformVariable uv : uniforms.values()) {
                LOGGER.info(uv.toString());
            }*/

            LOGGER.info("link successfully.");
        }
        return status;
    }


    /**
     * Make the current shader program current.
     * The method name "bind" is used to use similar terminology to other OpenGL objects.
     */
    public void bind() {
        if (current != this) {
            glUseProgram(id);
            updateNeeded = true;
            GLProgram.current = this;
        } else
            updateNeeded = false;
    }

    /**
     * Disable the shader program.
     */
    public void unbind() {
        glUseProgram(0);
        GLProgram.current = null;
        updateNeeded = true;
    }


    /**
     * Define the uniform variable name to be used in the shader.
     *
     * @param uniform the uniform of the variable
     */
    private void addUniform(VariableDef uniform) {
        if (!uniforms.containsKey(uniform.getName())) {
            UniformVariable uniformVariable = new UniformVariable(uniform.getType(), uniform.getName());
            uniformVariable.setLocation(glGetUniformLocation(id, uniformVariable.getName()));
            this.uniforms.put(uniform.getName(), uniformVariable);
        }
    }

    /*
    public void initUniforms(String[] names) {
        for (String name : names) {
            UniformVariable uniform = new UniformVariable(name);
            uniform.setLocation(glGetUniformLocation(id, name));
            //LOGGER.info(name);
            uniforms.put(name, uniform);
        }
    }*/

    private boolean hasUniform(String name) {
        return uniforms.containsKey(name);
    }


    public void initTextureGroup() {
        textureGroup = 0;
    }

    /**
     * Set the value associated with the uniform variable name.
     *
     * @param var of the uniform variable.
     */
    public void setUniform(UniformVariable var) {
        if (hasUniform(var.getName())) {

            int location = uniforms.get(var.getName()).getLocation();
            //if (var.getValue() == uniforms.get(var.getName()).getValue() && !needUpdate)
            //	return;

            uniforms.get(var.getName()).setValue(var.getValue());

            switch (var.varType) {
                case Int:
                    glUniform1i(location, (Integer) var.getValue());
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
                    glUniform1f(location, (Float) var.getValue());
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

                    textureGroup++;

                    break;
                default:
                    break;
            }
            if (GLHelper.checkGLError())
                LOGGER.severe(var.getName() + " " + location);
        }
        else
        	LOGGER.info("no variable: " + var.getName());
    }


    public void defineFragOut(int index, String variableName) {
        glBindFragDataLocation(id, index, variableName);
    }

    /**
     * Detach the shader from the program and delete it.
     */

    @Override
    public void deleteObject() {
        IntBuffer shaderCount = BufferUtils.createIntBuffer(16);

        glGetProgram(id, GL_ATTACHED_SHADERS, shaderCount);

        //System.out.println("delete " + shaderCount.get(0) + " shaders");

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
     * Returns the shader version.
     *
     * @return
     */
    public static String getVersion() {
        return "shader language version : " + glGetString(GL_SHADING_LANGUAGE_VERSION);
    }

    public static int getAttributeCapacity() {
        return glGetInteger(GL_MAX_VERTEX_ATTRIBS);
    }

}
