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
 * Created by Alchemist on 2014/6/18.
 */
public class ShaderSource {
    private String name;
    private String source;
    private String[] independence;

    public ShaderSource(String name, String source, String[] independence) {
        this.name = name;
        this.source = source;
        this.independence = independence;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String[] getIndependence() {
        return independence;
    }
}
