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
import com.n8lm.zener.math.Matrix4f;

public class GlobalUniforms extends UniformGroup {

	public GlobalUniforms() {
		super();
    	uniforms.put("g_ViewMatrix", new UniformVariable("g_ViewMatrix", VarType.Matrix4));
    	uniforms.put("g_ModelMatrix", new UniformVariable("g_ModelMatrix", VarType.Matrix4));
    	uniforms.put("g_ProjectionMatrix", new UniformVariable("g_ProjectionMatrix", VarType.Matrix4));
	}

	public void setViewMatrix(Matrix4f value) {
		uniforms.get("g_ViewMatrix").setValue(value);
	}

	public void setModelMatrix(Matrix4f value) {
		uniforms.get("g_ModelMatrix").setValue(value);
	}
	
	public void setProjectionMatrix(Matrix4f value) {
		uniforms.get("g_ProjectionMatrix").setValue(value);
	}
}
