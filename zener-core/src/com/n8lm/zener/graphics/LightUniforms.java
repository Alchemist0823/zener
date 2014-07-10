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

package com.n8lm.zener.graphics;

import com.n8lm.zener.glsl.VarType;
import com.n8lm.zener.math.Vector3f;

/**
 * @deprecated
 */
public class LightUniforms extends UniformGroup {

    public LightUniforms() {
        super();
        //addUniform("Light[].La", VarType.Vector3f);
        uniforms.put("Lights[].La", new UniformVariable("Light.La", VarType.Vector3f, new Vector3f(0.2f, 0.2f, 0.2f)));
        uniforms.put("Lights[].Ld", new UniformVariable("Light.Ld", VarType.Vector3f, new Vector3f(0.8f, 0.8f, 0.8f)));
        uniforms.put("Lights[].Ls", new UniformVariable("Light.Ls", VarType.Vector3f, new Vector3f(1.0f, 1.0f, 1.0f)));
        uniforms.put("Lights[].Position", new UniformVariable("Light.Position", VarType.Vector4f));
    }

    public void setAmbientColor(float x, float y, float z) {
        setVector3f("Light.La", x, y, z);
    }

    public void setDiffuseColor(float x, float y, float z) {
        setVector3f("Light.Ld", x, y, z);
    }

    public void setSpecularColor(float x, float y, float z) {
        setVector3f("Light.Ls", x, y, z);
    }

    public void setPosition(float x, float y, float z, float isPoint) {
        setVector4f("Light.Position", x, y, z, isPoint);
    }
}
