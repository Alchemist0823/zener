// Generated from GLSL430.g4 by ANTLR 4.2.2
package com.n8lm.zener.glsl;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GLSL430Parser}.
 */
public interface GLSL430Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull GLSL430Parser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull GLSL430Parser.ExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#variable_identifier}.
	 * @param ctx the parse tree
	 */
	void enterVariable_identifier(@NotNull GLSL430Parser.Variable_identifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#variable_identifier}.
	 * @param ctx the parse tree
	 */
	void exitVariable_identifier(@NotNull GLSL430Parser.Variable_identifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#assignment_expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_expression(@NotNull GLSL430Parser.Assignment_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#assignment_expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_expression(@NotNull GLSL430Parser.Assignment_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#multiplicative_expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicative_expression(@NotNull GLSL430Parser.Multiplicative_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#multiplicative_expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicative_expression(@NotNull GLSL430Parser.Multiplicative_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void enterJump_statement(@NotNull GLSL430Parser.Jump_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void exitJump_statement(@NotNull GLSL430Parser.Jump_statementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#constructor_identifier}.
	 * @param ctx the parse tree
	 */
	void enterConstructor_identifier(@NotNull GLSL430Parser.Constructor_identifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#constructor_identifier}.
	 * @param ctx the parse tree
	 */
	void exitConstructor_identifier(@NotNull GLSL430Parser.Constructor_identifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#equality_expression}.
	 * @param ctx the parse tree
	 */
	void enterEquality_expression(@NotNull GLSL430Parser.Equality_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#equality_expression}.
	 * @param ctx the parse tree
	 */
	void exitEquality_expression(@NotNull GLSL430Parser.Equality_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#function_header}.
	 * @param ctx the parse tree
	 */
	void enterFunction_header(@NotNull GLSL430Parser.Function_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#function_header}.
	 * @param ctx the parse tree
	 */
	void exitFunction_header(@NotNull GLSL430Parser.Function_headerContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#struct_specifier}.
	 * @param ctx the parse tree
	 */
	void enterStruct_specifier(@NotNull GLSL430Parser.Struct_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#struct_specifier}.
	 * @param ctx the parse tree
	 */
	void exitStruct_specifier(@NotNull GLSL430Parser.Struct_specifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#struct_declarator_list}.
	 * @param ctx the parse tree
	 */
	void enterStruct_declarator_list(@NotNull GLSL430Parser.Struct_declarator_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#struct_declarator_list}.
	 * @param ctx the parse tree
	 */
	void exitStruct_declarator_list(@NotNull GLSL430Parser.Struct_declarator_listContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#struct_declarator}.
	 * @param ctx the parse tree
	 */
	void enterStruct_declarator(@NotNull GLSL430Parser.Struct_declaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#struct_declarator}.
	 * @param ctx the parse tree
	 */
	void exitStruct_declarator(@NotNull GLSL430Parser.Struct_declaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#function_definition}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition(@NotNull GLSL430Parser.Function_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#function_definition}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition(@NotNull GLSL430Parser.Function_definitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#compound_statement_no_new_scope}.
	 * @param ctx the parse tree
	 */
	void enterCompound_statement_no_new_scope(@NotNull GLSL430Parser.Compound_statement_no_new_scopeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#compound_statement_no_new_scope}.
	 * @param ctx the parse tree
	 */
	void exitCompound_statement_no_new_scope(@NotNull GLSL430Parser.Compound_statement_no_new_scopeContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(@NotNull GLSL430Parser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(@NotNull GLSL430Parser.DeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#function_prototype}.
	 * @param ctx the parse tree
	 */
	void enterFunction_prototype(@NotNull GLSL430Parser.Function_prototypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#function_prototype}.
	 * @param ctx the parse tree
	 */
	void exitFunction_prototype(@NotNull GLSL430Parser.Function_prototypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#fully_specified_type}.
	 * @param ctx the parse tree
	 */
	void enterFully_specified_type(@NotNull GLSL430Parser.Fully_specified_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#fully_specified_type}.
	 * @param ctx the parse tree
	 */
	void exitFully_specified_type(@NotNull GLSL430Parser.Fully_specified_typeContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#init_declarator_list}.
	 * @param ctx the parse tree
	 */
	void enterInit_declarator_list(@NotNull GLSL430Parser.Init_declarator_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#init_declarator_list}.
	 * @param ctx the parse tree
	 */
	void exitInit_declarator_list(@NotNull GLSL430Parser.Init_declarator_listContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(@NotNull GLSL430Parser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(@NotNull GLSL430Parser.ConditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#init_declarator}.
	 * @param ctx the parse tree
	 */
	void enterInit_declarator(@NotNull GLSL430Parser.Init_declaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#init_declarator}.
	 * @param ctx the parse tree
	 */
	void exitInit_declarator(@NotNull GLSL430Parser.Init_declaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#translation_unit}.
	 * @param ctx the parse tree
	 */
	void enterTranslation_unit(@NotNull GLSL430Parser.Translation_unitContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#translation_unit}.
	 * @param ctx the parse tree
	 */
	void exitTranslation_unit(@NotNull GLSL430Parser.Translation_unitContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#type_qualifier}.
	 * @param ctx the parse tree
	 */
	void enterType_qualifier(@NotNull GLSL430Parser.Type_qualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#type_qualifier}.
	 * @param ctx the parse tree
	 */
	void exitType_qualifier(@NotNull GLSL430Parser.Type_qualifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterExclusive_or_expression(@NotNull GLSL430Parser.Exclusive_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitExclusive_or_expression(@NotNull GLSL430Parser.Exclusive_or_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_and_expression(@NotNull GLSL430Parser.Logical_and_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_and_expression(@NotNull GLSL430Parser.Logical_and_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#additive_expression}.
	 * @param ctx the parse tree
	 */
	void enterAdditive_expression(@NotNull GLSL430Parser.Additive_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#additive_expression}.
	 * @param ctx the parse tree
	 */
	void exitAdditive_expression(@NotNull GLSL430Parser.Additive_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#field_selection}.
	 * @param ctx the parse tree
	 */
	void enterField_selection(@NotNull GLSL430Parser.Field_selectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#field_selection}.
	 * @param ctx the parse tree
	 */
	void exitField_selection(@NotNull GLSL430Parser.Field_selectionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#unary_operator}.
	 * @param ctx the parse tree
	 */
	void enterUnary_operator(@NotNull GLSL430Parser.Unary_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#unary_operator}.
	 * @param ctx the parse tree
	 */
	void exitUnary_operator(@NotNull GLSL430Parser.Unary_operatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#shift_expression}.
	 * @param ctx the parse tree
	 */
	void enterShift_expression(@NotNull GLSL430Parser.Shift_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#shift_expression}.
	 * @param ctx the parse tree
	 */
	void exitShift_expression(@NotNull GLSL430Parser.Shift_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#function_call}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call(@NotNull GLSL430Parser.Function_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#function_call}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call(@NotNull GLSL430Parser.Function_callContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#iteration_statement}.
	 * @param ctx the parse tree
	 */
	void enterIteration_statement(@NotNull GLSL430Parser.Iteration_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#iteration_statement}.
	 * @param ctx the parse tree
	 */
	void exitIteration_statement(@NotNull GLSL430Parser.Iteration_statementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#logical_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_or_expression(@NotNull GLSL430Parser.Logical_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#logical_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_or_expression(@NotNull GLSL430Parser.Logical_or_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#primary_expression_or_function_call}.
	 * @param ctx the parse tree
	 */
	void enterPrimary_expression_or_function_call(@NotNull GLSL430Parser.Primary_expression_or_function_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#primary_expression_or_function_call}.
	 * @param ctx the parse tree
	 */
	void exitPrimary_expression_or_function_call(@NotNull GLSL430Parser.Primary_expression_or_function_callContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#simple_statement}.
	 * @param ctx the parse tree
	 */
	void enterSimple_statement(@NotNull GLSL430Parser.Simple_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#simple_statement}.
	 * @param ctx the parse tree
	 */
	void exitSimple_statement(@NotNull GLSL430Parser.Simple_statementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#statement_with_scope}.
	 * @param ctx the parse tree
	 */
	void enterStatement_with_scope(@NotNull GLSL430Parser.Statement_with_scopeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#statement_with_scope}.
	 * @param ctx the parse tree
	 */
	void exitStatement_with_scope(@NotNull GLSL430Parser.Statement_with_scopeContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterInclusive_or_expression(@NotNull GLSL430Parser.Inclusive_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitInclusive_or_expression(@NotNull GLSL430Parser.Inclusive_or_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#integer_expression}.
	 * @param ctx the parse tree
	 */
	void enterInteger_expression(@NotNull GLSL430Parser.Integer_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#integer_expression}.
	 * @param ctx the parse tree
	 */
	void exitInteger_expression(@NotNull GLSL430Parser.Integer_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#statement_no_new_scope}.
	 * @param ctx the parse tree
	 */
	void enterStatement_no_new_scope(@NotNull GLSL430Parser.Statement_no_new_scopeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#statement_no_new_scope}.
	 * @param ctx the parse tree
	 */
	void exitStatement_no_new_scope(@NotNull GLSL430Parser.Statement_no_new_scopeContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#function_declarator}.
	 * @param ctx the parse tree
	 */
	void enterFunction_declarator(@NotNull GLSL430Parser.Function_declaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#function_declarator}.
	 * @param ctx the parse tree
	 */
	void exitFunction_declarator(@NotNull GLSL430Parser.Function_declaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#function_identifier}.
	 * @param ctx the parse tree
	 */
	void enterFunction_identifier(@NotNull GLSL430Parser.Function_identifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#function_identifier}.
	 * @param ctx the parse tree
	 */
	void exitFunction_identifier(@NotNull GLSL430Parser.Function_identifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#constant_expression}.
	 * @param ctx the parse tree
	 */
	void enterConstant_expression(@NotNull GLSL430Parser.Constant_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#constant_expression}.
	 * @param ctx the parse tree
	 */
	void exitConstant_expression(@NotNull GLSL430Parser.Constant_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#struct_declaration}.
	 * @param ctx the parse tree
	 */
	void enterStruct_declaration(@NotNull GLSL430Parser.Struct_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#struct_declaration}.
	 * @param ctx the parse tree
	 */
	void exitStruct_declaration(@NotNull GLSL430Parser.Struct_declarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#relational_expression}.
	 * @param ctx the parse tree
	 */
	void enterRelational_expression(@NotNull GLSL430Parser.Relational_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#relational_expression}.
	 * @param ctx the parse tree
	 */
	void exitRelational_expression(@NotNull GLSL430Parser.Relational_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#parameter_declaration}.
	 * @param ctx the parse tree
	 */
	void enterParameter_declaration(@NotNull GLSL430Parser.Parameter_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#parameter_declaration}.
	 * @param ctx the parse tree
	 */
	void exitParameter_declaration(@NotNull GLSL430Parser.Parameter_declarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#interpolation_qualifier}.
	 * @param ctx the parse tree
	 */
	void enterInterpolation_qualifier(@NotNull GLSL430Parser.Interpolation_qualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#interpolation_qualifier}.
	 * @param ctx the parse tree
	 */
	void exitInterpolation_qualifier(@NotNull GLSL430Parser.Interpolation_qualifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#layout_qualifier}.
	 * @param ctx the parse tree
	 */
	void enterLayout_qualifier(@NotNull GLSL430Parser.Layout_qualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#layout_qualifier}.
	 * @param ctx the parse tree
	 */
	void exitLayout_qualifier(@NotNull GLSL430Parser.Layout_qualifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#struct_declaration_list}.
	 * @param ctx the parse tree
	 */
	void enterStruct_declaration_list(@NotNull GLSL430Parser.Struct_declaration_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#struct_declaration_list}.
	 * @param ctx the parse tree
	 */
	void exitStruct_declaration_list(@NotNull GLSL430Parser.Struct_declaration_listContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void enterPostfix_expression(@NotNull GLSL430Parser.Postfix_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void exitPostfix_expression(@NotNull GLSL430Parser.Postfix_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#assignment_operator}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_operator(@NotNull GLSL430Parser.Assignment_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#assignment_operator}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_operator(@NotNull GLSL430Parser.Assignment_operatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#declaration_statement}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration_statement(@NotNull GLSL430Parser.Declaration_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#declaration_statement}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration_statement(@NotNull GLSL430Parser.Declaration_statementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void enterUnary_expression(@NotNull GLSL430Parser.Unary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void exitUnary_expression(@NotNull GLSL430Parser.Unary_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#parameter_qualifier}.
	 * @param ctx the parse tree
	 */
	void enterParameter_qualifier(@NotNull GLSL430Parser.Parameter_qualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#parameter_qualifier}.
	 * @param ctx the parse tree
	 */
	void exitParameter_qualifier(@NotNull GLSL430Parser.Parameter_qualifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#single_declarator}.
	 * @param ctx the parse tree
	 */
	void enterSingle_declarator(@NotNull GLSL430Parser.Single_declaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#single_declarator}.
	 * @param ctx the parse tree
	 */
	void exitSingle_declarator(@NotNull GLSL430Parser.Single_declaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#function_call_header}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_header(@NotNull GLSL430Parser.Function_call_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#function_call_header}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_header(@NotNull GLSL430Parser.Function_call_headerContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#for_init_statement}.
	 * @param ctx the parse tree
	 */
	void enterFor_init_statement(@NotNull GLSL430Parser.For_init_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#for_init_statement}.
	 * @param ctx the parse tree
	 */
	void exitFor_init_statement(@NotNull GLSL430Parser.For_init_statementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#statement_list}.
	 * @param ctx the parse tree
	 */
	void enterStatement_list(@NotNull GLSL430Parser.Statement_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#statement_list}.
	 * @param ctx the parse tree
	 */
	void exitStatement_list(@NotNull GLSL430Parser.Statement_listContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#selection_statement}.
	 * @param ctx the parse tree
	 */
	void enterSelection_statement(@NotNull GLSL430Parser.Selection_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#selection_statement}.
	 * @param ctx the parse tree
	 */
	void exitSelection_statement(@NotNull GLSL430Parser.Selection_statementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#expression_statement}.
	 * @param ctx the parse tree
	 */
	void enterExpression_statement(@NotNull GLSL430Parser.Expression_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#expression_statement}.
	 * @param ctx the parse tree
	 */
	void exitExpression_statement(@NotNull GLSL430Parser.Expression_statementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#external_declaration}.
	 * @param ctx the parse tree
	 */
	void enterExternal_declaration(@NotNull GLSL430Parser.External_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#external_declaration}.
	 * @param ctx the parse tree
	 */
	void exitExternal_declaration(@NotNull GLSL430Parser.External_declarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#storage_qualifier}.
	 * @param ctx the parse tree
	 */
	void enterStorage_qualifier(@NotNull GLSL430Parser.Storage_qualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#storage_qualifier}.
	 * @param ctx the parse tree
	 */
	void exitStorage_qualifier(@NotNull GLSL430Parser.Storage_qualifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#conditional_expression}.
	 * @param ctx the parse tree
	 */
	void enterConditional_expression(@NotNull GLSL430Parser.Conditional_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#conditional_expression}.
	 * @param ctx the parse tree
	 */
	void exitConditional_expression(@NotNull GLSL430Parser.Conditional_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#and_expression}.
	 * @param ctx the parse tree
	 */
	void enterAnd_expression(@NotNull GLSL430Parser.And_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#and_expression}.
	 * @param ctx the parse tree
	 */
	void exitAnd_expression(@NotNull GLSL430Parser.And_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#primary_expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimary_expression(@NotNull GLSL430Parser.Primary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#primary_expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimary_expression(@NotNull GLSL430Parser.Primary_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#compound_statement_with_scope}.
	 * @param ctx the parse tree
	 */
	void enterCompound_statement_with_scope(@NotNull GLSL430Parser.Compound_statement_with_scopeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#compound_statement_with_scope}.
	 * @param ctx the parse tree
	 */
	void exitCompound_statement_with_scope(@NotNull GLSL430Parser.Compound_statement_with_scopeContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#logical_xor_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_xor_expression(@NotNull GLSL430Parser.Logical_xor_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#logical_xor_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_xor_expression(@NotNull GLSL430Parser.Logical_xor_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#type_specifier}.
	 * @param ctx the parse tree
	 */
	void enterType_specifier(@NotNull GLSL430Parser.Type_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#type_specifier}.
	 * @param ctx the parse tree
	 */
	void exitType_specifier(@NotNull GLSL430Parser.Type_specifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#for_rest_statement}.
	 * @param ctx the parse tree
	 */
	void enterFor_rest_statement(@NotNull GLSL430Parser.For_rest_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#for_rest_statement}.
	 * @param ctx the parse tree
	 */
	void exitFor_rest_statement(@NotNull GLSL430Parser.For_rest_statementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#function_call_generic}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_generic(@NotNull GLSL430Parser.Function_call_genericContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#function_call_generic}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_generic(@NotNull GLSL430Parser.Function_call_genericContext ctx);

	/**
	 * Enter a parse tree produced by {@link GLSL430Parser#initializer}.
	 * @param ctx the parse tree
	 */
	void enterInitializer(@NotNull GLSL430Parser.InitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GLSL430Parser#initializer}.
	 * @param ctx the parse tree
	 */
	void exitInitializer(@NotNull GLSL430Parser.InitializerContext ctx);
}