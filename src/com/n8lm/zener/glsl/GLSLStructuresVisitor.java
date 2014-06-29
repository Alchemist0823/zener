package com.n8lm.zener.glsl;

import java.util.*;

import com.n8lm.zener.glsl.GLSL430Parser.Struct_declarationContext;
import com.n8lm.zener.glsl.GLSL430Parser.Struct_declaratorContext;
import com.n8lm.zener.glsl.GLSL430Parser.Struct_specifierContext;

public class GLSLStructuresVisitor extends GLSL430BaseVisitor<Void>{
	
	private Map<String, GLSLStructure> structs = new HashMap<String, GLSLStructure>();

	@Override
	public Void visitStruct_specifier(Struct_specifierContext ctx) {
		
		GLSLStructure struct = new GLSLStructure(ctx.IDENTIFIER().getText());
		
		for(Struct_declarationContext structDec : ctx.struct_declaration_list().struct_declaration()) {
			for(Struct_declaratorContext declarator : structDec.struct_declarator_list().struct_declarator()) {
                int arrayLength = 0;
                if (declarator.constant_expression() != null)
                    arrayLength = Integer.parseInt(declarator.constant_expression().getText());

                GLSLHelper.addGLSLVariables(struct, structDec.type_specifier().getText(), declarator.IDENTIFIER().getText(), arrayLength);
			}
		}
		structs.put(struct.getName(), struct);
			
		return super.visitStruct_specifier(ctx);
	}
		
	public Map<String, GLSLStructure> getStructs() {
		return structs;
	}
}

