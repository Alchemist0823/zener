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
import com.n8lm.zener.graphics.material.UniformsMaterial;

public class MaterialComponent extends Component {
	
	private UniformsMaterial material;
	private boolean transparent;
	private boolean shadowReceiver;
	
	public MaterialComponent(UniformsMaterial material) {
        this(material, false, false);
	}

    public MaterialComponent(UniformsMaterial material, boolean shadowReceiver) {
        this(material, shadowReceiver, false);
    }

    public MaterialComponent(UniformsMaterial material, boolean shadowReceiver, boolean transparent) {
        this.material = material;
        this.shadowReceiver = shadowReceiver;
        this.transparent = transparent;
    }

	public UniformsMaterial getMaterial() {
		return material;
	}

	public void setMaterial(UniformsMaterial material) {
		this.material = material;
	}

	public boolean isTransparent() {
		return transparent;
	}

	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	public boolean isShadowReceiver() {
		return shadowReceiver;
	}

	public void setShadowReceiver(boolean shadowReceiver) {
		this.shadowReceiver = shadowReceiver;
	}
	
}
