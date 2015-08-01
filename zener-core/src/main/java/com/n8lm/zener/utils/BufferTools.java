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

package com.n8lm.zener.utils;

import com.n8lm.zener.math.*;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferTools {

    public static void fillBuffer(Vector4f v, FloatBuffer buffer) {
        buffer.put(v.x);
        buffer.put(v.y);
        buffer.put(v.z);
        buffer.put(v.w);
    }

    public static void fillBuffer(Vector3f v, FloatBuffer buffer) {
        buffer.put(v.x);
        buffer.put(v.y);
        buffer.put(v.z);
    }

    public static void fillBuffer(Vector2f v, FloatBuffer buffer) {
        buffer.put(v.x);
        buffer.put(v.y);
    }

    public static void fillBuffer(ColorRGBA v, FloatBuffer buffer) {
        buffer.put(v.r);
        buffer.put(v.g);
        buffer.put(v.b);
        buffer.put(v.a);
    }

    public static void fillBuffer(Byte4 v, ByteBuffer buffer) {
        buffer.put(v.b1);
        buffer.put(v.b2);
        buffer.put(v.b3);
        buffer.put(v.b4);
    }

    public static void fillBuffer(Rectangle2D rect, FloatBuffer buffer) {
        buffer.put(rect.x0);
        buffer.put(rect.y0);
        buffer.put(rect.x1);
        buffer.put(rect.y1);
    }

    /**
     * @param elements the amount of elements to check
     *
     * @return true if the contents of the two buffers are the same, false if not
     */
    public static boolean bufferEquals(FloatBuffer bufferOne, FloatBuffer bufferTwo, int elements) {
        for (int i = 0; i < elements; i++) {
            if (bufferOne.get(i) != bufferTwo.get(i)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * @param values the byte values that are to be turned into a readable ByteBuffer
     *
     * @return a readable ByteBuffer
     */
    public static ByteBuffer asByteBuffer(byte... values) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(values.length);
        buffer.put(values);
        return buffer;
    }

    /**
     * @param b the byte values that are to be turned into a readable ByteBuffer
     *
     * @return a readable ByteBuffer
     */
    public static ByteBuffer asByteBuffer(Byte4 b) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(4);
        buffer.put(b.b1);
        buffer.put(b.b2);
        buffer.put(b.b3);
        buffer.put(b.b4);
        return buffer;
    }
    
    /**
     * @param buffer a readable buffer
     * @param elements the amount of elements in the buffer
     *
     * @return a string representation of the elements in the buffer
     */
    public static String bufferToString(FloatBuffer buffer, int elements) {
        StringBuilder bufferString = new StringBuilder();
        for (int i = 0; i < elements; i++) {
            bufferString.append(" ").append(buffer.get(i));
        }
        return bufferString.toString();
    }

    /**
     * @param matrix4f the Matrix4f that is to be turned into a readable FloatBuffer
     *
     * @return a FloatBuffer representation of matrix4f
     */
    public static FloatBuffer asFloatBuffer(Matrix4f matrix4f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix4f.fillFloatBuffer(buffer);
        return buffer;
    }

    /**
     * @param matrix4f the Matrix4f that is to be turned into a FloatBuffer that is readable to OpenGL (but not to you)
     *
     * @return a FloatBuffer representation of matrix4f
     */
    public static FloatBuffer asFlippedFloatBuffer(Matrix4f matrix4f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix4f.fillFloatBuffer(buffer);
        buffer.flip();
        return buffer;
    }

    /**
     * @param values the float values that are to be turned into a readable FloatBuffer
     *
     * @return a readable FloatBuffer containing values
     */
    public static FloatBuffer asFloatBuffer(float... values) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        return buffer;
    }

    /**
     * @param values the float values that are to be turned into a FloatBuffer
     *
     * @return a FloatBuffer readable to OpenGL (not to you!) containing values
     */
    public static FloatBuffer asFlippedFloatBuffer(float... values) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }
    
    /**
     * Creates an integer buffer to hold specified ints
     * - strictly a utility method
     *
     * @param size how many int to contain
     * @return created IntBuffer
     */
    public static IntBuffer createIntBuffer(int size) {
      ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
      temp.order(ByteOrder.nativeOrder());

      return temp.asIntBuffer();
    }
}