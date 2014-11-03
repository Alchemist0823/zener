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

// Generated from GLSL430.g4 by ANTLR 4.2.2
package com.n8lm.zener.glsl;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GLSL430Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GLSL430Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull GLSL430Parser.ExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#variable_identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_identifier(@NotNull GLSL430Parser.Variable_identifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#assignment_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_expression(@NotNull GLSL430Parser.Assignment_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#multiplicative_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative_expression(@NotNull GLSL430Parser.Multiplicative_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#jump_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJump_statement(@NotNull GLSL430Parser.Jump_statementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#constructor_identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructor_identifier(@NotNull GLSL430Parser.Constructor_identifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#equality_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquality_expression(@NotNull GLSL430Parser.Equality_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#function_header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_header(@NotNull GLSL430Parser.Function_headerContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#struct_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_specifier(@NotNull GLSL430Parser.Struct_specifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#struct_declarator_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_declarator_list(@NotNull GLSL430Parser.Struct_declarator_listContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#struct_declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_declarator(@NotNull GLSL430Parser.Struct_declaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#function_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition(@NotNull GLSL430Parser.Function_definitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#compound_statement_no_new_scope}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompound_statement_no_new_scope(@NotNull GLSL430Parser.Compound_statement_no_new_scopeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(@NotNull GLSL430Parser.DeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#function_prototype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_prototype(@NotNull GLSL430Parser.Function_prototypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#fully_specified_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFully_specified_type(@NotNull GLSL430Parser.Fully_specified_typeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#init_declarator_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInit_declarator_list(@NotNull GLSL430Parser.Init_declarator_listContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(@NotNull GLSL430Parser.ConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#init_declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInit_declarator(@NotNull GLSL430Parser.Init_declaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#translation_unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranslation_unit(@NotNull GLSL430Parser.Translation_unitContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#type_qualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_qualifier(@NotNull GLSL430Parser.Type_qualifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusive_or_expression(@NotNull GLSL430Parser.Exclusive_or_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#logical_and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_and_expression(@NotNull GLSL430Parser.Logical_and_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#additive_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_expression(@NotNull GLSL430Parser.Additive_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#field_selection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField_selection(@NotNull GLSL430Parser.Field_selectionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#unary_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_operator(@NotNull GLSL430Parser.Unary_operatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#shift_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShift_expression(@NotNull GLSL430Parser.Shift_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#function_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_call(@NotNull GLSL430Parser.Function_callContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#iteration_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteration_statement(@NotNull GLSL430Parser.Iteration_statementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#logical_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_or_expression(@NotNull GLSL430Parser.Logical_or_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#primary_expression_or_function_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_expression_or_function_call(@NotNull GLSL430Parser.Primary_expression_or_function_callContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#simple_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_statement(@NotNull GLSL430Parser.Simple_statementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#statement_with_scope}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_with_scope(@NotNull GLSL430Parser.Statement_with_scopeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusive_or_expression(@NotNull GLSL430Parser.Inclusive_or_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#integer_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger_expression(@NotNull GLSL430Parser.Integer_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#statement_no_new_scope}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_no_new_scope(@NotNull GLSL430Parser.Statement_no_new_scopeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#function_declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_declarator(@NotNull GLSL430Parser.Function_declaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#function_identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_identifier(@NotNull GLSL430Parser.Function_identifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#constant_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant_expression(@NotNull GLSL430Parser.Constant_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#struct_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_declaration(@NotNull GLSL430Parser.Struct_declarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#relational_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_expression(@NotNull GLSL430Parser.Relational_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#parameter_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter_declaration(@NotNull GLSL430Parser.Parameter_declarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#interpolation_qualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterpolation_qualifier(@NotNull GLSL430Parser.Interpolation_qualifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#layout_qualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLayout_qualifier(@NotNull GLSL430Parser.Layout_qualifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#struct_declaration_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_declaration_list(@NotNull GLSL430Parser.Struct_declaration_listContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#postfix_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix_expression(@NotNull GLSL430Parser.Postfix_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#assignment_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_operator(@NotNull GLSL430Parser.Assignment_operatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#declaration_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration_statement(@NotNull GLSL430Parser.Declaration_statementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#unary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_expression(@NotNull GLSL430Parser.Unary_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#parameter_qualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter_qualifier(@NotNull GLSL430Parser.Parameter_qualifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#single_declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingle_declarator(@NotNull GLSL430Parser.Single_declaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#function_call_header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_call_header(@NotNull GLSL430Parser.Function_call_headerContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#for_init_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_init_statement(@NotNull GLSL430Parser.For_init_statementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#statement_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_list(@NotNull GLSL430Parser.Statement_listContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#selection_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelection_statement(@NotNull GLSL430Parser.Selection_statementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#expression_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression_statement(@NotNull GLSL430Parser.Expression_statementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#external_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExternal_declaration(@NotNull GLSL430Parser.External_declarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#storage_qualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStorage_qualifier(@NotNull GLSL430Parser.Storage_qualifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#conditional_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditional_expression(@NotNull GLSL430Parser.Conditional_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_expression(@NotNull GLSL430Parser.And_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#primary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_expression(@NotNull GLSL430Parser.Primary_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#compound_statement_with_scope}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompound_statement_with_scope(@NotNull GLSL430Parser.Compound_statement_with_scopeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#logical_xor_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_xor_expression(@NotNull GLSL430Parser.Logical_xor_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#type_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_specifier(@NotNull GLSL430Parser.Type_specifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#for_rest_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_rest_statement(@NotNull GLSL430Parser.For_rest_statementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#function_call_generic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_call_generic(@NotNull GLSL430Parser.Function_call_genericContext ctx);

	/**
	 * Visit a parse tree produced by {@link GLSL430Parser#initializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializer(@NotNull GLSL430Parser.InitializerContext ctx);
}