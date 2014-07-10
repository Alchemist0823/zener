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
import com.n8lm.zener.math.Vector3f;

public class LightComponent extends Component {

    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private boolean isPoint;

    private boolean enable;

    private float attenuation;
    private float spotCutoff, spotExponent;
    private Vector3f spotDirection;

    public LightComponent() {
        this(true);
    }

    public LightComponent(boolean isPoint) {
        ambient = new Vector3f(0.0f, 0.0f, 0.0f);
        diffuse = new Vector3f(0.8f, 0.8f, 0.8f);
        specular = new Vector3f(1.0f, 1.0f, 1.0f);
        this.isPoint = isPoint;
        this.enable = true;
        attenuation = 0.0f;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isPoint() {
        return isPoint;
    }

    public void setPoint(boolean isPoint) {
        this.isPoint = isPoint;
    }

    public float getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(float attenuation) {
        this.attenuation = attenuation;
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector3f ambient) {
        this.ambient.set(ambient);
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector3f specular) {
        this.specular.set(specular);
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector3f diffuse) {
        this.diffuse.set(diffuse);
    }
}
