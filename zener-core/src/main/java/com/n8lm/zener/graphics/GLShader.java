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

import com.n8lm.zener.glsl.VariableContainer;
import com.n8lm.zener.glsl.VariableDef;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

public class GLShader extends GLObject implements VariableContainer {

    private final static Logger LOGGER = Logger.getLogger(GLShader.class
            .getName());

    public enum ShaderType {
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

    private String name;
    private ShaderType type;
    private List<String> macros;
    private List<VariableDef> uniforms;

    //private int geometricShader = -1;

    public GLShader(String name, ShaderType type) {
        super();
        this.name = name;
        this.type = type;
        this.macros = new ArrayList<String>();
        this.uniforms = new ArrayList<VariableDef>();
    }

    public void add(VariableDef uniform) {
        this.uniforms.add(uniform);
    }

    public List<VariableDef> getUniforms() {
        return this.uniforms;
    }

    /**
     * Create Vertex shader and compile the code contained in strs list
     *
     * @param strs the list source
     */
    public boolean compile(List<String> strs) {

        if (type == ShaderType.Vertex)
            id = glCreateShader(GL_VERTEX_SHADER);
        else if (type == ShaderType.Fragment)
            id = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(id, strs.toArray(new String[strs.size()]));
        glCompileShader(id);

        return (glGetShaderi(id, GL_COMPILE_STATUS) == GL_TRUE);
    }

    /**
     * Link the Vertex and Fragment shaders
     * A printout to the console lists any errors with the linkage.
     */
    public void printErrorLog() {
        int loglength = glGetShaderi(id, GL_INFO_LOG_LENGTH);
        LOGGER.severe(glGetShaderInfoLog(id, loglength));
    }

    @Override
    public void deleteObject() {
        glDeleteShader(getID());
    }

}

