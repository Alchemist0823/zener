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
import com.n8lm.zener.graphics.geom.Geometry;

public class GeometryComponent extends Component {

    private boolean visible;
    private boolean shadowCaster;
    private Geometry ds;

    public GeometryComponent(Geometry ds) {
        this(ds, true);
    }

    public GeometryComponent(Geometry ds, boolean shadowCaster) {
        this.ds = ds;
        this.shadowCaster = shadowCaster;
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Geometry getGeometry() {
        return ds;
    }

    public void setGeometry(Geometry ds) {
        this.ds = ds;
    }

    public boolean isShadowCaster() {
        return shadowCaster;
    }

    public void setShadowCaster(boolean shadowCaster) {
        this.shadowCaster = shadowCaster;
    }
}
