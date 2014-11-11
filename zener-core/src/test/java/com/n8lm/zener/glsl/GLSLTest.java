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
import java.io.InputStream;

import com.n8lm.zener.graphics.GLShader;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Test;

public class GLSLTest {

	@Test
	public void testGLSLParser() throws IOException {
		InputStream inputStream = GLSLTest.class.getClassLoader().getResourceAsStream("shaders/standard.frag.glsl");

		ANTLRInputStream input = new ANTLRInputStream(inputStream);
		GLSL430Lexer lexer = new GLSL430Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		GLSL430Parser parser = new GLSL430Parser(tokens);
		ParseTree tree = parser.translation_unit();

		GLSLStructuresVisitor svisitor = new GLSLStructuresVisitor();
		svisitor.visit(tree);
		GLShader glShader = new GLShader("shaderTest", GLShader.ShaderType.Fragment);
		UniformsDetector visitor = new UniformsDetector(svisitor, glShader);
		visitor.visit(tree);

		Assert.assertEquals("the numbers of uniforms not equal", glShader.getUniforms().size(), 7);
		/*for (VariableDef var : glShader.getUniforms()) {
			System.out.println("var.getName() = " + var.getName());
			System.out.println("var.getType() = " + var.getType());
		}*/

	}
}
