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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class GLSLTest {

	public static void main(String[] args) {
		InputStream inputStream = GLSLTest.class.getClassLoader().getResourceAsStream("shaders/standard_normal.frag.glsl");
		try {
			ANTLRInputStream input = new ANTLRInputStream(inputStream);
			GLSL430Lexer lexer = new GLSL430Lexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			GLSL430Parser parser = new GLSL430Parser(tokens);
			ParseTree tree = parser.translation_unit();

			GLSLStructuresVisitor svisitor = new GLSLStructuresVisitor();
			svisitor.visit(tree);
			//UniformsDetector visitor = new UniformsDetector(svisitor);
			//visitor.visit(tree);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
