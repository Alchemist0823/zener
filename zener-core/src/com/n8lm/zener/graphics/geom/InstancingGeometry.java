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

package com.n8lm.zener.graphics.geom;

import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.graphics.VertexBuffer.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public abstract class InstancingGeometry extends Geometry {

    protected Map<VertexBuffer.Type, Integer> divisors;

    protected int instancesCount;

    public InstancingGeometry(String shader) {
        super(shader);
        divisors = new HashMap<VertexBuffer.Type, Integer>();
        instancesCount = 0;
    }

    @Override
    public void createGL() {
        super.createGL();

        glBindVertexArray(id);

        for (Entry<Type, VertexBuffer> vb : vbs.entrySet()) {

            glVertexAttribDivisor(vb.getKey().id, divisors.get(vb.getKey()).intValue());
        }

        glBindVertexArray(0);

    }


    public int getInstancesCount() {
        return instancesCount;
    }

}
