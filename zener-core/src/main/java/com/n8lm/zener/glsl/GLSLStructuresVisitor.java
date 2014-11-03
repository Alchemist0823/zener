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

