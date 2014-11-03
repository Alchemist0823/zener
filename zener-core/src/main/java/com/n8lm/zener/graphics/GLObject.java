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

public abstract class GLObject {

    public static final int INVALID_ID = -1;

    protected int id = INVALID_ID;

    protected boolean updateNeeded = true;

    protected GLObject() {
        GLObjectManager.register(this);
    }

    protected GLObject(int id) {
        super();
        this.id = id;
    }

    public boolean isCreatedGL() {
        if (id == INVALID_ID)
            return false;
        else
            return true;
    }

    public int getID() {
        return id;
    }

    protected void setID(int id) {
        this.id = id;
    }

    public abstract void deleteObject();

}
