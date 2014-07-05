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
