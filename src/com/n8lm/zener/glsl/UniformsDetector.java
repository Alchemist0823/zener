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

import com.n8lm.zener.glsl.GLSL430Parser.Fully_specified_typeContext;
import com.n8lm.zener.glsl.GLSL430Parser.Init_declarator_listContext;
import com.n8lm.zener.glsl.GLSL430Parser.Single_declaratorContext;
import com.n8lm.zener.graphics.GLShader;

public class UniformsDetector extends GLSL430BaseVisitor<Void> {

	private GLSLStructuresVisitor structVisitor;
    private GLShader glShader;
	
	public UniformsDetector(GLSLStructuresVisitor structVisitor, GLShader glShader) {
		this.structVisitor = structVisitor;
        this.glShader = glShader;
	}
	@Override
	public Void visitInit_declarator_list(Init_declarator_listContext ctx) {
		
		Fully_specified_typeContext fully_specified_type = ctx.fully_specified_type();
		
		//System.out.println(ctx.getText());
		
		if (fully_specified_type != null && fully_specified_type.type_qualifier() != null
				&& fully_specified_type.type_qualifier().storage_qualifier() != null
				&& fully_specified_type.type_qualifier().storage_qualifier().getText().equals("uniform")) {
			
			if (ctx.single_declarator().size() > 0) {
				String structName = "";
				if (fully_specified_type.type_specifier().struct_specifier() != null) {
                    structName = fully_specified_type.type_specifier().struct_specifier().IDENTIFIER().getText();
					
				} else if (fully_specified_type.type_specifier().IDENTIFIER() != null) {
                    structName = fully_specified_type.type_specifier().IDENTIFIER().getText();
					
				}
				
				if (structName.equals("")) {
					for (Single_declaratorContext declarator : ctx.single_declarator()) {
                        String type = fully_specified_type.type_specifier().getText();
                        String varname = declarator.IDENTIFIER().getText();
                        int arrayLength = 0;
                        if (declarator.init_declarator() != null && declarator.init_declarator().constant_expression() != null)
                            arrayLength = Integer.parseInt(declarator.init_declarator().constant_expression().getText());

                        GLSLHelper.addGLSLVariables(glShader, type, varname, arrayLength);
                        //System.out.println(fully_specified_type.type_specifier().getText() + ": " + declarator.IDENTIFIER().getText());
                    }
				} else {
					
					if (structVisitor.getStructs().containsKey(structName)) {
						for (Single_declaratorContext declarator : ctx.single_declarator())
							for (VariableDef var : structVisitor.getStructs().get(structName).getVars()) {
                                String varname = declarator.IDENTIFIER().getText() + "." + var.getName();
                                glShader.add(new VariableDef(var.getType(), varname));
								//System.out.println(var.type + ": " + declarator.IDENTIFIER().getText() + "." + var.name);
							}
					}
				}
			}
		}
		return super.visitInit_declarator_list(ctx);
	}

}
