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

import com.n8lm.zener.graphics.geom.Geometry;

import java.util.HashMap;
import java.util.Map;

public class HardResourceManager {

    class GeometryEntry {
        Geometry geometry;
        int invokeCount;

        public GeometryEntry(Geometry geometry) {
            this.geometry = geometry;
            this.invokeCount = 0;
        }
    }

    private Map<String, GeometryEntry> geometryNameMap;

    public HardResourceManager() {
        geometryNameMap = new HashMap<String, GeometryEntry>();
    }

    public boolean hasGeometry(String name) {
        return geometryNameMap.containsKey(name);
    }

	/*public boolean hasGeometry(Geometry geometry) {
        return invokeCount.containsKey(geometry);
	}*/

    public Geometry getGeometry(String name) {
        GeometryEntry geometryEntry = geometryNameMap.get(name);
        incrementInvokeCount(geometryEntry);
        return geometryEntry.geometry;
    }

    public void registerGeometry(Geometry geometry) {
        GeometryEntry geometryEntry = new GeometryEntry(geometry);
        geometryNameMap.put(geometry.getName(), geometryEntry);
        incrementInvokeCount(geometryEntry);
    }

    private void incrementInvokeCount(GeometryEntry geometryEntry) {
        geometryEntry.invokeCount ++;
    }

    public void reduceInvokeCount(Geometry geometry) {
        GeometryEntry geometryEntry = geometryNameMap.get(geometry.getName());
        geometryEntry.invokeCount --;
        if (geometryEntry.invokeCount == 0) {
            geometryNameMap.remove(geometry.getName());
            geometryEntry.geometry.deleteObject();
        }
    }
}
