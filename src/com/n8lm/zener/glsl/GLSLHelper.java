package com.n8lm.zener.glsl;

/**
 * Created by Alchemist on 2014/6/19.
 */
public class GLSLHelper {

    public static VarType getVarTypeFromDef(String type, boolean isArray) {
        if (type.equals("mat4")) {
            if (isArray)
                return VarType.Matrix4Array;
            else
                return VarType.Matrix4;
        } else if (type.equals("mat3")) {
            if (isArray)
                return VarType.Matrix3Array;
            else
                return VarType.Matrix3;
        } else if (type.equals("vec4")) {
            if (isArray)
                return VarType.Vector4Array;
            else
                return VarType.Vector4f;
        } else if (type.equals("vec3")) {
            if (isArray)
                return VarType.Vector3Array;
            else
                return VarType.Vector3f;
        } else if (type.equals("vec2")) {
            if (isArray)
                return VarType.Vector2Array;
            else
                return VarType.Vector2f;
        } else if (type.equals("sampler2D")) {
                return VarType.Texture2D;
        } else if (type.equals("float")) {
            if (isArray)
                return VarType.FloatArray;
            else
                return VarType.Float;
        }
        else
            throw new IllegalArgumentException(type);
    }

    public static void addGLSLVariables(VariableContainer container, String type, String varName, int arrayLength) {

        VarType varType = GLSLHelper.getVarTypeFromDef(type, arrayLength > 0);
        if (varType == VarType.Texture2D && arrayLength > 0) {
            int n = arrayLength;
            for(int i = 0; i < n; i ++)
                container.add(new VariableDef(varType, varName + "[" + i + "]"));
        } else
            container.add(new VariableDef(varType, varName));
    }
}
