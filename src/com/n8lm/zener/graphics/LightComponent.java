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

import com.artemis.Component;

public class LightComponent extends Component {
	private float[] diffuse = new float[]{0.8f, 0.8f, 0.8f, 1f};
	private float[] ambient = new float[]{0.2f, 0.2f, 0.2f, 1f};
	private boolean isPoint = true;
	
	public LightComponent() {
	}

	public LightComponent(boolean isPoint) {
		this.isPoint = isPoint;
	}

	public boolean isPoint() {
		return isPoint;
	}
	
	public void setPoint(boolean isPoint) {
		this.isPoint = isPoint;
	}

	public float[] getAmbient() {
		return ambient;
	}

	public void setAmbient(float[] ambient) {
		this.ambient = ambient;
	}

	public float[] getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(float[] diffuse) {
		this.diffuse = diffuse;
	}
}
