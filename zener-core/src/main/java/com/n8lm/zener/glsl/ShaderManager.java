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

package com.n8lm.zener.glsl;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.n8lm.zener.graphics.GLProgram;
import com.n8lm.zener.graphics.GLShader;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.n8lm.zener.data.ResourceManager;

public class ShaderManager {

	private final static Logger LOGGER = Logger.getLogger(ShaderManager.class
		      .getName());
	
	private Map<String, GLProgram> programs;
	private Map<String, GLShader> shaders;
    private Map<String, ShaderSource> shaderSources;
	private ResourceManager rm;
	
	public ShaderManager(ResourceManager rm) {

		programs = new HashMap<String, GLProgram>();
		shaders = new HashMap<String, GLShader>();
        shaderSources = new HashMap<String, ShaderSource>();
		this.rm = rm;
	}

    public void loadShaderSource(String name, String[] filenames) {

		String shaderCode = rm.readText(name + ".glsl");
		
		if (!shaderSources.containsKey(name))
            shaderSources.put(name, new ShaderSource(name, shaderCode, filenames));

		LOGGER.info("Loaded Shader Source: " + name + " " + filenames);
	}
	
	public GLShader buildShader(String name, List<String> macros) throws IOException{

        GLShader shader = null;

        String key = generateShaderKey(name, macros);

        ShaderSource ss = shaderSources.get(name);

        if (ss == null)
            throw new IOException("can not find glsl resource :" + name);

        String shaderCode = ss.getSource();

        String[] libnames = ss.getIndependence();

        String[] groups = name.split("\\.");
        String type = groups[groups.length - 1];

        try {
            // check error
            if (type.equals("vert")) {
                shader = new GLShader(name, GLShader.ShaderType.Vertex);
            } else if (type.equals("frag")) {
                shader = new GLShader(name, GLShader.ShaderType.Fragment);
            } else
                throw new IOException("Error shader extension name" + name);

            for (String libname : libnames) {
                if (!shaderSources.containsKey(libname))
                    throw new IOException("can not find shader lib" + libname);
            }
            // add code
            List<String> codes = new ArrayList<String>();

            StringBuilder sb = new StringBuilder();
            for (String macro : macros)
                sb.append("#define " + macro + "\n");
            codes.add(sb.toString());

            addUniforms(shader, shaderCode, libnames);

            for (String libname : libnames) {
                if (shaderSources.containsKey(libname))
                    codes.add(shaderSources.get(libname).getSource());
            }
            codes.add(shaderCode);

            if (shader.compile(codes)) {

                if (!shaders.containsKey(name))
                    shaders.put(name, shader);

                LOGGER.info(name + ":" + shader.getID() + " compiled");
            } else {
                LOGGER.severe("******** start shader info log*********");
                shader.printErrorLog();
                LOGGER.severe("********end shader info log*********");
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
		LOGGER.info("Builded Shader: " + name);

        return shader;
	}

    public GLProgram getProgram(String vertName, String fragName, List<String> options) {
        String key = generateProgramKey(vertName, fragName, options);
        if (programs.containsKey(key))
            return programs.get(key);
        else {
            try {
                LOGGER.info(vertName + " " + fragName + Arrays.toString(options.toArray(new String[options.size()])));
                return buildProgram(vertName, fragName, options);
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.log(Level.SEVERE, "GLShader Compile Error", e);
                return null;
            }
        }
    }

    public String generateShaderKey(String name, List<String> macros) {

        StringBuffer key = new StringBuffer(name);
        key.append('(');

        for (String macro: macros) {
            key.append(macro);
            key.append(',');
        }
        return key.toString();
    }


    public String generateProgramKey(String vertName, String fragName, List<String> options) {
        StringBuffer key = new StringBuffer();
        key.append(vertName);
        key.append('_');
        key.append(fragName);
        key.append('(');
        for (String option: options) {
            key.append(option);
            key.append(',');
        }
        return key.toString();
    }

    private GLProgram buildProgram(String vertName, String fragName, List<String> options) throws IOException{
        String vertKey = generateShaderKey(vertName, options);
        String fragKey = generateShaderKey(fragName, options);

        if (!shaders.containsKey(vertKey)) {
            shaders.put(vertKey, buildShader(vertName, options));
        }
        if (!shaders.containsKey(fragKey)) {
            shaders.put(fragKey, buildShader(fragName, options));
        }

        GLShader vertShader = shaders.get(vertKey);
        GLShader fragShader = shaders.get(fragKey);

        String key = generateProgramKey(vertName, fragName, options);
        GLProgram program = new GLProgram();

        if (program.linkProgram(vertShader, fragShader) > 0) {
            programs.put(key, program);
            return program;
        } else
            return null;
    }
	
	private void addUniforms(GLShader shader, String code, String[] libnames) {

		GLSLStructuresVisitor svisitor = new GLSLStructuresVisitor();
		for (int i = 0; i < libnames.length; i ++) {
			ANTLRInputStream input = new ANTLRInputStream(shaderSources.get(libnames[i]).getSource());
			GLSL430Lexer lexer = new GLSL430Lexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			GLSL430Parser parser = new GLSL430Parser(tokens);
			ParseTree tree = parser.translation_unit();
			svisitor.visit(tree);
		}

		ANTLRInputStream input = new ANTLRInputStream(code);
		GLSL430Lexer lexer = new GLSL430Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		GLSL430Parser parser = new GLSL430Parser(tokens);
		ParseTree tree = parser.translation_unit();
		svisitor.visit(tree);
		
		UniformsDetector visitor = new UniformsDetector(svisitor, shader);
		visitor.visit(tree);
	}
}
