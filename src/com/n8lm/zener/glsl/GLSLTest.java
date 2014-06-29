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
